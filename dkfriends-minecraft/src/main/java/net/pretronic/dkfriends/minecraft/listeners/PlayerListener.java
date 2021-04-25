package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.event.Listener;
import net.pretronic.libraries.event.execution.ExecutionType;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.event.player.MinecraftPlayerLogoutEvent;
import org.mcnative.runtime.api.event.player.login.MinecraftPlayerPostLoginEvent;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerListener {

    @Listener(execution = ExecutionType.ASYNC)
    public void onJoin(MinecraftPlayerPostLoginEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

        Collection<OnlineMinecraftPlayer> onlineFriends = new ArrayList<>();
        for (OnlineMinecraftPlayer online : McNative.getInstance().getNetwork().getOnlinePlayers()) {
            if(player.isFriend(online.getUniqueId())){
                online.sendMessage(Messages.FRIEND_LOGIN, VariableSet.create()
                        .addDescribed("friend",event.getOnlinePlayer()));
            }
        }


    }

    @Listener(execution = ExecutionType.ASYNC)
    public void onLogout(MinecraftPlayerLogoutEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

        for (OnlineMinecraftPlayer online : McNative.getInstance().getNetwork().getOnlinePlayers()) {
            if(player.isFriend(online.getUniqueId())){
                online.sendMessage(Messages.FRIEND_LOGOUT, VariableSet.create()
                        .addDescribed("friend",event.getOnlinePlayer()));
            }
        }
    }

}
