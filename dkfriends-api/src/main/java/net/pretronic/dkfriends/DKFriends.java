package net.pretronic.dkfriends;

import net.pretronic.dkfriends.clan.ClanManager;
import net.pretronic.dkfriends.party.PartyManager;
import net.pretronic.dkfriends.player.DKFriendsPlayerManager;

public interface DKFriends {

    String getVersion();

    DKFriendsPlayerManager getPlayerManager();

    PartyManager getPartyManager();

    ClanManager getClanManager();

}
