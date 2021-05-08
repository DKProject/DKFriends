package net.pretronic.dkfriends.common.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberJoinEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberJoinEvent;

import java.util.UUID;

public class DefaultClanInvitation implements ClanInvitation {

    private final DefaultDKFriends dkFriends;

    private final UUID clanId;
    private transient Clan clan;

    private final UUID playerId;
    private transient DKFriendsPlayer player;

    private final UUID inviterId;
    private transient DKFriendsPlayer inviter;

    private final long invitationTime;

    public DefaultClanInvitation(DefaultDKFriends dkFriends, UUID clanId, UUID playerId, UUID inviterId, long invitationTime) {
        this.dkFriends = dkFriends;

        this.clanId = clanId;
        this.playerId = playerId;
        this.inviterId = inviterId;
        this.invitationTime = invitationTime;
    }

    @Override
    public UUID getClanId() {
        return this.clanId;
    }

    @Override
    public Clan getClan() {
        if(this.clan == null) {
            this.clan = this.dkFriends.getClanManager().getClan(getClanId());
        }
        return this.clan;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        if(this.player == null) {
            this.player = this.dkFriends.getPlayerManager().getPlayer(getPlayerId());
        }
        return this.player;
    }

    @Override
    public UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public DKFriendsPlayer getInviter() {
        if(this.inviter == null) {
            this.inviter = this.dkFriends.getPlayerManager().getPlayer(getInviterId());
        }
        return this.inviter;
    }

    @Override
    public UUID getInviterId() {
        return this.inviterId;
    }

    @Override
    public long getInvitationTime() {
        return this.invitationTime;
    }

    @Override
    public ClanMember accept() {
        return getClan().acceptInvitation(this);
    }

    @Override
    public void deny() {
        getClan().denyInvitation(this);
    }
}
