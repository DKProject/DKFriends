package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanDeleteEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultClanDeleteEvent implements ClanDeleteEvent {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID clanId;

    private transient Clan clan;
    private transient boolean cancelled;

    public DefaultClanDeleteEvent(DKFriends dkfriends,UUID clanId) {
        this.dkfriends = dkfriends;
        this.clanId =clanId;
        this.cancelled = false;
    }

    @Override
    public UUID getClanId() {
        return clanId;
    }

    @Override
    public Clan getClan() {
        if(clan == null) clan = dkfriends.getClanManager().getClan(clanId);
        return clan;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
