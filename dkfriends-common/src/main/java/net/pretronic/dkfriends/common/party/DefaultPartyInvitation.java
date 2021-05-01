package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyInvitation implements PartyInvitation {

    private final DKFriends dkfriends;
    private final UUID partyId;
    private final UUID playerId;
    private final UUID inviterId;
    private final long invitationTime;

    public DefaultPartyInvitation(DKFriends dkfriends, UUID partyId, UUID playerId, UUID inviterId, long invitationTime) {
        this.dkfriends = dkfriends;
        this.partyId = partyId;
        this.playerId = playerId;
        this.inviterId = inviterId;
        this.invitationTime = invitationTime;
    }

    @Override
    public UUID getPartyId() {
        return partyId;
    }

    @Override
    public Party getParty() {
        return dkfriends.getPartyManager().getParty(partyId);
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return dkfriends.getPlayerManager().getPlayer(playerId);
    }

    @Override
    public UUID getInviterId() {
        return inviterId;
    }

    @Override
    public DKFriendsPlayer getInviter() {
        return dkfriends.getPlayerManager().getPlayer(inviterId);
    }

    @Override
    public long getInvitationTime() {
        return invitationTime;
    }

    @Override
    public PartyMember accept() {
        return null;
    }

    @Override
    public void deny() {

    }
}
