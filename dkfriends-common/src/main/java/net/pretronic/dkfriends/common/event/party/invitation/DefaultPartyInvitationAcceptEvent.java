package net.pretronic.dkfriends.common.event.party.invitation;

import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationAcceptEvent;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyInvitationAcceptEvent implements PartyInvitationAcceptEvent {

    private final PartyInvitation invitation;

    public DefaultPartyInvitationAcceptEvent(PartyInvitation invitation) {
        this.invitation = invitation;
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
    public PartyInvitation getInvitation() {
        return invitation;
    }
}
