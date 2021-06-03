package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class InviteCommand extends BasicCommand {

    public InviteCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("invite","i"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_PARTY_HELP);
            return;
        }
        if(CommandUtil.isSelfCheck(Messages.PREFIX_PARTY,sender,arguments[0])) return;
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        MinecraftPlayer target = CommandUtil.getPlayer(sender,Messages.PREFIX_FRIEND,arguments[0]);
        if(target == null) return;

        if(!target.isOnline()){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_ONLINE, VariableSet.create()
                    .addDescribed("player",target)
                    .addDescribed("prefix",Messages.PREFIX_PARTY));
            return;
        }

        //Check if can invite

        Party party = player.getParty();
        if(party != null){
            if(!party.canInteract(player.getId(),target.getUniqueId())){
                sender.sendMessage(Messages.ERROR_PARTY_NOT_ALLOWED);
                return;
            }
        }else party = player.createParty();

        if(party == null) return;

        party.invite(player,target.getUniqueId());
        sender.sendMessage(Messages.COMMAND_PARTY_INVITED,VariableSet.create()
                .addDescribed("player",target));
    }
}
