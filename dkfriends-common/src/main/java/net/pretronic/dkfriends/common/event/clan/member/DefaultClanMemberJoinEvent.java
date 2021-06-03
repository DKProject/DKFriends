package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberJoinEvent;

import java.util.UUID;

public class DefaultClanMemberJoinEvent implements ClanMemberJoinEvent {

    private final ClanMember member;
    public transient boolean cancelled;

    public DefaultClanMemberJoinEvent(ClanMember member) {
        this.member = member;
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
}
