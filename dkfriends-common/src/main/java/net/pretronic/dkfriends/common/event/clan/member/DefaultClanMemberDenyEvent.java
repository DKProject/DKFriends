package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberDenyEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanEvent;

public class DefaultClanMemberDenyEvent extends DefaultClanEvent implements ClanMemberDenyEvent {

    private final ClanInvitation invitation;

    public DefaultClanMemberDenyEvent(Clan clan, ClanInvitation invitation) {
        super(clan);
        this.invitation = invitation;
    }

    @Override
    public ClanInvitation getInvitation() {
        return this.invitation;
    }
}
