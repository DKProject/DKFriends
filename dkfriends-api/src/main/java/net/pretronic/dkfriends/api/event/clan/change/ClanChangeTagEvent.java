package net.pretronic.dkfriends.api.event.clan.change;

public interface ClanChangeTagEvent extends ClanChangeEvent {

    String getOldTag();

    String getNewTag();

    void setNewTag(String newTag);
}
