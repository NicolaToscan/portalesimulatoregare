package it.unitn.lpsmt2020.portalesimulatoregare.models;

public class ChampionshipItem {
    private int id;
    private String name;
    private boolean isSubscribed;

    public ChampionshipItem(int id, String name, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.isSubscribed = isSubscribed;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean isSubscribed() {
        return this.isSubscribed;
    }
    public void  setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }
}
