package net.pretronic.dkfriends.common.event.clan.member;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.libraries.event.Cancellable;

import java.util.UUID;

public class DefaultClanMemberCancelAbleEvent extends DefaultClanMemberEvent implements Cancellable {

    private boolean cancelled;

    public DefaultClanMemberCancelAbleEvent(Clan clan, ClanMember member) {
        super(clan, member);
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
