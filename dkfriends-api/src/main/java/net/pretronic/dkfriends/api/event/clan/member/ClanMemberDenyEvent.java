package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.ClanEvent;

public interface ClanMemberDenyEvent extends ClanEvent {

    ClanInvitation getInvitation();
}