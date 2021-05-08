package net.pretronic.dkfriends.api.clan;

import java.util.Collection;
import java.util.UUID;

public interface ClanManager {

    Collection<Clan> getClans();

    Clan getClan(UUID id);

    Clan getClan(String name);

    Clan getClanByTag(String tag);

    Clan getClanByPlayer(UUID playerId);

    /*
    Returns null, if clan already exists.
     */
    Clan createClan(String name, String tag);

    boolean existClan(String name, String tag);


    void deleteClan(UUID clanId);

    boolean deleteClan(Clan clan);

}
