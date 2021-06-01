package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyDeleteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyDeleteEvent implements PartyDeleteEvent {

    @Inject
    private final DKFriends dkFriends;

    private final UUID partyId;

    private transient Party party;
    private transient boolean cancelled;

    public DefaultPartyDeleteEvent(DKFriends dkFriends,Party party) {
        this.dkFriends = dkFriends;
        this.partyId = party.getId();
        this.party = party;
        this.cancelled = false;
    }

    @Override
    public UUID getPartyId() {
        return party.getId();
    }

    @Override
    public Party getParty() {
        if(party == null) party = dkFriends.getPartyManager().getParty(partyId);
        return party;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
