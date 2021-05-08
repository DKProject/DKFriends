package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanMessageEvent;

public class DefaultClanMessageEvent extends DefaultClanEvent implements ClanMessageEvent {

    private String channel;
    private String message;

    public DefaultClanMessageEvent(Clan clan, String channel, String message) {
        super(clan);
        this.channel = channel;
        this.message = message;
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