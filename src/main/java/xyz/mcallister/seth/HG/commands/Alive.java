package xyz.mcallister.seth.HG.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mcallister.seth.HG.AtomicStorage;

/**
 * Created by sethm on 06/04/2016.
 */
public class Alive implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if(command.getName().equalsIgnoreCase("alive"))
        {
            if(AtomicStorage.value() == 1)
                sender.sendMessage(ChatColor.GREEN + "There are currently " + ChatColor.BOLD + AtomicStorage.value() + ChatColor.GREEN + " player alive.");
            else
                sender.sendMessage(ChatColor.GREEN + "There are currently " + ChatColor.BOLD + AtomicStorage.value() + ChatColor.GREEN + " players alive.");
        }
        return false;
    }
}
