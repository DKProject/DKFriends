package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.ClanMessageEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultClanMessageEvent implements ClanMessageEvent {

    private final transient DKFriends dkfriends;

    private final UUID clanId;
    private final UUID senderId;
    private String channel;
    private String message;

    private transient Clan clan;
    private transient DKFriendsPlayer sender;
    private transient boolean cancelled;

    public DefaultClanMessageEvent(DKFriends dkfriends,Clan clan,DKFriendsPlayer sender, String channel, String message) {
        this.dkfriends = dkfriends;
        this.senderId = sender.getId();
        this.sender = sender;
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
    public UUID getSenderId() {
        return senderId;
    }

    @Override
    public DKFriendsPlayer getSender() {
        if(sender == null) sender = dkfriends.getPlayerManager().getPlayer(senderId);
        return sender;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
