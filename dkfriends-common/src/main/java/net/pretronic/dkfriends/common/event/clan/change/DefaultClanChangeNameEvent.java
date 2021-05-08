package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeNameEvent;
import net.pretronic.libraries.utility.Validate;

public class DefaultClanChangeNameEvent extends DefaultClanChangeEvent implements ClanChangeNameEvent {

    private String newName;

    public DefaultClanChangeNameEvent(Clan clan, String newName) {
        super(clan);
        this.newName = newName;
    }

    @Override
    public String getOldName() {
        return getClan().getName();
    }

    @Override
    public String getNewName() {
        return this.newName;
    }

    @Override
    public void setNewName(String newName) {
        Validate.notNull(newName);
        this.newName = newName;
    }
}
