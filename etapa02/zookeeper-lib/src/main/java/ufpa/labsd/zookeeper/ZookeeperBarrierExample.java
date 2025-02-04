package ufpa.labsd.zookeeper;

public class ZookeeperBarrierExample {
  public static void main(String[] args) throws Exception {
    Barrier barrier = new Barrier();

    if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
      System.out.println("Criando a barreira...");
      barrier.createBarrier();
      System.out.println("Barreira definida.");

    } else if (args.length > 0 && args[0].equalsIgnoreCase("remove")) {
      System.out.println("Removendo a barreira...");
      barrier.remove();
      System.out.println("Barreira removida!");

    } else {
      System.out.println(Thread.currentThread().getName() + " aguardando a liberação da barreira...");
      barrier.waitBarrier();
      System.out.println(Thread.currentThread().getName() + " passou pela barreira!");
    }
    barrier.closeClient();
  }
}
