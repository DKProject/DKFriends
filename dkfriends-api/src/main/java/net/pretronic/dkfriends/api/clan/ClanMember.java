package net.pretronic.dkfriends.api.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface ClanMember {

    UUID getClanId();

    Clan getClan();


    UUID getPlayerId();

    DKFriendsPlayer getPlayer();


    long getJoinTime();


    ClanRole getRole();

    default ClanRole setRole(ClanRole role){
        return setRole(role,null);
    }

    ClanRole setRole(ClanRole role,String cause);

    //Returns promoted role or null if not possible
    ClanRole promote(ClanMember executor);

    ClanRole demote(ClanMember executor);

    boolean canDemote(ClanMember executor);

    boolean canPromote(ClanMember executor);

    boolean leave();


    boolean canKick(ClanMember executor);

    boolean kick(ClanMember executor);
}
