package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberDemoteEvent;

public class DefaultClanMemberDemoteEvent extends DefaultClanMemberEvent implements ClanMemberDemoteEvent {

    private final ClanRole newRole;
    private final ClanMember executor;

    public DefaultClanMemberDemoteEvent(Clan clan, ClanMember member, ClanRole newRole, ClanMember executor) {
        super(clan, member);
        this.newRole = newRole;
        this.executor = executor;
    }

    @Override
    public ClanRole getNewRole() {
        return this.newRole;
    }

    @Override
    public ClanMember getExecutor() {
        return this.executor;
    }

}
