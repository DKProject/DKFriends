package net.pretronic.dkfriends.api.player.settings.checks;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;

public class ClanPlayerActionCheck implements PlayerActionCheck {

    @Override
    public boolean matches(DKFriendsPlayer player, DKFriendsPlayer target) {
        Clan clan = player.getClan();
        return clan != null && clan.isMember(target.getId());
    }
}
