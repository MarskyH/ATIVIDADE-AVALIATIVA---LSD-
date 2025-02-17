package ufpa.labsd.zookeeper;

public class Corrida {
    public static void main(String[] args) {
        // Usando a Barreira Reutilizável Restritiva
        Barreira barreira = new Barreira("localhost:2181", "/corrida", 3);

        for (int i = 0; i < 2; i++) { // Executa duas corridas
            Piloto piloto1 = new Piloto("José Carlos", "Carro " + (i * 3 + 1), barreira);
            Piloto piloto2 = new Piloto("Vitória Nauanda", "Carro " + (i * 3 + 2), barreira);
            Piloto piloto3 = new Piloto("Clarice Mendes", "Carro " + (i * 3 + 3), barreira);

            // Inicia os pilotos para a corrida
            piloto1.start();
            piloto2.start();
            piloto3.start();

            try {
                // Aguarda a conclusão de cada piloto para a corrida
                piloto1.join();
                piloto2.join();
                piloto3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Após todos os pilotos terminarem, a barreira é resetada
            try {
                barreira.resetar(); // Resetando a barreira para a próxima corrida
            } catch (Exception e) {
                System.out.println("Erro ao resetar a barreira: " + e.getMessage());
            }
        }

        System.out.println("Corrida finalizada!");
    }
}
