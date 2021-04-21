package net.pretronic.dkfriends.api;

import net.pretronic.dkfriends.api.clan.ClanManager;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;

public interface DKFriends {

    String getVersion();

    DKFriendsPlayerManager getPlayerManager();

    PartyManager getPartyManager();

    ClanManager getClanManager();

}
