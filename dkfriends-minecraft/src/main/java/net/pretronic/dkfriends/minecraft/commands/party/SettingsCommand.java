package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SettingsCommand extends BasicCommand implements Completable {

    private static final Map<String,String> MAPPER = new HashMap<>();

    static {
        MAPPER.put("allow-invitation",PlayerSettings.PARTY_ALLOW_INVITATIONS);
    }

    public SettingsCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("settings","setting","s"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_PARTY_HELP);
            return;
        }

        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        String setting = MAPPER.get(arguments[0]);
        String value = arguments[1];
        String group = "all";

        if(arguments.length > 2){
            group = arguments[2];
            player.setActionSetting(setting,group,Boolean.parseBoolean(value));
        }else{
            player.setActionSetting(setting,Boolean.parseBoolean(value));
        }

        sender.sendMessage(Messages.COMMAND_PARTY_SETTING_CHANGED, VariableSet.create()
                .addDescribed("setting",setting)
                .addDescribed("value",value)
                .addDescribed("group",group));
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        if(args.length == 0){
            return MAPPER.keySet();
        }else if(args.length == 1){
            return Iterators.filter(MAPPER.keySet(), s -> s.startsWith(args[0].toLowerCase()));
        }else if(args.length == 2){
            return Arrays.asList("true","false");
        }else if(args.length == 3){
            return PlayerSettings.ACTION_CHECKS.keySet();
        }
        return null;
    }
}
