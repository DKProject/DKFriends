package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeTagEvent;
import net.pretronic.libraries.event.injection.annotations.Inject;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;

public class DefaultClanChangeTagEvent implements ClanChangeTagEvent {

    @Inject
    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private String newTag;

    private transient Clan clan;
    private transient boolean cancelled;

    public DefaultClanChangeTagEvent(DKFriends dkfriends,Clan clan, String newTag) {
        this.dkfriends = dkfriends;
        this.clanId = clan.getId();
        this.newTag = newTag;
        this.clan = clan;
        this.cancelled = false;
    }

    @Override
    public String getOldTag() {
        return getClan().getTag();
    }

    @Override
    public String getNewTag() {
        return newTag;
    }

    @Override
    public void setNewTag(String newTag) {
        Validate.notNull(newTag);
        this.newTag = newTag;
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
