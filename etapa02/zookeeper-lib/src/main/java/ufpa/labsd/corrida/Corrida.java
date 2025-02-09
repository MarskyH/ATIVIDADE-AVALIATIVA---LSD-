package ufpa.labsd.corrida;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;

public class Corrida {
  private List<Piloto> pilotos;
  private DistributedBarrier largada;

  public Corrida(CuratorFramework client, String pathLargada) {
    this.largada = new DistributedBarrier(client, pathLargada);
  }

  public void addPiloto(Piloto piloto) {
    this.pilotos.add(piloto);
  }

  public List<Piloto> getPilotos() {
    return pilotos;
  }

  // Configura a barreira antes da corrida começar
  public void prepararCorrida() throws Exception {
    System.out.println("🔵 Criando barreira...");
    largada.setBarrier();
    System.out.println("🚦 Barreira criada! Pilotos aguardando...");
  }

  // Remove a barreira e inicia a corrida
  public void iniciarCorrida() throws Exception {
    System.out.println("🟡 Corrida liberada! Removendo barreira...");
    largada.removeBarrier();
  }
}
