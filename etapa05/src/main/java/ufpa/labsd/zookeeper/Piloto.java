package ufpa.labsd.zookeeper;

import ufpa.labsd.zookeeper.Equipe;

public class Piloto {
  private String nome;
  private Equipe equipe;
  private Barreira barreiraCorrida;

  public Piloto(String nome, Equipe equipe) {
    this.nome = nome;
    this.equipe = equipe;
    this.barreiraCorrida = new Barreira("localhost:2181", "/corrida", 3);
  }

  public void competir() throws Exception {
    if (equipe.validarAptidao()) {
      System.out.println(nome + " está apto para a corrida!");
      if (this.barreiraCorrida.entrar(10000)) {
        System.out.println(nome + " está pronto para correr!");
      } else {
        System.out.println(nome + " não chegou a tempo para a corrida.");
      }
    } else {
      System.out.println(nome + " não ficou apto a tempo.");
    }
  }
}
