package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyTeleportEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyTeleportEvent implements PartyTeleportEvent {

    @Inject
    private transient final DKFriends dkFriends;

    private final UUID partyId;
    private final String type;
    private final String target;

    private transient Party party;
    private transient boolean cancelled;

    public DefaultPartyTeleportEvent(DKFriends dkFriends,Party party,String type, String target) {
        this.dkFriends = dkFriends;
        this.partyId = party.getId();
        this.party = party;
        this.type = type;
        this.target = target;

        this.cancelled = false;
    }

    @Override
    public Party getParty() {
        if(party == null) party = dkFriends.getPartyManager().getParty(partyId);
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
