package it.unitn.lpsmt2020.portalesimulatoregare.models;

public class ChampionshipItem {
    private int id;
    private String name;

    public ChampionshipItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
