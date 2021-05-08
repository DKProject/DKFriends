package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberLeaveEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanCancelAbleEvent;

public class DefaultClanMemberLeaveEvent extends DefaultClanMemberCancelAbleEvent implements ClanMemberLeaveEvent {

    private final ClanMember member;

    public DefaultClanMemberLeaveEvent(Clan clan, ClanMember member) {
        super(clan, member);
        this.member = member;
    }

    @Override
    public ClanMember getMember() {
        return this.member;
    }
}
