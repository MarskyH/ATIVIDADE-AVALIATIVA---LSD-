package ufpa.labsd.corrida;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Run {
  public static void main(String[] args) throws Exception {

    final String ZOOKEEPER_CONNECTION_STRING = "localhost:2181";
    final String BARRIER_PATH = "/corrida/largada";

    CuratorFramework client = CuratorFrameworkFactory.newClient(
        ZOOKEEPER_CONNECTION_STRING,
        new ExponentialBackoffRetry(1000, 3));
    client.start();

    Corrida corrida = new Corrida(client, BARRIER_PATH);
    corrida.prepararCorrida();

    Piloto p1 = new Piloto("Hamilton", client, BARRIER_PATH);
    Piloto p2 = new Piloto("Verstappen", client, BARRIER_PATH);
    Piloto p3 = new Piloto("Leclerc", client, BARRIER_PATH);

    new Thread(p1).start();
    new Thread(p2).start();
    new Thread(p3).start();

    Thread.sleep(2000);

    corrida.iniciarCorrida();

    Thread.sleep(2000);
    client.close();
  }
}
