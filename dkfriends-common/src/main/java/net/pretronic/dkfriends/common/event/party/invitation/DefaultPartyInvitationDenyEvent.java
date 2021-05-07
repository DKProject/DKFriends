package net.pretronic.dkfriends.common.event.party.invitation;

import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationDenyEvent;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyInvitationDenyEvent implements PartyInvitationDenyEvent {

    private final PartyInvitation invitation;

    public DefaultPartyInvitationDenyEvent(PartyInvitation invitation) {
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
