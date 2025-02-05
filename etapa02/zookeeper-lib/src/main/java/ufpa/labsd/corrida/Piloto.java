package ufpa.labsd.corrida;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;

public class Piloto implements Runnable {
  private final String nome;
  private final DistributedBarrier barrier;

  public Piloto(String nome, CuratorFramework client, String path) {
    this.nome = nome;
    this.barrier = new DistributedBarrier(client, path);
  }

  @Override
  public void run() {
    try {
      System.out.println("🔴 " + nome + " aguardando largada...");
      barrier.waitOnBarrier();
      System.out.println("🟢 " + nome + " largou!!!");
    } catch (Exception e) {
      System.err.println("Erro no piloto " + nome + ": " + e.getMessage());
    }
  }
}
