package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.libraries.plugin.lifecycle.Lifecycle;
import net.pretronic.libraries.plugin.lifecycle.LifecycleState;
import org.mcnative.runtime.api.plugin.MinecraftPlugin;

public class DKFriendsPlugin extends MinecraftPlugin {

    @Lifecycle(state = LifecycleState.LOAD)
    public void onLoad(LifecycleState state){
        getLogger().info("DKFriends is starting, please wait..");

        //@Todo license

        getConfiguration().load(DKFriendsConfig.class);


        getLogger().info("DKFriends started successfully");
    }

}
