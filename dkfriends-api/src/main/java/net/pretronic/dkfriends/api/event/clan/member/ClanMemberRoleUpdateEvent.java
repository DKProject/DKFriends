package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.libraries.event.Cancellable;

public interface ClanMemberRoleUpdateEvent extends ClanMemberEvent, Cancellable {

    ClanRole getNewRole();
}
