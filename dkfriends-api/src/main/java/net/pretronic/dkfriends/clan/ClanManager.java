package net.pretronic.dkfriends.clan;

import java.util.Collection;
import java.util.UUID;

public interface ClanManager {

    Collection<Clan> getClans();

    Clan getClan(UUID id);

    Clan getClan(String name);

    Clan getClanByTag(String tag);

    Clan getClanByPlayer(UUID playerId);


    Clan createClan(String name, String tag);


    void deleteClan(UUID clanId);

    void deleteClan(Clan clan);

}
