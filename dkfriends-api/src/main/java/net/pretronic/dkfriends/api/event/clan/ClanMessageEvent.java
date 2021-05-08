package net.pretronic.dkfriends.api.event.clan;

public interface ClanMessageEvent extends ClanEvent {

    String getChannel();

    void setChannel(String channel);

    String getMessage();

    void setMessage(String message);
}
