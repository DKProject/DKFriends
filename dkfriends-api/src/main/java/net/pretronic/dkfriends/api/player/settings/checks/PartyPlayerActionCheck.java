package net.pretronic.dkfriends.api.player.settings.checks;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;

public class PartyPlayerActionCheck implements PlayerActionCheck {

    @Override
    public boolean matches(DKFriendsPlayer player, DKFriendsPlayer target) {
        Party party = player.getParty();
        return party != null && party.isMember(target.getId());
    }
}
