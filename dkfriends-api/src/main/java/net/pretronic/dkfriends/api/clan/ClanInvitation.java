package net.pretronic.dkfriends.api.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface ClanInvitation {

    UUID getClanId();

    Clan getClan();

    DKFriendsPlayer getPlayer();

    UUID getPlayerId();

    DKFriendsPlayer getInviter();

    UUID getInviterId();

    long getInvitationTime();


    ClanMember accept();

    void deny();
}
