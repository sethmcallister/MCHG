package xyz.mcallister.seth.HG.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mcallister.seth.HG.GameState;

/**
 * Created by sethm on 06/04/2016.
 */
public class Lobby implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
    {
        if(!sender.hasPermission("hg.admin"))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
            return true;
        }
        Bukkit.broadcastMessage(ChatColor.RED + "The server has been changed to Lobby state.");
        GameState.lobby.set(true);
        return false;
    }
}
