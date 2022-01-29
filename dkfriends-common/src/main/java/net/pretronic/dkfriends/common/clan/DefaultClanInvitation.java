package net.pretronic.dkfriends.common.clan;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultClanInvitation implements ClanInvitation {

    @Inject
    private final transient DKFriends dkFriends;

    private final UUID clanId;
    private final UUID playerId;
    private final UUID inviterId;
    private final long invitationTime;

    private transient Clan clan;
    private transient DKFriendsPlayer player;
    private transient DKFriendsPlayer inviter;

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
        if(this.clan == null) this.clan = this.dkFriends.getClanManager().getClan(getClanId());
        return this.clan;
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        if(this.player == null) this.player = this.dkFriends.getPlayerManager().getPlayer(getPlayerId());
        return this.player;
    }

    @Override
    public UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public DKFriendsPlayer getInviter() {
        if(this.inviter == null) this.inviter = this.dkFriends.getPlayerManager().getPlayer(getInviterId());
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
