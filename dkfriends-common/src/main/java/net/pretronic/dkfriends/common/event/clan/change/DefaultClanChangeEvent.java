package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanCancelAbleEvent;

public class DefaultClanChangeEvent extends DefaultClanCancelAbleEvent implements ClanChangeEvent {

    public DefaultClanChangeEvent(Clan clan) {
        super(clan);
    }
}
