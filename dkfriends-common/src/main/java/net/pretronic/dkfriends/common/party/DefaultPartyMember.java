package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyRoleChangeEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.party.DefaultPartyRoleChangeEvent;

import java.util.UUID;

public class DefaultPartyMember implements PartyMember {

    private final DefaultDKFriends dkFriends;
    private final UUID partyId;
    private final UUID playerId;
    private final long joinTime;
    private PartyRole role;

    public DefaultPartyMember(DefaultDKFriends dkFriends, UUID partyId, UUID playerId, long joinTime, PartyRole role) {
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
    public DKFriendsPlayer getPlayer() {
        return dkFriends.getPlayerManager().getPlayer(playerId);
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
        PartyRoleChangeEvent event = new DefaultPartyRoleChangeEvent(this,role);
        dkFriends.getEventBus().callEvent(PartyRoleChangeEvent.class,event);

        dkFriends.getStorage().getPartiesMembers().update()
                .set("Role",role)
                .where("PartyId",partyId)
                .where("PlayerId",playerId)
                .execute();
        this.role = role;
    }

    @Override
    public void promote() {
        PartyRole next;
        if(this.role == PartyRole.GUEST) next = PartyRole.MODERATOR;
        else if(this.role == PartyRole.MODERATOR) next = PartyRole.LEADER;
        else return;
        setRole(next);
    }

    @Override
    public void demote() {
        PartyRole next;
        if(this.role == PartyRole.MODERATOR) next = PartyRole.GUEST;
        else if(this.role == PartyRole.LEADER) next = PartyRole.MODERATOR;
        else return;
        setRole(next);
    }

    @Override
    public void leave() {
        getParty().leaveMember(this);
    }
}
