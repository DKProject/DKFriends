package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeNameEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;

public class DefaultClanChangeNameEvent implements ClanChangeNameEvent {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private String newName;

    private transient Clan clan;
    private transient boolean cancelled;

    public DefaultClanChangeNameEvent(DKFriends dkfriends,Clan clan, String newName) {
        this.dkfriends = dkfriends;
        this.clanId = clan.getId();
        this.newName = newName;
        this.clan = clan;
        this.cancelled = false;
    }

    @Override
    public String getOldName() {
        return getClan().getName();
    }

    @Override
    public String getNewName() {
        return this.newName;
    }

    @Override
    public void setNewName(String newName) {
        Validate.notNull(newName);
        this.newName = newName;
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
