package ufpa.labsd.zookeeper;

import java.util.Arrays;
import java.util.List;

import ufpa.labsd.zookeeper.Equipe;

public class Corrida {
  public static void main(String[] args) throws Exception {
    Equipe equipeA = new Equipe("EquipeA", 2);
    Equipe equipeB = new Equipe("EquipeB", 2);

    List<Piloto> pilotos = Arrays.asList(
        new Piloto("Piloto1", equipeA),
        new Piloto("Piloto2", equipeA),
        new Piloto("Piloto3", equipeB),
        new Piloto("Piloto4", equipeB));

    for (Piloto piloto : pilotos) {
      new Thread(() -> {
        try {
          piloto.competir();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}
