package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.Listener;
import net.pretronic.libraries.event.execution.ExecutionType;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.event.player.MinecraftPlayerLogoutEvent;
import org.mcnative.runtime.api.event.player.login.MinecraftPlayerPostLoginEvent;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;

public class PlayerListener {

    @Listener(execution = ExecutionType.ASYNC)
    public void onJoin(MinecraftPlayerPostLoginEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

    }

    @Listener(execution = ExecutionType.ASYNC)
    public void onLogout(MinecraftPlayerLogoutEvent event){
        DKFriendsPlayer player = event.getPlayer().getAs(DKFriendsPlayer.class);

        for (ConnectedMinecraftPlayer online : McNative.getInstance().getLocal().getConnectedPlayers()) {
            if(player.isFriend(online.getUniqueId())){
                //@Todo send offline message
            }
        }
    }

}
