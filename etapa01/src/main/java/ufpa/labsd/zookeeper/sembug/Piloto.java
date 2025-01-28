package ufpa.labsd.zookeeper.sembug;

import org.apache.zookeeper.KeeperException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        // Aqui simula a comunicação UDP durante a corrida (sem interrupção de IO)
        Listen listen = new Listen(nome); // Substituindo o comportamento da classe Listen da Pessoa
        listen.start();

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

    // Classe Listen para simular comunicação UDP
    public static class Listen extends Thread {
        private String nome;

        public Listen(String nome) {
            this.nome = nome;
        }

        @Override
        public void run() {
            DatagramSocket serverSocket = null;
            try {
                serverSocket = new DatagramSocket();  
            } catch (SocketException ex) {
                Logger.getLogger(Piloto.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro ao criar o socket, provavelmente conflito de porta");
            }

            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    System.out.println(nome + " escutando...");
                    serverSocket.receive(receivePacket);
                } catch (IOException ex) {
                    System.out.println("Erro ao receber a mensagem!");
                    Logger.getLogger(Piloto.class.getName()).log(Level.SEVERE, null, ex);
                }

                String sentence = new String(receivePacket.getData()).trim();
                System.out.println(nome + " recebeu: " + sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                // Responde com o número do carro 
                sendData = this.nome.getBytes();  
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, port);

                try {
                    serverSocket.send(sendPacket);
                    System.out.println(nome + " respondeu!");
                } catch (IOException ex) {
                    Logger.getLogger(Piloto.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Erro ao responder!");
                }
            }
        }
    }
}
