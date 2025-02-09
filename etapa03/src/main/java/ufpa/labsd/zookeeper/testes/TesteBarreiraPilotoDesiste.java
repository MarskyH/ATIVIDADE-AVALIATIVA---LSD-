package ufpa.labsd.zookeeper.testes;

import ufpa.labsd.zookeeper.Piloto;

public class TesteBarreiraPilotoDesiste {
    public static void main(String[] args) {
        System.out.println("Teste: Um piloto abandona antes da corrida iniciar.");

        Piloto piloto1 = new Piloto("José Carlos", "Carro 1");
        Piloto piloto2 = new Piloto("Vitória Nauanda", "Carro 2");
        Piloto piloto3 = new Piloto("Clarice Mendes", "Carro 3");

        piloto1.start();
        piloto2.start();

        try {
            Thread.sleep(1000); // Simula um atraso
            System.out.println("Simulando abandono do piloto 3 antes de entrar na barreira.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            piloto1.join();
            piloto2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fim do teste de abandono prematuro.");
    }
}
