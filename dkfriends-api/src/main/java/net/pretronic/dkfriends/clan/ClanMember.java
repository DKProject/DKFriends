package net.pretronic.dkfriends.clan;

import java.util.UUID;

public interface ClanMember {

    UUID getClanId();

    Clan getClan();

    UUID getPlayerId();

    long getJoinTime();


    ClanRole getRole();

    void setRole(ClanRole role);


    void leave();

}
