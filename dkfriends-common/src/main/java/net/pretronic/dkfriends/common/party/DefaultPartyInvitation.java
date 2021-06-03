package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyInvitation implements PartyInvitation {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID partyId;
    private final UUID playerId;
    private final UUID inviterId;
    private final long invitationTime;

    private transient Party party;

    public DefaultPartyInvitation(DKFriends dkfriends,Party party, UUID playerId, UUID inviterId, long invitationTime) {
        this.dkfriends = dkfriends;
        this.party = party;
        this.partyId = party.getId();
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
        if(party == null) party = dkfriends.getPartyManager().getParty(partyId);
        return party;
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
        return getParty().acceptInvitation(this);
    }

    @Override
    public void deny() {
        getParty().denyInvitation(this);
    }
}
