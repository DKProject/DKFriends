package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.DKFriendsEvent;

public interface ClanEvent extends DKFriendsEvent {

    Clan getClan();
}
