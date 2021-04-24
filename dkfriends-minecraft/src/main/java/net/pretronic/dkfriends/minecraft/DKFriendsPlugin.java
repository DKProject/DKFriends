package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DKFriendStorage;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.minecraft.commands.friend.FriendCommand;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.listeners.PerformListener;
import net.pretronic.dkfriends.minecraft.listeners.PlayerListener;
import net.pretronic.dkfriends.minecraft.listeners.SyncListener;
import net.pretronic.libraries.plugin.lifecycle.Lifecycle;
import net.pretronic.libraries.plugin.lifecycle.LifecycleState;
import org.mcnative.runtime.api.plugin.MinecraftPlugin;

public class DKFriendsPlugin extends MinecraftPlugin {

    @Lifecycle(state = LifecycleState.LOAD)
    public void onLoad(LifecycleState state){
        getLogger().info("DKFriends is starting, please wait..");

        getConfiguration().load(DKFriendsConfig.class);

        DKFriends dkfriends = new DefaultDKFriends(getDescription().getVersion().getName()
                ,new DKFriendStorage(getDatabase())
                ,getRuntime().getLocal().getEventBus());

        registerListeners();
        registerCommands();

        getRuntime().getRegistry().registerService(this,DKFriends.class,dkfriends);
        getRuntime().getPlayerManager().registerPlayerAdapter(DKFriendsPlayer.class, player -> dkfriends.getPlayerManager().getPlayer(player.getUniqueId()));

        getLogger().info("DKFriends started successfully");
    }

    private void registerListeners(){
        if(getRuntime().isNetworkAvailable()){
            getRuntime().getNetwork().getEventBus().subscribe(this,new SyncListener());
            if(getRuntime().getPlatform().isProxy()){
                getRuntime().getLocal().getEventBus().subscribe(this,new PlayerListener());
                getRuntime().getNetwork().getEventBus().subscribe(this,new PerformListener());
            }
        }else{
            getRuntime().getLocal().getEventBus().subscribe(this,new PlayerListener());
            getRuntime().getLocal().getEventBus().subscribe(this,new PerformListener());
        }
    }

    private void registerCommands(){
        getRuntime().getLocal().getCommandManager().registerCommand(new FriendCommand(this,DKFriendsConfig.COMMAND_FRIENDS));
    }
}
