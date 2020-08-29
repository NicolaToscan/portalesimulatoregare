package it.unitn.lpsmt2020.portalesimulatoregare.event;

import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItemLight;

public class SubscriptionChangedEvent {
    public final ChampionshipItemLight item;

    public SubscriptionChangedEvent(ChampionshipItemLight item) {
        this.item = item;

    }
}
