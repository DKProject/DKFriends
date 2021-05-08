package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;

public interface ClanMemberDemoteEvent extends ClanMemberEvent {

    ClanRole getNewRole();

    ClanMember getExecutor();
}
