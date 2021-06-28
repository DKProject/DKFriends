package net.pretronic.dkfriends.api.player.settings.checks;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;

public class PermissionActionCheck implements PlayerActionCheck {

    private final String permission;

    public PermissionActionCheck(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean matches(DKFriendsPlayer player, DKFriendsPlayer target) {
        return target.hasPermission(permission);
    }
}
