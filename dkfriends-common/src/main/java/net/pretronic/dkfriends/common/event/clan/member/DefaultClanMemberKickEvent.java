package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberKickEvent;

public class DefaultClanMemberKickEvent extends DefaultClanMemberCancelAbleEvent implements ClanMemberKickEvent {

    private final ClanMember executor;

    public DefaultClanMemberKickEvent(Clan clan, ClanMember member, ClanMember executor) {
        super(clan, member);
        this.executor = executor;
    }

    @Override
    public ClanMember getExecutor() {
        return this.executor;
    }
}
