package net.pretronic.dkfriends.common.event.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.libraries.event.Cancellable;

public class DefaultClanCancelAbleEvent extends DefaultClanEvent implements Cancellable {

    private boolean cancelled;

    public DefaultClanCancelAbleEvent(Clan clan) {
        super(clan);

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
