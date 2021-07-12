package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.event.EventPriority;
import net.pretronic.libraries.event.Listener;
import net.pretronic.libraries.event.execution.ExecutionType;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.event.player.MinecraftPlayerLogoutEvent;
import org.mcnative.runtime.api.event.player.login.MinecraftPlayerPostLoginEvent;
import org.mcnative.runtime.api.event.server.MinecraftPlayerServerSwitchEvent;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerListener {

    @Listener(execution = ExecutionType.ASYNC,priority = EventPriority.HIGHEST)
    public void onJoin(MinecraftPlayerPostLoginEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

        List<OnlineMinecraftPlayer> onlineFriends = new ArrayList<>();

        Clan clan = player.getClan();

        for (OnlineMinecraftPlayer online : getOnlinePlayers()) {
            if(player.isFriend(online.getUniqueId())){
                onlineFriends.add(online);
                if(online.getAs(DKFriendsPlayer.class).isActionAllow(PlayerSettings.FRIEND_NOTIFICATIONS,player)){
                    online.sendMessage(Messages.FRIEND_LOGIN, VariableSet.create()
                            .addDescribed("player",event.getOnlinePlayer()));
                }
            }else if (clan != null && clan.isMember(online.getUniqueId()) && !online.getUniqueId().equals(event.getPlayer().getUniqueId())){
                if(online.getAs(DKFriendsPlayer.class).isActionAllow(PlayerSettings.CLAN_NOTIFICATIONS,player)){
                    online.sendMessage(Messages.CLAN_LOGIN, VariableSet.create()
                            .addDescribed("player",event.getOnlinePlayer()));
                }
            }
        }

        int requests = player.getFriendRequests().size();
        if(requests > 0){
            event.getOnlinePlayer().sendMessage(Messages.FRIEND_LOGIN_INFO_REQUESTS,VariableSet.create()
                    .add("amount",requests));
        }

        if(onlineFriends.size() == 1){
            event.getOnlinePlayer().sendMessage(Messages.FRIEND_LOGIN_INFO_ONE,VariableSet.create()
                    .addDescribed("player",onlineFriends.get(0)));
        }else if(onlineFriends.size() == 2){
            event.getOnlinePlayer().sendMessage(Messages.FRIEND_LOGIN_INFO_TWO,VariableSet.create()
                    .addDescribed("player",onlineFriends.get(0))
                    .addDescribed("player2",onlineFriends.get(1)));
        }else if(onlineFriends.size() == 3){
            event.getOnlinePlayer().sendMessage(Messages.FRIEND_LOGIN_INFO_THREE,VariableSet.create()
                    .addDescribed("player",onlineFriends.get(0))
                    .addDescribed("player2",onlineFriends.get(1))
                    .addDescribed("player3",onlineFriends.get(2)));
        }else if(onlineFriends.size() > 3){
            event.getOnlinePlayer().sendMessage(Messages.FRIEND_LOGIN_INFO_MORE,VariableSet.create()
                    .addDescribed("player",onlineFriends.get(0))
                    .addDescribed("player2",onlineFriends.get(1))
                    .addDescribed("more",onlineFriends.size()-2));
        }

        //@Todo send clan notification
    }

    @Listener(execution = ExecutionType.ASYNC)
    public void onLogout(MinecraftPlayerLogoutEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

        player.leaveParty();

        Clan clan = player.getClan();

        for (OnlineMinecraftPlayer online : getOnlinePlayers()) {
            if(player.isFriend(online.getUniqueId())){
                if(online.getAs(DKFriendsPlayer.class).isActionAllow(PlayerSettings.FRIEND_NOTIFICATIONS,player)){
                    online.sendMessage(Messages.FRIEND_LOGOUT, VariableSet.create()
                            .addDescribed("player",event.getOnlinePlayer()));
                }
            }else if (clan != null && clan.isMember(online.getUniqueId())){
                if(online.getAs(DKFriendsPlayer.class).isActionAllow(PlayerSettings.CLAN_NOTIFICATIONS,player)){
                    online.sendMessage(Messages.CLAN_LOGOUT, VariableSet.create()
                            .addDescribed("player",event.getOnlinePlayer()));
                }
            }
        }
    }

    @Listener(execution = ExecutionType.ASYNC)
    public void onServerSwitch(MinecraftPlayerServerSwitchEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);
        Party party = player.getParty();
        if(party != null && party.getMember(event.getPlayer().getUniqueId()).getRole() == PartyRole.LEADER){
            party.teleport(event.getTo().getName());
        }
    }

    private Collection<OnlineMinecraftPlayer> getOnlinePlayers(){
        if(McNative.getInstance().isNetworkAvailable()){
            return McNative.getInstance().getNetwork().getOnlinePlayers();
        }else{
            return McNative.getInstance().getLocal().getOnlinePlayers();
        }
    }
}
