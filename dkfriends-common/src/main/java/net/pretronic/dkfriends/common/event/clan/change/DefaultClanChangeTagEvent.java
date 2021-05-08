package net.pretronic.dkfriends.common.event.clan.change;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeTagEvent;
import net.pretronic.libraries.utility.Validate;

public class DefaultClanChangeTagEvent extends DefaultClanChangeEvent implements ClanChangeTagEvent {

    private String newTag;

    public DefaultClanChangeTagEvent(Clan clan, String newTag) {
        super(clan);

        this.newTag = newTag;
    }

    @Override
    public String getOldTag() {
        return getClan().getTag();
    }

    @Override
    public String getNewTag() {
        return newTag;
    }

    @Override
    public void setNewTag(String newTag) {
        Validate.notNull(newTag);
        this.newTag = newTag;
    }
}
