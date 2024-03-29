package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.event.party.PartyRoleChangeEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.party.DefaultPartyRoleChangeEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyMember implements PartyMember {

    @Inject
    private transient final DefaultDKFriends dkFriends;

    private final UUID partyId;
    private final UUID playerId;
    private final long joinTime;
    private PartyRole role;

    private transient Party party;

    public DefaultPartyMember(DefaultDKFriends dkFriends,Party party, UUID playerId, long joinTime, PartyRole role) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.partyId = party.getId();
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
        if(party == null) party = dkFriends.getPartyManager().getParty(partyId);
        return party;
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
        if(role.equals(PartyRole.LEADER)){
           PartyMember member = getParty().getLeader();
           if(member != null) member.setRole(PartyRole.MODERATOR);
        }

        PartyRoleChangeEvent event = new DefaultPartyRoleChangeEvent(this,role,this.role);
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
