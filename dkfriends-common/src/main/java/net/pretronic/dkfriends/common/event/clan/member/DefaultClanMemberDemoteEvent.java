package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberDemoteEvent;

import java.util.UUID;

public class DefaultClanMemberDemoteEvent extends DefaultClanMemberEvent implements ClanMemberDemoteEvent {

    private final ClanRole newRole;

    public DefaultClanMemberDemoteEvent(Clan clan, ClanMember member, ClanRole newRole) {
        super(clan, member);
        this.newRole = newRole;
    }

    @Override
    public ClanRole getNewRole() {
        return this.newRole;
    }
}
