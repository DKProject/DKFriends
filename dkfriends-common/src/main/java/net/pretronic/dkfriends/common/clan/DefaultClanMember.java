package net.pretronic.dkfriends.common.clan;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberRoleUpdateEvent;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberRoleUpdateEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;

public class DefaultClanMember implements ClanMember {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private final UUID playerId;
    private final long joinTime;
    private ClanRole role;

    private transient DKFriendsPlayer player;
    private transient Clan clan;

    public DefaultClanMember(DKFriends dkfriends,Clan clan, UUID playerId, long joinTime, ClanRole role) {
        Validate.notNull(dkfriends,clan, playerId, joinTime, role);
        this.dkfriends = dkfriends;
        this.clanId = clan.getId();
        this.clan = clan;
        this.playerId = playerId;
        this.joinTime = joinTime;
        this.role = role;
    }

    @Override
    public UUID getClanId() {
        return clanId;
    }

    @Override
    public Clan getClan() {
        if(clan == null) clan = dkfriends.getClanManager().getClan(clanId);
        return this.clan;
    }

    @Override
    public UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        if(player == null) player = dkfriends.getPlayerManager().getPlayer(getPlayerId());
        return player;
    }

    @Override
    public long getJoinTime() {
        return this.joinTime;
    }

    @Override
    public ClanRole getRole() {
        return this.role;
    }

    @Override
    public ClanRole setRole(ClanRole role, String cause) {
        Validate.notNull(role);
        ClanMemberRoleUpdateEvent event = new DefaultClanMemberRoleUpdateEvent(this, role,cause);
        ((DefaultDKFriends)(dkfriends)).getEventBus().callEvent(ClanMemberRoleUpdateEvent.class, event);
        if(event.isCancelled()) return null;

        this.role = role;
        ((DefaultDKFriends)(dkfriends)).getStorage().getClanMembers().update()
                .set("Role", role)
                .where("ClanId", clan.getId())
                .where("PlayerId", getPlayerId())
                .execute();
        return role;
    }

    @Override
    public ClanRole promote(ClanMember executor) {
        Validate.notNull(executor);
        if(!executor.getClan().getId().equals(getClan().getId())) {
            throw new IllegalArgumentException("Member (Id:"+executor.getPlayerId() + "; ClanId: "+executor.getClan().getId()
                    +") belongs not to this clan (" + getClan().getId() + ")");
        }
        if(canPromote(executor)) {
            ClanRole newRole;
            if(getRole() == ClanRole.GUEST) newRole = ClanRole.MODERATOR;
            else if(getRole() == ClanRole.MODERATOR) newRole = ClanRole.LEADER;
            else {
                throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not promote player " + getPlayerId() + ". New role can not be get.");
            }

            newRole = setRole(newRole,"PROMOTE");

            if(newRole == ClanRole.LEADER) {
                executor.setRole(ClanRole.MODERATOR);
            }
            return newRole;
        }
        throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not promote player " + getPlayerId());
    }

    @Override
    public ClanRole demote(ClanMember executor) {
        Validate.notNull(executor);
        if(!executor.getClan().getId().equals(getClan().getId())) {
            throw new IllegalArgumentException("Member (Id:"+executor.getPlayerId() + "; ClanId: "+executor.getClan().getId()
                    +") belongs not to this clan (" + getClan().getId() + ")");
        }
        if(canDemote(executor)) {
            ClanRole newRole;
            if(getRole() == ClanRole.MODERATOR) newRole = ClanRole.GUEST;
            else {
                throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not demote player " + getPlayerId() + ". New role can not be get.");
            }

            return setRole(newRole,"DEMOTE");
        }
        throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not demote player " + getPlayerId());
    }

    @Override
    public boolean canDemote(ClanMember player) {
        Validate.notNull(player);
        if(player.getRole() == getRole()) return false;
        return player.getRole() == ClanRole.LEADER && getRole() == ClanRole.MODERATOR;
    }

    @Override
    public boolean canPromote(ClanMember player) {
        Validate.notNull(player);
        if(player.getRole() == getRole()) return false;
        return player.getRole() == ClanRole.LEADER || (player.getRole() == ClanRole.MODERATOR && getRole() != ClanRole.LEADER);
    }

    @Override
    public boolean leave() {
        return getClan().removeMember(this, "LEAVE");
    }

    @Override
    public boolean canKick(ClanMember executor) {
        Validate.notNull(executor);
        if(!executor.getClan().getId().equals(getClan().getId())) {
            throw new IllegalArgumentException("Member (Id:"+executor.getPlayerId() + "; ClanId: "+executor.getClan().getId()
                    +") belongs not to this clan (" + getClan().getId() + ")");
        }
        if(executor.getRole() == ClanRole.LEADER) return true;
        return executor.getRole() == ClanRole.MODERATOR && getRole() == ClanRole.GUEST;
    }

    @Override
    public boolean kick(ClanMember executor) {
        Validate.notNull(executor);
        if(!executor.getClan().getId().equals(getClan().getId())) {
            throw new IllegalArgumentException("Member (Id:"+executor.getPlayerId() + "; ClanId: "+executor.getClan().getId()
                    +") belongs not to this clan (" + getClan().getId() + ")");
        }
        if(canKick(executor)) {
            return getClan().removeMember(this, "KICK");
        }
        throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not kick player " + getPlayerId());
    }

    public void setRoleInternal(ClanRole role){
        this.role = role;
    }
}
