package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanEvent;

import java.util.UUID;

public class DefaultClanMemberEvent extends DefaultClanEvent implements ClanMemberEvent {

    private final ClanMember member;

    public DefaultClanMemberEvent(Clan clan, ClanMember member) {
        super(clan);
        this.member = member;
    }

    @Override
    public ClanMember getMember() {
        return this.member;
    }

    @Override
    public UUID getMemberId() {
        return this.member.getPlayerId();
    }
}
