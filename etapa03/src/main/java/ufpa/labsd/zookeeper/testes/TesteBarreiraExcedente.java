package ufpa.labsd.zookeeper.testes;

import ufpa.labsd.zookeeper.Piloto;

public class TesteBarreiraExcedente {
    public static void main(String[] args) {
        System.out.println("Teste: Mais pilotos do que o esperado tentando entrar na barreira.");

        Piloto piloto1 = new Piloto("José Carlos", "Carro 1");
        Piloto piloto2 = new Piloto("Vitória Nauanda", "Carro 2");
        Piloto piloto3 = new Piloto("Clarice Mendes", "Carro 3");
        Piloto piloto4 = new Piloto("Ana Beatriz", "Carro 4"); // Piloto extra

        piloto1.start();
        piloto2.start();
        piloto3.start();
        piloto4.start(); // Piloto extra tenta entrar

        try {
            piloto1.join();
            piloto2.join();
            piloto3.join();
            piloto4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Teste de barreira com excesso finalizado.");
    }
}
