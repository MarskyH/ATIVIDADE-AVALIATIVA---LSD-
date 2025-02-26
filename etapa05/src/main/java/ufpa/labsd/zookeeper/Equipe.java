package ufpa.labsd.zookeeper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 *                              Sobre a aptidão
 *
 * Entende-se como apta a equipe que passou pelo processo de homologação pré
 * corrida.
 *
 */

public class Equipe {
  private String nome;
  private Barreira barreiraAptidao;
  private List<Piloto> pilotos;
  private AtomicInteger pilotosAptos;

  public Equipe(String nome, int tamanho) {
    this.nome = nome;
    this.barreiraAptidao = new Barreira("localhost:2181", "/aptidao_" + nome, tamanho);
    this.pilotosAptos = new AtomicInteger(0);
  }

  public synchronized boolean validarAptidao() throws Exception {
    if (barreiraAptidao.entrar(5000)) {
      int aptos = pilotosAptos.incrementAndGet();
      System.out.println(nome + " tem " + aptos + " pilotos aptos.");
      return true;
    }
    return false;
  }

  public void adicionarPiloto(Piloto piloto) {
    pilotos.add(piloto);
  }

  public List<Piloto> getPilotos() {
    return pilotos;
  }
}
