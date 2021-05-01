package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;

import java.util.UUID;

public class DefaultPartyMember implements PartyMember {

    private final DKFriends dkFriends;
    private final UUID partyId;
    private final UUID playerId;
    private final long joinTime;
    private PartyRole role;

    public DefaultPartyMember(DKFriends dkFriends, UUID partyId, UUID playerId, long joinTime, PartyRole role) {
        this.dkFriends = dkFriends;
        this.partyId = partyId;
        this.playerId = playerId;
        this.joinTime = joinTime;
        this.role = role;
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
    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public long getJoinTime() {
        return joinTime;
    }

    @Override
    public PartyRole getRole() {
        return role;
    }

    @Override
    public void setRole(PartyRole role) {
        this.role = role;
        //@Todo update and sync role
    }

    @Override
    public void leave() {
        getParty().leaveMember(this);
    }
}
