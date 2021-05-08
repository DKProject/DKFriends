package net.pretronic.dkfriends.api.event.clan.change;

public interface ClanChangeStatusEvent extends ClanChangeEvent {

    String getOldStatus();

    String getNewStatus();

    void setNewStatus(String newStatus);
}
