package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberLeaveEvent;

import java.util.UUID;

public class DefaultClanMemberLeaveEvent implements ClanMemberLeaveEvent {

    private final ClanMember member;
    private final String cause;
    private transient boolean cancelled;

    public DefaultClanMemberLeaveEvent(ClanMember member,String cause) {
        this.member = member;
        this.cause = cause;
        this.cancelled = false;
    }

    @Override
    public ClanMember getMember() {
        return this.member;
    }

    @Override
    public UUID getClanId() {
        return member.getClanId();
    }

    @Override
    public Clan getClan() {
        return member.getClan();
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
    public String getCause() {
        return cause;
    }
}
