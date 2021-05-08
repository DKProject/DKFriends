package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberJoinEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanCancelAbleEvent;

public class DefaultClanMemberJoinEvent extends DefaultClanMemberCancelAbleEvent implements ClanMemberJoinEvent {

    private final ClanMember member;

    public DefaultClanMemberJoinEvent(Clan clan, ClanMember member) {
        super(clan, member);
        this.member = member;
    }

    @Override
    public ClanMember getMember() {
        return this.member;
    }
}
