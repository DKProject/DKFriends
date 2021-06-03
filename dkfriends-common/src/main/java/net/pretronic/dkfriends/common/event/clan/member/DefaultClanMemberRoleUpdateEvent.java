package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberRoleUpdateEvent;

import java.util.UUID;

public class DefaultClanMemberRoleUpdateEvent implements ClanMemberRoleUpdateEvent {

    private final ClanMember member;
    private final ClanRole newRole;
    public final String cause;
    public transient boolean cancelled;

    public DefaultClanMemberRoleUpdateEvent(ClanMember member, ClanRole newRole,String cause) {
        this.member = member;
        this.newRole = newRole;
        this.cause = cause;
        this.cancelled = false;
    }

    @Override
    public String getCause() {
        return cause;
    }

    @Override
    public ClanRole getNewRole() {
        return this.newRole;
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
    public ClanMember getMember() {
        return member;
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
