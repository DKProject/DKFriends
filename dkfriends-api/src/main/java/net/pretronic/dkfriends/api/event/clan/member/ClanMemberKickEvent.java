package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.libraries.event.Cancellable;

public interface ClanMemberKickEvent extends ClanMemberEvent, Cancellable {

    ClanMember getExecutor();
}
