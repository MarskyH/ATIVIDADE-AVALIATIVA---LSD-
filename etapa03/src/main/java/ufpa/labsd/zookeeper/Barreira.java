package ufpa.labsd.zookeeper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * Barreira reutilizável para sincronizar os pilotos na corrida
 */
public class Barreira extends SyncPrimitive {
    private int tamanho;  // Número de "pilotos" necessários para a barreira
    private String nome;  // Nome do piloto (hostname)
    private String root;  // Caminho no Zookeeper onde a barreira será armazenada

    public Barreira(String address, String root, int tamanho) {
        super(address);  // Chama o construtor da classe pai que inicializa o Zookeeper
        this.root = root;  // Define o caminho onde a barreira estará no Zookeeper
        this.tamanho = tamanho;  // Define o tamanho (número de pilotos necessários)

        // Criando o nó da barreira se não existir
        try {
            Stat s = zk.exists(root, false);  // Verifica se o nó da barreira já existe
            if (s == null) {
                // Se não existir, cria um nó persistente no Zookeeper
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            System.out.println("Erro ao criar a barreira: " + e.getMessage());
        }

        // Definindo o nome do piloto baseado no hostname
        try {
            nome = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            System.out.println("Erro ao obter hostname: " + e.getMessage());
            nome = "desconhecido";
        }
    }

    // Método que permite que o "piloto" entre na barreira
    public boolean entrar() throws KeeperException, InterruptedException {
        String path = root + "/" + nome + "_";  // Define o caminho do nó para o piloto
        // Cria um nó sequencial e efêmero no Zookeeper para esse piloto
        zk.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        
        while (true) {
            synchronized (mutex) {
                // Obtém a lista de nós filhos (pilotos) da barreira
                List<String> lista = zk.getChildren(root, true);
                if (lista.size() < tamanho) {
                    // Se não houver o número necessário de pilotos, espera
                    mutex.wait();
                } else {
                    // Quando o número necessário de pilotos for atingido, continua
                    return true;
                }
            }
        }
    }

    // Método que permite que o "piloto" saia da barreira
    public boolean sair() throws KeeperException, InterruptedException {
        List<String> lista = zk.getChildren(root, false);
        
        // Tenta remover o nó do piloto da barreira
        for (String node : lista) {
            if (node.startsWith(nome + "_")) {
                try {
                    // Remove o nó do "piloto" que saiu da barreira
                    zk.delete(root + "/" + node, -1);
                } catch (KeeperException.NoNodeException e) {
                    // Se o nó já foi removido, não há problema
                    System.out.println("Nó já foi removido: " + root + "/" + node);
                }
                break;
            }
        }
    
        // Verifica se a barreira foi esvaziada, evitando espera infinita
        while (true) {
            synchronized (mutex) {
                lista = zk.getChildren(root, true);
                if (lista.isEmpty()) {
                    // Se não houver mais pilotos, significa que a barreira foi liberada
                    System.out.println("Todos os pilotos saíram da barreira. Liberando...");
                    return true;
                } 
                
                // Se o piloto não está mais na lista, ele já saiu
                if (!lista.contains(nome + "_")) {
                    return true;
                }
    
                // Espera se ainda houver pilotos na barreira
                mutex.wait();
            }
        }
    }
}
