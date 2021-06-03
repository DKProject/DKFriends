package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberInviteEvent;

import java.util.UUID;

public class DefaultClanInviteEvent implements ClanMemberInviteEvent {

    private final ClanInvitation invitation;
    private transient boolean cancelled;

    public DefaultClanInviteEvent(ClanInvitation invitation) {
        this.invitation = invitation;
        this.cancelled = false;
    }

    @Override
    public ClanInvitation getInvitation() {
        return invitation;
    }

    @Override
    public UUID getClanId() {
        return invitation.getClanId();
    }

    @Override
    public Clan getClan() {
        return invitation.getClan();
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
