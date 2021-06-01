package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DKFriendStorage;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.minecraft.commands.DKFriendsCommand;
import net.pretronic.dkfriends.minecraft.commands.clan.ClanCommand;
import net.pretronic.dkfriends.minecraft.commands.friend.FriendCommand;
import net.pretronic.dkfriends.minecraft.commands.party.PartyCommand;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.gui.DKFriendsGui;
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

        DefaultDKFriends dkfriends = new DefaultDKFriends(getDescription().getVersion().getName()
                ,new DKFriendStorage(getDatabaseOrCreate())
                ,getRuntime().getLocal().getEventBus());

        DescriberRegistrar.register();
        registerListeners();
        registerCommands(dkfriends);

        getRuntime().getRegistry().registerService(this,DKFriends.class,dkfriends);
        getRuntime().getPlayerManager().registerPlayerAdapter(DKFriendsPlayer.class, player -> dkfriends.getPlayerManager().getPlayer(player.getUniqueId()));

        DKFriendsGui.register();

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

    private void registerCommands(DefaultDKFriends dkFriends){
        getRuntime().getLocal().getCommandManager().registerCommand(new DKFriendsCommand(this));
        getRuntime().getLocal().getCommandManager().registerCommand(new FriendCommand(this,DKFriendsConfig.COMMAND_FRIEND));
        getRuntime().getLocal().getCommandManager().registerCommand(new ClanCommand(dkFriends, this, DKFriendsConfig.COMMAND_CLAN));
        getRuntime().getLocal().getCommandManager().registerCommand(new PartyCommand(this,DKFriendsConfig.COMMAND_PARTY,dkFriends.getPartyManager()));
    }

}
