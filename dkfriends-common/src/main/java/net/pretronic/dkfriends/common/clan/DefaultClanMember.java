package net.pretronic.dkfriends.common.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberDemoteEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberKickEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberPromoteEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberRoleUpdateEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberDemoteEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberKickEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberPromoteEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberRoleUpdateEvent;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;

public class DefaultClanMember implements ClanMember {

    private final DefaultClan clan;
    private final UUID playerId;
    private final long joinTime;
    private ClanRole role;

    public DefaultClanMember(DefaultClan clan, UUID playerId, long joinTime, ClanRole role) {
        Validate.notNull(clan, playerId, joinTime, role);
        this.clan = clan;
        this.playerId = playerId;
        this.joinTime = joinTime;
        this.role = role;
    }

    @Override
    public Clan getClan() {
        return this.clan;
    }

    @Override
    public UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return this.clan.dkFriends.getPlayerManager().getPlayer(getPlayerId());
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
    public ClanRole setRole(ClanRole role) {
        Validate.notNull(role);
        ClanMemberRoleUpdateEvent event = new DefaultClanMemberRoleUpdateEvent(getClan(), this, role);
        this.clan.dkFriends.getEventBus().callEvent(ClanMemberRoleUpdateEvent.class, event);
        if(event.isCancelled()) return null;

        this.role = role;
        this.clan.dkFriends.getStorage().getClanMembers().update()
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
            ClanMemberPromoteEvent event = new DefaultClanMemberPromoteEvent(getClan(), this, newRole, executor);
            this.clan.dkFriends.getEventBus().callEvent(ClanMemberPromoteEvent.class, event);

            newRole = setRole(newRole);

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

            ClanMemberDemoteEvent event = new DefaultClanMemberDemoteEvent(getClan(), this, newRole, executor);
            this.clan.dkFriends.getEventBus().callEvent(ClanMemberDemoteEvent.class, event);
            return setRole(newRole);
        }
        throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not demote player " + getPlayerId());
    }

    @Override
    public boolean canDemote(ClanMember player) {
        Validate.notNull(player);
        if(player.getRole() == getRole()) return false;
        if(player.getRole() == ClanRole.LEADER && getRole() == ClanRole.MODERATOR) return true;
        return false;
    }

    @Override
    public boolean canPromote(ClanMember player) {
        Validate.notNull(player);
        if(player.getRole() == getRole()) return false;
        if(player.getRole() == ClanRole.LEADER || player.getRole() == ClanRole.MODERATOR) return true;
        return false;
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
        if(executor.getRole() == ClanRole.MODERATOR && getRole() == ClanRole.GUEST) return true;
        return false;
    }

    @Override
    public boolean kick(ClanMember executor) {
        Validate.notNull(executor);
        if(!executor.getClan().getId().equals(getClan().getId())) {
            throw new IllegalArgumentException("Member (Id:"+executor.getPlayerId() + "; ClanId: "+executor.getClan().getId()
                    +") belongs not to this clan (" + getClan().getId() + ")");
        }
        if(canKick(executor)) {
            ClanMemberKickEvent event = new DefaultClanMemberKickEvent(getClan(), this, executor);
            this.clan.dkFriends.getEventBus().callEvent(ClanMemberKickEvent.class, event);
            if(event.isCancelled()) return false;

            return getClan().removeMember(this, "KICK");
        }
        throw new IllegalArgumentException("Clan member (" + executor.getPlayerId() + ") can not kick player " + getPlayerId());
    }
}
