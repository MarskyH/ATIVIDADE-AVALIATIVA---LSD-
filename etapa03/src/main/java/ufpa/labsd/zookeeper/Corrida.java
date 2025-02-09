package ufpa.labsd.zookeeper;

public class Corrida {
    public static void main(String[] args) {
        // Primeiro grupo de pilotos
        Piloto piloto1 = new Piloto("José Carlos", "Carro 1");
        Piloto piloto2 = new Piloto("Vitória Nauanda", "Carro 2");
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

        System.out.println("Primeira corrida finalizada!");

        // Segundo grupo de pilotos
        Piloto piloto4 = new Piloto("Ana Beatriz", "Carro 4");
        Piloto piloto5 = new Piloto("Fernando Lima", "Carro 5");
        Piloto piloto6 = new Piloto("Lucas Oliveira", "Carro 6");

        piloto4.start();
        piloto5.start();
        piloto6.start();

        try {
            piloto4.join();
            piloto5.join();
            piloto6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Segunda corrida finalizada!");
    }
}
