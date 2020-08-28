package it.unitn.lpsmt2020.portalesimulatoregare.event;

import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItem;

public class SubscriptionChangedEvent {
    public final ChampionshipItem item;

    public SubscriptionChangedEvent(ChampionshipItem item) {
        this.item = item;

    }
}
