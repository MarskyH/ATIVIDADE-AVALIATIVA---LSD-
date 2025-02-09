package ufpa.labsd.zookeeper.testes;

import ufpa.labsd.zookeeper.Piloto;

public class TesteBarreiraIncompleta {
    public static void main(String[] args) {
        System.out.println("Teste: Apenas dois pilotos entram na barreira (não atinge o limite necessário).");

        Piloto piloto1 = new Piloto("José Carlos", "Carro 1");
        Piloto piloto2 = new Piloto("Vitória Nauanda", "Carro 2");

        piloto1.start();
        piloto2.start();

        try {
            piloto1.join();
            piloto2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fim do teste de barreira incompleta.");
    }
}
