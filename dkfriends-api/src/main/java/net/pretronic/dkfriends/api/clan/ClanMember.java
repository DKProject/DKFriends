package net.pretronic.dkfriends.api.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface ClanMember {

    Clan getClan();


    UUID getPlayerId();

    DKFriendsPlayer getPlayer();


    long getJoinTime();


    ClanRole getRole();

    ClanRole setRole(ClanRole role);

    //Returns promoted role or null if not possible
    ClanRole promote(ClanMember player);

    ClanRole demote(ClanMember player);

    boolean canDemote(ClanMember player);

    boolean canPromote(ClanMember player);

    boolean leave();


    boolean canKick(ClanMember executor);

    boolean kick(ClanMember executor);
}
