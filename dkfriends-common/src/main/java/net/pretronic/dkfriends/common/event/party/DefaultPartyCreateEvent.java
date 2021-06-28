package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyCreateEvent implements PartyCreateEvent {

    @Inject
    private final transient DKFriends dkFriends;

    private final UUID partyId;
    private final UUID ownerId;
    private final int maxSize;

    private transient Party party;
    private transient boolean cancelled;

    public DefaultPartyCreateEvent(DKFriends dkFriends, Party party,UUID ownerId,int maxSize) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.partyId = party.getId();
        this.ownerId = ownerId;
        this.maxSize = maxSize;

        this.cancelled = false;
    }

    @Override
    public UUID getPlayerId() {
        return ownerId;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return dkFriends.getPlayerManager().getPlayer(ownerId);
    }

    @Override
    public UUID getPartyId() {
        return partyId;
    }

    @Override
    public Party getParty() {
        if(party == null) party = dkFriends.getPartyManager().getParty(partyId);
        return party;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
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
