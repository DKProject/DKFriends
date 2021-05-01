package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyInviteEvent;
import net.pretronic.dkfriends.api.event.party.PartyMessageEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyInviteEvent implements PartyInviteEvent {

    private final DKFriends dkFriends;
    private final UUID partyId;
    private final PartyInvitation invitation;

    public DefaultPartyInviteEvent(DKFriends dkFriends, UUID partyId, PartyInvitation invitation) {
        this.dkFriends = dkFriends;
        this.partyId = partyId;
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
    public UUID getPartyId() {
        return partyId;
    }

    @Override
    public Party getParty() {
        return dkFriends.getPartyManager().getParty(partyId);
    }

    @Override
    public PartyInvitation getInvitation() {
        return invitation;
    }

}
