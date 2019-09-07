package de.thejeterlp.commandapiexample;

import de.thejeterlp.commandapi.BaseCommand;
import de.thejeterlp.commandapi.CommandArgs;
import de.thejeterlp.commandapi.CommandArgs.Flag;
import de.thejeterlp.commandapi.CommandHandler;
import de.thejeterlp.commandapi.CommandResult;
import de.thejeterlp.commandapi.HelpPage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
@CommandHandler
public class ExampleCommands {

    private final HelpPage helpPage = new HelpPage("example");

    public ExampleCommands() {
        //shows as "/example: See the plugin version
        helpPage.addPage("", "See the plugin version");
        //shows as /example test -p <player> Returns the UUID of the player
        helpPage.addPage("test -p <player>", "Returns the UUID of the player");
        helpPage.prepare();
    }

    /*
    Method for /example, gets executed when the console executes the command. 
     */
    @BaseCommand(command = "example", sender = BaseCommand.Sender.CONSOLE)
    public CommandResult executeConsole(CommandSender sender, CommandArgs args) {
        //checks if /example ? or /example help was typed and sets help page if so
        if (helpPage.sendHelp(sender, args)) {
            return CommandResult.SUCCESS;
        }

        //no ? or help was typed, so send the sender the plugin version
        sender.sendMessage("§aExample plugin by " + Main.getInstance().getDescription().getAuthors() + ". Version: " + Main.getInstance().getDescription().getVersion());
        return CommandResult.SUCCESS;

    }

    /*
    Method for /example, gets executed when a player uses the command. 
    Checks automatically for the given permission (example.permission) if provided.
    If the Player does not have the Permission, a message including the needed permission gets sent.
     */
    @BaseCommand(command = "example", sender = BaseCommand.Sender.PLAYER, permission = "example.permission")
    public CommandResult executePlayer(Player sender, CommandArgs args) {
        //do the same as the console
        return executeConsole(sender, args);
    }

    /*
    Method for /example test, gets executed when the conole uses the command
     */
    @BaseCommand(command = "example", sender = BaseCommand.Sender.CONSOLE, permission = "example.test", subCommand = "test")
    public CommandResult executeSubSetConsole(CommandSender sender, CommandArgs args) {
        //check if the player used args. "test" does not count to the args.
        if (args.isEmpty()) {
            //send a message. for example wrong usage, type /example help 
            return CommandResult.ERROR;
        }
        //check if the -p flag was entered
        if (args.hasFlag("p")) {
            //get the Flag that was typed after the -p.
            Flag f = args.getFlag("p");
            if (f.isPlayer()) {
                //get Bukkit OfflinePlayer by the name after the -p flag
                OfflinePlayer p = f.getOfflinePlayer();
                String uuid = p.getUniqueId().toString();
                sender.sendMessage("UUID of Player " + p.getName() + " has the following uuid: " + uuid);
                return CommandResult.SUCCESS;
            } else {
                //String after the -p is not a player
                return CommandResult.NOT_ONLINE;
            }
        } else {
            //wrong usage again, send wrong usage message
            return CommandResult.ERROR;
        }
    }

    @BaseCommand(command = "example", sender = BaseCommand.Sender.PLAYER, permission = "example.test", subCommand = "test")
    public CommandResult executeSubSetPlayer(Player sender, CommandArgs args) {
        return executeSubSetConsole(sender, args);
    }

}
