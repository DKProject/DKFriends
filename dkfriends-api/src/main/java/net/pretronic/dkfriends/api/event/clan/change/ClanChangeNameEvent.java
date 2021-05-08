package net.pretronic.dkfriends.api.event.clan.change;

public interface ClanChangeNameEvent extends ClanChangeEvent {

    String getOldName();

    String getNewName();

    void setNewName(String newName);
}
