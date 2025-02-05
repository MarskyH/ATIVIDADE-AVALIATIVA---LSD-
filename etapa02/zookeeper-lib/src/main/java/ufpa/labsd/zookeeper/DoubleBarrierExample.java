package ufpa.labsd.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/*
 * BARREIRA DUPLA
 *
 * É uma forma de garantir que vários processos cheguem a um ponto comum, antes
 * de proseguir.
 *
 * Todos precisam entrar `enter()` antes de sair `leave()`.
 *
 */
public class DoubleBarrierExample {

  private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:2181";
  private static final String BARRIER_PATH = "/double-barrier";
  private static final int PARTICIPANT_COUNT = 3;

  public static void main(String[] args) {
    CuratorFramework client = CuratorFrameworkFactory.newClient(
        ZOOKEEPER_CONNECTION_STRING,
        new ExponentialBackoffRetry(1000, 3));
    client.start();

    DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, BARRIER_PATH, PARTICIPANT_COUNT);

    try {
      System.out.format("🔴 Esperando para entrar na barreira %s...\n", BARRIER_PATH);
      barrier.enter();
      System.out.println("🟠 Entrou na barreira");


      System.out.println("🟡 Esperando para sair...");
      barrier.leave();
      System.out.println("🟢 Saiu da barreira!");

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      client.close();
    }
  }
}
