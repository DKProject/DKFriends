package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanEvent;

public class DefaultClanEvent implements ClanEvent {

    private final Clan clan;

    public DefaultClanEvent(Clan clan) {
        this.clan = clan;
    }

    @Override
    public Clan getClan() {
        return this.clan;
    }
}
