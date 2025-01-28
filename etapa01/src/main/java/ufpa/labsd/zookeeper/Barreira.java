package ufpa.labsd.zookeeper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * Barreira para sincronizar os pilotos na corrida
 */
public class Barreira extends SyncPrimitive {
    int tamanho;
    String nome;

    /**
     * Construtor da barreira
     */
    Barreira(String address, String root, int tamanho) {
        super(address);
        this.root = root;
        this.tamanho = tamanho;

        // Criação do nó da barreira
        if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                            CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                System.out.println("Erro ao criar a barreira: " + e.toString());
            } catch (InterruptedException e) {
                System.out.println("Erro de interrupção");
            }
        }

        // Nome do carro/piloto
        try {
            nome = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Método para os pilotos entrarem na barreira
     */
    boolean entrar() throws KeeperException, InterruptedException {
        zk.create(root + "/" + nome, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        while (true) {
            synchronized (mutex) {
                List<String> lista = zk.getChildren(root, true);

                if (lista.size() < tamanho) {
                    mutex.wait();
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * Método para os pilotos saírem da barreira
     */
    boolean sair() throws KeeperException, InterruptedException {
        zk.delete(root + "/" + nome, 0);
        while (true) {
            synchronized (mutex) {
                List<String> lista = zk.getChildren(root, true);
                if (lista.size() > 0) {
                    mutex.wait();
                } else {
                    return true;
                }
            }
        }
    }
}

