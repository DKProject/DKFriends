package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyJoinEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyJoinEvent implements PartyJoinEvent {

    private final PartyMember member;

    private boolean cancelled;

    public DefaultPartyJoinEvent(PartyMember member) {
        this.member = member;
        this.cancelled = false;
    }

    @Override
    public UUID getPlayerId() {
        return member.getPlayerId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return member.getPlayer();
    }

    @Override
    public Party getParty() {
        return member.getParty();
    }

    @Override
    public PartyMember getMember() {
        return member;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
