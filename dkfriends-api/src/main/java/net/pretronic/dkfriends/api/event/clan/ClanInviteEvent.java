package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.libraries.event.Cancellable;

public interface ClanInviteEvent extends ClanEvent, Cancellable {

    ClanInvitation getInvitation();

}
