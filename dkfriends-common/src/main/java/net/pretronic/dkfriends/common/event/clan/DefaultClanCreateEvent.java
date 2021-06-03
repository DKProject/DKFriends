package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanCreateEvent;

import java.util.UUID;

public class DefaultClanCreateEvent implements ClanCreateEvent {

    private final UUID clanId;
    private String name;
    private String tag;
    private transient boolean cancelled;

    public DefaultClanCreateEvent(UUID clanId, String name, String tag) {
        this.clanId = clanId;
        this.name = name;
        this.tag = tag;
        this.cancelled = false;
    }

    @Override
    public UUID getClanId() {
        return clanId;
    }

    @Override
    public Clan getClan() {
        throw new UnsupportedOperationException("Clan is not available yet");
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }
}
