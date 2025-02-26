package ufpa.labsd.zookeeper;

import java.io.IOException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class SyncPrimitive implements Watcher {
    protected static ZooKeeper zk = null;
    protected static final Object mutex = new Object();

    public SyncPrimitive(String address) {
        if (zk == null) {
            try {
                zk = new ZooKeeper(address, 3000, this);
            } catch (IOException e) {
                System.out.println("Erro ao conectar ao ZooKeeper: " + e.getMessage());
                zk = null;
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }
}
