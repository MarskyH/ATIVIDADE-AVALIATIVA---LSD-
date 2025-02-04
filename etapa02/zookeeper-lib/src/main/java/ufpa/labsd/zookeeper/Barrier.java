package ufpa.labsd.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;

public class Barrier {
  private String stringConnection;
  private String barrierPath;
  private CuratorFramework client;
  private DistributedBarrier barrier;

  public Barrier(String stringConnection, String barrierPath) {
    this.stringConnection = stringConnection;
    this.barrierPath = barrierPath;

    this.client = CuratorFrameworkFactory.newClient(
        this.stringConnection,
        new ExponentialBackoffRetry(1000, 3));

    this.client.start();
    this.barrier = new DistributedBarrier(this.client, this.barrierPath);
  }

  public Barrier() {
    this("localhost:2181", "/barrier");
  }

  public void createBarrier() {
    try {
      this.barrier.setBarrier();
      System.out.println("Barreira definida.");
    } catch (Exception e) {
      System.err.println("Erro ao definir barreira: " + e.getMessage());
    }
  }

  public void waitBarrier() {
    try {
      this.barrier.waitOnBarrier();
      System.out.println("Saiu da barreira.");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Thread interrompida enquanto esperava a barreira.");
    } catch (Exception e) {
      System.err.println("Erro ao esperar pela barreira: " + e.getMessage());
    } finally {
      closeClient();
    }
  }

  public void remove() {
    try {
      this.barrier.removeBarrier();
      System.out.println("Barreira removida com sucesso.");
    } catch (Exception e) {
      System.err.println("Erro ao remover a barreira: " + e.getMessage());
    }
  }

  public void closeClient() {
    if (this.client != null) {
      try {
        this.client.close();
        System.out.println("Cliente ZooKeeper fechado com sucesso.");
      } catch (Exception e) {
        System.err.println("Erro ao fechar cliente ZooKeeper: " + e.getMessage());
      }
    }
  }
}
