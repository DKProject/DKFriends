package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeStatusEvent;
import net.pretronic.libraries.utility.Validate;

public class DefaultClanChangeStatusEvent extends DefaultClanChangeEvent implements ClanChangeStatusEvent {

    private String newStatus;

    public DefaultClanChangeStatusEvent(Clan clan, String newStatus) {
        super(clan);

        this.newStatus = newStatus;
    }

    @Override
    public String getOldStatus() {
        return getClan().getStatus();
    }

    @Override
    public String getNewStatus() {
        return this.newStatus;
    }

    @Override
    public void setNewStatus(String newStatus) {
        Validate.notNull(newStatus);
        this.newStatus = newStatus;
    }
}
