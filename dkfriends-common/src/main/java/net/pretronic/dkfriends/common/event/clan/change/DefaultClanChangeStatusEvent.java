package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeStatusEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;

public class DefaultClanChangeStatusEvent implements ClanChangeStatusEvent {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private String newStatus;

    private transient Clan clan;
    private transient boolean cancelled;

    public DefaultClanChangeStatusEvent(DKFriends dkfriends,Clan clan, String newStatus) {
        this.dkfriends = dkfriends;
        this.clanId = clan.getId();
        this.newStatus = newStatus;
        this.clan = clan;
        this.cancelled = false;
    }

    @Override
    public String getOldStatus() {
        return getClan().getStatus();
    }

    @Override
    public String getNewStatus() {
        return this.newStatus;
    }

    @Override
    public void setNewStatus(String newStatus) {
        Validate.notNull(newStatus);
        this.newStatus = newStatus;
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
