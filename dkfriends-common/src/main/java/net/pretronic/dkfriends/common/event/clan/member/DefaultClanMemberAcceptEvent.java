package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberAcceptEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanEvent;

public class DefaultClanMemberAcceptEvent extends DefaultClanEvent implements ClanMemberAcceptEvent {

    private final ClanInvitation invitation;

    public DefaultClanMemberAcceptEvent(Clan clan, ClanInvitation invitation) {
        super(clan);
        this.invitation = invitation;
    }

    @Override
    public ClanInvitation getInvitation() {
        return this.invitation;
    }
}
