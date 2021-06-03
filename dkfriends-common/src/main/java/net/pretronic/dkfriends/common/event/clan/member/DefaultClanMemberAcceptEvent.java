package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberInvitationAcceptEvent;

import java.util.UUID;

public class DefaultClanMemberAcceptEvent implements ClanMemberInvitationAcceptEvent {

    private final ClanInvitation invitation;

    public DefaultClanMemberAcceptEvent(ClanInvitation invitation) {
        this.invitation = invitation;
    }

    @Override
    public ClanInvitation getInvitation() {
        return this.invitation;
    }

    @Override
    public UUID getClanId() {
        return invitation.getClanId();
    }

    @Override
    public Clan getClan() {
        return invitation.getClan();
    }
}
