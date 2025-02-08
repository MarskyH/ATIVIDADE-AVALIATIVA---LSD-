package ufpa.labsd.corrida;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Run {
  public static void main(String[] args) throws Exception {

    final String ZOOKEEPER_CONNECTION_STRING = "localhost:2181";
    final String PATH_LARGADA = "/corrida/largada";
    final String PATH_CHEGADA = "/corrida/chegada";
    final int TOTAL_PILOTOS = 3;

    CuratorFramework client = CuratorFrameworkFactory.builder()
        .connectString(ZOOKEEPER_CONNECTION_STRING)
        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
        .sessionTimeoutMs(15000)
        .connectionTimeoutMs(5000)
        .build();
    client.start();

    Corrida corrida = new Corrida(client, PATH_LARGADA);
    corrida.prepararCorrida();

    Piloto p1 = new Piloto("Hamilton", client, PATH_LARGADA, PATH_CHEGADA, TOTAL_PILOTOS);
    Piloto p2 = new Piloto("Verstappen", client, PATH_LARGADA, PATH_CHEGADA, TOTAL_PILOTOS);
    Piloto p3 = new Piloto("Leclerc", client, PATH_LARGADA, PATH_CHEGADA, TOTAL_PILOTOS);

    Thread t1 = new Thread(p1);
    Thread t2 = new Thread(p2);
    Thread t3 = new Thread(p3);

    t1.start();
    t2.start();
    t3.start();

    Thread.sleep(2000);

    corrida.iniciarCorrida();

    Thread.sleep(2000);

    t1.join();
    t2.join();
    t3.join();

    client.close();
  }
}
