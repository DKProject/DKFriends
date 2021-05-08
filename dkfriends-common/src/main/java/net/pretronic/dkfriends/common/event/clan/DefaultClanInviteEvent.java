package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.ClanInviteEvent;

public class DefaultClanInviteEvent extends DefaultClanCancelAbleEvent implements ClanInviteEvent {

    private final ClanInvitation invitation;

    public DefaultClanInviteEvent(ClanInvitation invitation) {
        super(invitation.getClan());
        this.invitation = invitation;
    }

    @Override
    public ClanInvitation getInvitation() {
        return invitation;
    }
}
