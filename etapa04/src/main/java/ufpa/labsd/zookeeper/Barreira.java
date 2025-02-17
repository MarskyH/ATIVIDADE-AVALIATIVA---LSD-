package ufpa.labsd.zookeeper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * Barreira reutilizável e restritiva para sincronizar os pilotos na corrida
 */
public class Barreira extends SyncPrimitive {
    private int tamanho;
    private String nome;
    private String root;

    public Barreira (String address, String root, int tamanho) {
        super(address);
        this.root = root;
        this.tamanho = tamanho;

        // Criando nó da barreira se não existir
        try {
            Stat s = zk.exists(root, false);
            if (s == null) {
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Erro ao criar a barreira: " + e.getMessage());
        }

        // Definindo nome do piloto baseado no hostname
        try {
            nome = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            System.out.println("Erro ao obter hostname: " + e.getMessage());
            nome = "desconhecido";
        }
    }

    public boolean entrar() throws KeeperException, InterruptedException {
        String path = root + "/" + nome + "_";
        zk.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    
        long startTime = System.currentTimeMillis();
        long timeout = 10000; // Tempo limite de 10 segundos
    
        while (true) {
            synchronized (mutex) {
                List<String> lista = zk.getChildren(root, true);
                if (lista.size() >= tamanho) {
                    return true; // Todos chegaram
                }
                if (System.currentTimeMillis() - startTime > timeout) {
                    System.out.println("Tempo limite atingido! Corrida iniciando sem todos os pilotos.");
                    return false; // Permite iniciar a corrida, mas registra que houve atraso
                }
                mutex.wait(1000); // Espera um segundo antes de checar novamente
            }
        }
    }
    
    public boolean sair() throws KeeperException, InterruptedException {
        List<String> lista = zk.getChildren(root, false);
        
        if (lista.size() < tamanho) {
            System.out.println("Erro: Tentativa de saída antes que todos entrassem!");
            return false; // Impede a saída prematura
        }
    
        for (String node : lista) {
            if (node.startsWith(nome + "_")) {
                try {
                    zk.delete(root + "/" + node, -1);
                } catch (KeeperException.NoNodeException e) {
                    System.out.println("Nó já foi removido: " + root + "/" + node);
                }
                break;
            }
        }
    
        while (true) {
            synchronized (mutex) {
                lista = zk.getChildren(root, true);
                
                if (lista.isEmpty()) {
                    System.out.println("Todos os pilotos saíram da barreira. Liberando para o próximo ciclo...");
                    return true;
                }
    
                mutex.wait(500);
            }
        }
    }
    
    public void resetar() throws KeeperException, InterruptedException {
        List<String> lista = zk.getChildren(root, false);
    
        if (!lista.isEmpty()) {
            System.out.println("Erro: Resetando barreira antes que todos saíssem!");
            return;
        }
    
        for (String node : lista) {
            zk.delete(root + "/" + node, -1);
        }
    
        System.out.println("Barreira resetada para o próximo ciclo.");
    }
    
}
