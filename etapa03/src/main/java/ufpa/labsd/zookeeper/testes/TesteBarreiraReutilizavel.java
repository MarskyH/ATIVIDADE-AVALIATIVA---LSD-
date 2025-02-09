package ufpa.labsd.zookeeper.testes;

import ufpa.labsd.zookeeper.Piloto;

public class TesteBarreiraReutilizavel {
    public static void main(String[] args) {
        System.out.println("Teste: Barreira sendo reutilizada para múltiplas corridas.");

        for (int i = 1; i <= 2; i++) { // Executa duas rodadas
            System.out.println("Rodada " + i + " começando...");

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

            System.out.println("Rodada " + i + " finalizada!");
        }

        System.out.println("Teste de barreira reutilizável concluído.");
    }
}
