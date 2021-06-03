package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.DKFriendsEvent;

import java.util.UUID;

public interface ClanEvent extends DKFriendsEvent {

    UUID getClanId();

    Clan getClan();
}
