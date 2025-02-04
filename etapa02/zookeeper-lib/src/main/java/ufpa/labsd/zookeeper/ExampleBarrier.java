package ufpa.labsd.zookeeper;

import ufpa.labsd.zookeeper.Barrier;

public class ExampleBarrier {
  public static void main(String[] args) {
    final Barrier barrier = new Barrier();

    barrier.createBarrier();
    int numThreads = 3;

    for (int i = 0; i < numThreads; i++) {
      final int threadId = i + 1;

      new Thread(() -> {

        try {
          System.out.println("Thread " + threadId + " esperando na barreira...");
          barrier.waitBarrier();
          System.out.println("Thread " + threadId + " passou da barreira!");

        } catch (Exception e) {
          System.err.println("Erro na Thread " + threadId + ": " + e.getMessage());
        }

      }).start();

    }

    try {
      Thread.sleep(5000);
      System.out.println("Removendo a barreira...");
      barrier.remove();

    } catch (Exception e) {
      System.err.println("Erro ao remover barreira: " + e.getMessage());
    }

    barrier.closeClient();
  }
}
