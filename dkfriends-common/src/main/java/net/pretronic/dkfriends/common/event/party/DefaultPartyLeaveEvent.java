package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.party.PartyLeaveEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultPartyLeaveEvent implements PartyLeaveEvent {

    @Inject
    private transient final DKFriends dkFriends;

    private final PartyMember member;
    private final String cause;
    private final UUID executorId;

    private transient boolean cancelled;

    public DefaultPartyLeaveEvent(DKFriends dkFriends,PartyMember member,String cause,UUID executorId) {
        this.dkFriends = dkFriends;
        this.member = member;
        this.cause = cause;
        this.executorId = executorId;
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
    public UUID getExecutorId() {
        return executorId;
    }

    @Override
    public DKFriendsPlayer getExecutor() {
        if(executorId == null) return null;
        return dkFriends.getPlayerManager().getPlayer(executorId);
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
