package ufpa.labsd.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/*
 * BARREIRA SIMPLES
 *
 * Uma forma de garantir bloqueio e desbloqueio proposital.
 *
 * Não tem, necessáriamente uma condições de processos que precisam chegar até a
 * barreira para que ela seja removida, como acontece nas barreiras duplas.
 *
 * A remoção acontece de forma explicita. É necessário invocar a liberação da
 * barreira, deferentemente do que acontece com as barreiras duplas.
 *
 */
public class BarrierExample {
  private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:2181";
  private static final String BARRIER_PATH = "/simple-barrier";

  public static void main(String[] args) {
    CuratorFramework client = CuratorFrameworkFactory.newClient(
        ZOOKEEPER_CONNECTION_STRING,
        new ExponentialBackoffRetry(100, 3));
    client.start();

    DistributedBarrier barrier = new DistributedBarrier(client, BARRIER_PATH);

    String operation = args.length > 0 ? args[0].toLowerCase() : "default";

    try {
      switch (operation) {

        // Criar barreira
        case "set":
          barrier.setBarrier();
          System.out.println("🔴 Barreira definida");
          break;

        // Esperar pela barreira
        case "wait":
          if (client.checkExists().forPath(BARRIER_PATH) != null) {
            System.out.println("🟡 Esperando na barreira...");
            barrier.waitOnBarrier();
            System.out.println("🏁 Saiu da barreira");
          } else {
            System.out.format("A barreira %s não existe.\n", BARRIER_PATH);
          }
          break;

        // Remover a barreira
        case "remove":
          if (client.checkExists().forPath(BARRIER_PATH) != null) {
            barrier.removeBarrier();
            System.out.println("🟢 Barreira removida");
          } else {
            System.out.format("A barreira %s não existe.\n", BARRIER_PATH);
          }
          break;

        default:
          System.out.println("Nenhuma das ações válidas para barreira simples foi selecionada: ");
          System.out.println(" - set\n - wait\n - remove");
          break;
      }

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      client.close();
    }
  }
}
