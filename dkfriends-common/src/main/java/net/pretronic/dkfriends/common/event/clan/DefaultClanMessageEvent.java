package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanMessageEvent;

import java.util.UUID;

public class DefaultClanMessageEvent implements ClanMessageEvent {

    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private String channel;
    private String message;

    private transient Clan clan;

    public DefaultClanMessageEvent(DKFriends dkfriends,Clan clan, String channel, String message) {
        this.dkfriends = dkfriends;
        this.clanId = clan.getId();
        this.channel = channel;
        this.message = message;
        this.clan = clan;
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
    public String getChannel() {
        return this.channel;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
