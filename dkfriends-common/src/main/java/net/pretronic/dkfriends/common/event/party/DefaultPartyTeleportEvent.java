package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyTeleportEvent;
import net.pretronic.dkfriends.api.party.Party;

public class DefaultPartyTeleportEvent implements PartyTeleportEvent {

    private final Party party;
    private final String type;
    private final String target;
    private boolean cancelled;

    public DefaultPartyTeleportEvent(Party party,String type, String target) {
        this.party = party;
        this.type = type;
        this.target = target;

        this.cancelled = false;
    }

    @Override
    public Party getParty() {
        return party;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
