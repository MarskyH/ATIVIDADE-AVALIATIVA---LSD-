package ufpa.labsd.corrida;

import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

public class Piloto implements Runnable {
  private final String nome;
  private final DistributedBarrier largada;
  private final DistributedDoubleBarrier chegada;

  public Piloto(String nome, CuratorFramework client, String pathLargada, String pathChegada, int totalPilotos) {
    this.nome = nome;
    this.largada = new DistributedBarrier(client, pathLargada);
    this.chegada = new DistributedDoubleBarrier(client, pathChegada, totalPilotos);
  }

  @Override
  public void run() {
    try {
      // Largada
      System.out.println("🔴 " + nome + " aguardando largada...");
      largada.waitOnBarrier();
      System.out.println("🟢 " + nome + " largou!!!");

      // Simulação de tempos diferentes de finalização
      int tempo = new Random().nextInt(9000) + 1000;

      System.out.println("⏰ " + nome + "finalizou a corrida em " + tempo + "ms");
      Thread.sleep(tempo);

      // Chegada
      System.out.println("🏳  " + nome + " aguradando finalização da corrida");
      chegada.enter();
      System.out.println("🏁 " + nome + " corrida finalizada !!!");
      chegada.leave();

    } catch (Exception e) {
      System.err.println("Erro no piloto " + nome + ": " + e.getMessage());
    }
  }
}
