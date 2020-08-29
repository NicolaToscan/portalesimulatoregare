package it.unitn.lpsmt2020.portalesimulatoregare.models;

public class ChampionshipItemLight {
    private final int id;
    private final String name;
    private final String imgUrl;
    private boolean isSubscribed;

    public ChampionshipItemLight(int id, String name, String imageUrl, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.imgUrl = imageUrl;
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
