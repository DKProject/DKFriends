package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.event.party.PartyDeleteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyDeleteEvent implements PartyDeleteEvent {

    private final DKFriends dkFriends;
    private final Party party;

    private boolean cancelled;

    public DefaultPartyDeleteEvent(DKFriends dkFriends, Party party) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.cancelled = false;
    }

    @Override
    public UUID getPartyId() {
        return party.getId();
    }

    @Override
    public Party getParty() {
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
