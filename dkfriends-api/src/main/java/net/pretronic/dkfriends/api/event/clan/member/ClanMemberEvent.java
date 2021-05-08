package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.ClanEvent;

import java.util.UUID;

public interface ClanMemberEvent extends ClanEvent {

    ClanMember getMember();

    UUID getMemberId();
}
