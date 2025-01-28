package ufpa.labsd.zookeeper;

import org.apache.zookeeper.KeeperException;

public class Piloto extends Thread {
  private String nome;
  private String numeroDoCarro;
  private String statusDeCorrida;

  public Piloto(String nome, String numeroDoCarro) {
      this.nome = nome;
      this.numeroDoCarro = numeroDoCarro;
      this.statusDeCorrida = "Aguardando";
  }

  @Override
  public void run() {
      // Entra na barreira antes de iniciar a corrida
      Barreira b = new Barreira("localhost:2181", "/corrida", 3);
      try {
          boolean flag = b.entrar();
          System.out.println(nome + " está na barreira...");
          if (!flag) {
              System.out.println("Erro ao entrar na barreira");
          }
      } catch (KeeperException | InterruptedException e) {
          e.printStackTrace();
      }

      // Inicia a corrida
      System.out.println(nome + " está correndo!");
      this.statusDeCorrida = "Correndo";

      // Simula a corrida
      try {
          Thread.sleep(2000); // Simula o tempo de corrida
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      // Sai da barreira depois de terminar a corrida
      try {
          Barreira b2 = new Barreira("localhost:2181", "/corrida", 3);
          b2.sair();
          System.out.println(nome + " terminou a corrida.");
          this.statusDeCorrida = "Finalizado";
      } catch (KeeperException | InterruptedException e) {
          e.printStackTrace();
      }
  }

  public String getNome() {
      return nome;
  }

  public String getNumeroDoCarro() {
      return numeroDoCarro;
  }

  public String getStatusDeCorrida() {
      return statusDeCorrida;
  }
}
