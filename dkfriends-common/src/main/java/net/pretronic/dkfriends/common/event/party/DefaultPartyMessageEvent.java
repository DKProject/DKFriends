package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyMessageEvent;
import net.pretronic.dkfriends.api.party.Party;

public class DefaultPartyMessageEvent implements PartyMessageEvent {

    private final Party party;
    private String channel;
    private String message;
    private boolean cancelled;

    public DefaultPartyMessageEvent(Party party, String channel, String message) {
        this.party = party;
        this.channel = channel;
        this.message = message;

        this.cancelled = false;
    }

    @Override
    public Party getParty() {
        return party;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
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
