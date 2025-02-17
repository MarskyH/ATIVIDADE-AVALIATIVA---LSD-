package ufpa.labsd.zookeeper;

import org.apache.zookeeper.KeeperException;

public class Piloto extends Thread {
    private String nome;
    private String numeroDoCarro;
    private Barreira barreira;

    public Piloto(String nome, String numeroDoCarro, Barreira barreira) {
        this.nome = nome;
        this.numeroDoCarro = numeroDoCarro;
        this.barreira = barreira;
    }

    @Override
    public void run() {
        try {
            if (barreira.entrar()) {
                System.out.println(nome + " está pronto para correr!");
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

        // Simula a corrida
        System.out.println(nome + " está correndo!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (barreira.sair()) {
                System.out.println(nome + " terminou a corrida.");
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
