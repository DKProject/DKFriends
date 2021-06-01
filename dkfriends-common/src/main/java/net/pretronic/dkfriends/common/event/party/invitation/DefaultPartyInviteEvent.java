package net.pretronic.dkfriends.common.event.party.invitation;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInviteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyInviteEvent implements PartyInviteEvent {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID partyId;
    private final PartyInvitation invitation;

    private transient boolean cancelled;
    private transient Party party;

    public DefaultPartyInviteEvent(DKFriends dkfriends, Party party, PartyInvitation invitation) {
        this.dkfriends = dkfriends;
        this.partyId = party.getId();
        this.invitation = invitation;

        this.cancelled = false;
        this.party = party;
    }

    @Override
    public UUID getPlayerId() {
        return invitation.getPlayerId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return invitation.getPlayer();
    }

    @Override
    public UUID getPartyId() {
        return partyId;
    }

    @Override
    public Party getParty() {
        if(party == null) party = dkfriends.getPartyManager().getParty(partyId);
        return party;
    }

    @Override
    public PartyInvitation getInvitation() {
        return invitation;
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
