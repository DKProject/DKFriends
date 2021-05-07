package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyCreateEvent implements PartyCreateEvent {

    private final DKFriends dkFriends;
    private final Party party;
    private final UUID ownerId;

    private boolean cancelled;

    public DefaultPartyCreateEvent(DKFriends dkFriends, Party party,UUID ownerId) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.ownerId = ownerId;

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
