package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyLeaveEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyLeaveEvent implements PartyLeaveEvent {

    private final PartyMember member;
    private final String cause;

    private boolean cancelled;

    public DefaultPartyLeaveEvent(PartyMember member,String cause) {
        this.member = member;
        this.cause = cause;
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
    public UUID getPartyId() {
        return member.getPartyId();
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
    public String getCause() {
        return cause;
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
