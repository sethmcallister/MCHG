package xyz.mcallister.seth.HG.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.mcallister.seth.HG.utils.KitData;

/**
 * Created by sethm on 26/06/2016.
 */
public class Kit implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        Player p = (Player)sender;
        if(args.length != 1)
        {
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
            sender.sendMessage(ChatColor.RED + "You can apply kits by using " + ChatColor.BOLD + "/kit <kitname>" + ChatColor.RED + ".");
            sender.sendMessage(ChatColor.GOLD + " Switcher: " + ChatColor.RED + "Switch locations with who you hit.");
            sender.sendMessage(ChatColor.GOLD + " Grappler: " + ChatColor.RED + "Use a finishing rod to reach locations.");
            sender.sendMessage(ChatColor.GOLD + " Fisherman: " + ChatColor.RED + "If you hit a player they will teleport towards you.");
            sender.sendMessage(ChatColor.GOLD + " Thor: " + ChatColor.RED + "You can spawn lighting.");
            sender.sendMessage(ChatColor.GOLD + " Kangaroo: " + ChatColor.RED + "You can use your firework to jump higher.");
            sender.sendMessage(ChatColor.GOLD + " Stomper: " + ChatColor.RED + "You can .");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
            return true;
        }
        if(args[0].equalsIgnoreCase("stomper"))
        {
            KitData.updateKit(p.getUniqueId(), "Stomper");
            sender.sendMessage(ChatColor.RED + "You selected the kit " + ChatColor.YELLOW + "Stomper" + ChatColor.RED + ".");
        }
        return false;
    }
}
