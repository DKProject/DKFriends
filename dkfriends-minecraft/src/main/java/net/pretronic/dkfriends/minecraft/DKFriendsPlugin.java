package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.common.DKFriendStorage;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.clan.DefaultClanInvitation;
import net.pretronic.dkfriends.common.clan.DefaultClanMember;
import net.pretronic.dkfriends.common.party.DefaultPartyInvitation;
import net.pretronic.dkfriends.common.party.DefaultPartyMember;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.dkfriends.common.player.friend.DefaultFriendRequest;
import net.pretronic.dkfriends.minecraft.commands.DKFriendsCommand;
import net.pretronic.dkfriends.minecraft.commands.clan.ClanCommand;
import net.pretronic.dkfriends.minecraft.commands.friend.FriendCommand;
import net.pretronic.dkfriends.minecraft.commands.party.PartyCommand;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.gui.DKFriendsGui;
import net.pretronic.dkfriends.minecraft.listeners.PerformListener;
import net.pretronic.dkfriends.minecraft.listeners.PlayerListener;
import net.pretronic.dkfriends.minecraft.listeners.ServiceListener;
import net.pretronic.dkfriends.minecraft.listeners.SyncListener;
import net.pretronic.dkfriends.minecraft.player.MinecraftDKFriendsPlayerManager;
import net.pretronic.libraries.document.DocumentRegistry;
import net.pretronic.libraries.plugin.lifecycle.Lifecycle;
import net.pretronic.libraries.plugin.lifecycle.LifecycleState;
import org.mcnative.licensing.context.platform.McNativeLicenseIntegration;
import org.mcnative.licensing.exceptions.CloudNotCheckoutLicenseException;
import org.mcnative.licensing.exceptions.LicenseNotValidException;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.plugin.MinecraftPlugin;
import org.mcnative.runtime.api.serviceprovider.placeholder.PlaceholderProvider;

import java.util.UUID;

public class DKFriendsPlugin extends MinecraftPlugin {

    public static String RESOURCE_ID ="f9aa2e05-fb5a-407a-844c-175105ec3544";
    public static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhjqoZgV1AEr/TFLmp17qatCK9q2aTQvZAP0YYsPLCqe2mVR6Uq6Re4FUjpUj4ouZB06F+Q8xAG+kYXQ3bvT9Bear6xBWWGyuTaIiJHLevB7eFJDz8K1492PiSrgWymzPz1VnRe1rC4qrLVSv0bXH7F2ioCCNftvAiv7Zata/GObcwy8n5aCWeIaE31+t7YmA8sEceiSQKtjpImx3MnqDLP3sq2kLLxBwTuKR1EsBKEcp+ayFibEaoHLLTmphMSsnwbfxtydQIjIOgJIQtHt/O+Gr8um19tyUrOo94mcnEs4kprnU1m9+kY5COt/cgZ+/tTCX3swvuJ2zV3+xrD7s5wIDAQAB";

    @Lifecycle(state = LifecycleState.LOAD)
    public void onLoad(LifecycleState state){
        getLogger().info("DKFriends is starting, please wait..");

        try{
            McNativeLicenseIntegration.newContext(this,RESOURCE_ID,PUBLIC_KEY).verifyOrCheckout();
        }catch (LicenseNotValidException | CloudNotCheckoutLicenseException e){
            getLogger().error("--------------------------------");
            getLogger().error("-> Invalid license");
            getLogger().error("-> Error: "+e.getMessage());
            getLogger().error("--------------------------------");
            getLogger().info("DKFriends is shutting down");
            getLoader().shutdown();
            return;
        }

        getConfiguration().load(DKFriendsConfig.class);

        DefaultDKFriends dkfriends = new DefaultDKFriends(getDescription().getVersion().getName()
                ,new DKFriendStorage(getDatabaseOrCreate())
                ,new MinecraftDKFriendsPlayerManager()
                ,getRuntime().getLocal().getEventBus());

        getRuntime().getRegistry().registerService(this,DKFriends.class,dkfriends);
        getRuntime().getPlayerManager().registerPlayerAdapter(DKFriendsPlayer.class, player -> dkfriends.getPlayerManager().getPlayer(player.getUniqueId()));
        getRuntime().getRegistry().getService(PlaceholderProvider.class).registerPlaceHolders(this,"dkfriends",new DKFriendsPlaceholders());

        DescriberRegistrar.register();
        registerListeners(dkfriends);
        registerCommands(dkfriends);
        registerDocumentAdapters();

        if(McNative.getInstance().isNetworkAvailable()){
            getRuntime().getNetwork().registerStatusCallback(this,((MinecraftDKFriendsPlayerManager)dkfriends.getPlayerManager()).getPlayerCache());
            getRuntime().getNetwork().getMessenger().registerSynchronizingChannel("dkfriends_players", this, UUID.class,((MinecraftDKFriendsPlayerManager)dkfriends.getPlayerManager()).getPlayerCache());
        }

        if(McNative.getInstance().getPlatform().isService()) {
            DKFriendsGui.register(dkfriends);
            getRuntime().getLocal().getEventBus().subscribe(this, new ServiceListener());
        }

        getLogger().info("DKFriends started successfully");
    }

    private void registerListeners(DKFriends dkfriends){
        if(getRuntime().isNetworkAvailable()){
            getRuntime().getNetwork().getEventBus().subscribe(this,new SyncListener(dkfriends));
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

    private void registerDocumentAdapters(){
        DocumentRegistry.getDefaultContext().registerMappingAdapter(Friend.class, DefaultFriend.class);
        DocumentRegistry.getDefaultContext().registerMappingAdapter(FriendRequest.class, DefaultFriendRequest.class);
        DocumentRegistry.getDefaultContext().registerMappingAdapter(PartyInvitation.class, DefaultPartyInvitation.class);
        DocumentRegistry.getDefaultContext().registerMappingAdapter(PartyMember.class, DefaultPartyMember.class);
        DocumentRegistry.getDefaultContext().registerMappingAdapter(ClanMember.class, DefaultClanMember.class);
        DocumentRegistry.getDefaultContext().registerMappingAdapter(ClanInvitation.class, DefaultClanInvitation.class);
    }


}
