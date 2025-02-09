package ufpa.labsd.zookeeper.testes;

import ufpa.labsd.zookeeper.Piloto;

public class TesteBarreiraBasico {
    public static void main(String[] args) {
        System.out.println("Teste Básico: Todos os pilotos entram e saem corretamente da barreira.");
        
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

        System.out.println("Teste Básico finalizado com sucesso.");
    }
}
