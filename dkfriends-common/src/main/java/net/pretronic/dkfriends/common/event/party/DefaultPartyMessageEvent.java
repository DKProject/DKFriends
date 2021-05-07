package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyMessageEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;

import java.util.UUID;

public class DefaultPartyMessageEvent implements PartyMessageEvent {

    private final DefaultDKFriends dkFriends;
    private final Party party;
    private final UUID senderId;
    private String channel;
    private String message;
    private boolean cancelled;

    public DefaultPartyMessageEvent(DefaultDKFriends dkFriends,Party party, UUID senderId, String channel, String message) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.senderId = senderId;
        this.channel = channel;
        this.message = message;

        this.cancelled = false;
    }

    @Override
    public Party getParty() {
        return party;
    }

    @Override
    public UUID getSenderId() {
        return senderId;
    }

    @Override
    public DKFriendsPlayer getSender() {
        return dkFriends.getPlayerManager().getPlayer(senderId);
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
