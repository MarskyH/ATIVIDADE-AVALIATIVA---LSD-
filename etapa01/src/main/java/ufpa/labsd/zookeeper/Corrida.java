package ufpa.labsd.zookeeper;


public class Corrida {
  public static void main(String[] args) {
      Piloto piloto1 = new Piloto("José Carlos", "Carro 1");
      Piloto piloto2 = new Piloto("Viória Nauanda", "Carro 2");
      Piloto piloto3 = new Piloto("Clarice Mendes", "Carro 3");

      piloto1.start();
      piloto2.start();
      piloto3.start();

      try {
          piloto1.join();
          piloto2.join();
          piloto3.join();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      System.out.println("Corrida finalizada!");
  }
}