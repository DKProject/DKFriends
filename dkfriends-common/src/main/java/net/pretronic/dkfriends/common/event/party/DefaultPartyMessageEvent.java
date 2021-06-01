package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyMessageEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyMessageEvent implements PartyMessageEvent {

    @Inject
    private transient final DefaultDKFriends dkFriends;

    private final UUID partyId;
    private final UUID senderId;
    private String channel;
    private String message;

    private transient Party party;
    private transient boolean cancelled;

    public DefaultPartyMessageEvent(DefaultDKFriends dkFriends,Party party, UUID senderId, String channel, String message) {
        this.dkFriends = dkFriends;
        this.party = party;
        this.partyId = party.getId();
        this.senderId = senderId;
        this.channel = channel;
        this.message = message;

        this.cancelled = false;
    }

    @Override
    public Party getParty() {
        if(party == null) party = dkFriends.getPartyManager().getParty(partyId);
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
