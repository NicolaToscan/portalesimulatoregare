package it.unitn.lpsmt2020.portalesimulatoregare.models;

import java.util.List;

public class ChampionshipItem extends  ChampionshipItemLight {

    public final List<Event> events;
    public final List<GameSetting> gameSetting;
    public final List<PilotSubscription> pilotSubscriptions;
    public final List<String> cars;


    public ChampionshipItem(int id, String name, String imgUrl, boolean isSubscribed, List<Event> events, List<GameSetting> gameSetting, List<PilotSubscription> pilotSubscriptions, List<String> cars) {
        super(id, name, imgUrl, isSubscribed);
        this.events = events;
        this.gameSetting = gameSetting;
        this.pilotSubscriptions = pilotSubscriptions;
        this.cars = cars;
    }


    public class Event {
        public final int index;
        public final String date;
        public final String circuit;

        public Event(int index, String date, String circuit) {
            this.index = index;
            this.date = date;
            this.circuit = circuit;
        }
    }

    public class GameSetting {
        public final String title;
        public final String value;

        public GameSetting(String title, String value) {
            this.title = title;
            this.value = value;
        }
    }

    public class PilotSubscription {
        public final String name;
        public final String team;
        public final String car;

        public PilotSubscription(String name, String team, String car) {
            this.name = name;
            this.team = team;
            this.car = car;
        }
    }



}
