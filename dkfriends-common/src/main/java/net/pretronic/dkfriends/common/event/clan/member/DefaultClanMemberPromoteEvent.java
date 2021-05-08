package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberPromoteEvent;

import java.util.UUID;

public class DefaultClanMemberPromoteEvent extends DefaultClanMemberEvent implements ClanMemberPromoteEvent {

    private final ClanRole newRole;

    public DefaultClanMemberPromoteEvent(Clan clan, ClanMember member, ClanRole newRole) {
        super(clan, member);
        this.newRole = newRole;
    }

    @Override
    public ClanRole getNewRole() {
        return this.newRole;
    }

    @Override
    public ClanMember getExecutor() {
        return null;
    }

    @Override
    public UUID getMemberId() {
        return getMember().getPlayerId();
    }
}
