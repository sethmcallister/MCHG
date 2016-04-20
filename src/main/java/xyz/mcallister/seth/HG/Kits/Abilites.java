package xyz.mcallister.seth.HG.Kits;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.mcallister.seth.HG.GameState;
import xyz.mcallister.seth.HG.Main;

/**
 * Created by sethm on 07/04/2016.
 */
public class Abilites implements Listener
{
    @EventHandler
    public void snowball(EntityDamageByEntityEvent e)
    {
        if(!GameState.lobby.get() == false)
        {
            if ((e.getDamager() instanceof Snowball) && (e.getEntity() instanceof Player))
            {
                Snowball s = (Snowball) e.getDamager();
                if ((s.getShooter() instanceof Player))
                {
                    Player shooter = (Player) s.getShooter();
                    Location shooterLoc = shooter.getLocation();
                    shooter.teleport(e.getEntity().getLocation());
                    e.getEntity().teleport(shooterLoc);
                }
            }
        } else
        {
            Snowball s = (Snowball) e.getDamager();
            if ((s.getShooter() instanceof Player))
            {
                Player shooter = (Player) s.getShooter();
                shooter.sendMessage(ChatColor.RED + "You cannot use your ability before the game starts.");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHitFishingrodscorpion(PlayerFishEvent event)
    {
        Player player = event.getPlayer();
        if ((event.getCaught() instanceof Player))
        {
            Player caught = (Player)event.getCaught();
            if (player.getItemInHand().getType() == Material.FISHING_ROD)
            {
                Location loc = player.getLocation();
                caught.teleport(loc);
            }
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent evt)
    {
        Player player = evt.getPlayer();
        Block origin = evt.getBlock();
        int type = origin.getTypeId();
        if ((type == 3) || (type == 2))
        {
            int addHunger = player.getFoodLevel() + 2;
            player.setFoodLevel(addHunger);
        }
    }

    public static ConcurrentMap<String, Long> endermagecd = new ConcurrentHashMap<String, Long>();

    @EventHandler
    public void onPlayerEndermage(PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        if ((e.getPlayer().getItemInHand().getType().equals(Material.PORTAL)) && (e.getItem().getTypeId() == Material.PORTAL.getId()) && (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock() != null))
        {
            e.setCancelled(true);
            Material item = e.getItem().getType();
            Location placed = e.getClickedBlock().getLocation().add(0.0D, 1.0D, 0.0D);
            if(endermagecd.containsKey(p.getName()) && endermagecd.get(p.getName()) > System.currentTimeMillis())
            {
                long millisLeft = ((Long)endermagecd.get(e.getPlayer().getName())).longValue() - System.currentTimeMillis();
                double value = millisLeft / 1000.0D;
                double sec = Math.round(10.0D * value) / 10.0D;
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                e.getPlayer().updateInventory();
            } else if (item == Material.PORTAL)
            {
                List<Entity> nearby = p.getNearbyEntities(5.0D, 256.0D, 5.0D);
                for (Entity ent : nearby) {
                    if ((ent instanceof Player)) {
                        ((Player)ent).teleport(placed);
                    }
                }
                if (nearby.size() >= 1)
                {
                    this.endermagecd.put(p.getName(),System.currentTimeMillis() + 16000);
                    p.teleport(placed);
                    p.sendMessage(ChatColor.YELLOW + "Teleported!");
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "You didn't get anyone! Cooldown still applies!");
                    this.endermagecd.put(p.getName(),System.currentTimeMillis() + 16000);
                }
            }
        }
    }

    public String flightTurnOn = ChatColor.RED + "You can now fly!";
    public String flightWoreOff = ChatColor.RED + "Your flight has just disappeared!";

    public static ConcurrentMap<String, Long> cooldown8 = new ConcurrentHashMap<String, Long>();

    @EventHandler
    public void onInteractPhantom(PlayerInteractEvent event)
    {
        if ((event.getAction().name().contains("RIGHT")) && (event.getPlayer().getItemInHand().getType() == Material.FEATHER))
        {
            final Player p = event.getPlayer();
            if(cooldown8.containsKey(p.getName()) && cooldown8.get(p.getName()) > System.currentTimeMillis())
            {
                long millisLeft = ((Long)cooldown8.get(event.getPlayer().getName())).longValue() - System.currentTimeMillis();
                double value = millisLeft / 1000.0D;
                double sec = Math.round(10.0D * value) / 10.0D;
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                event.getPlayer().updateInventory();
            } else
            {
                this.cooldown8.put(p.getName(),System.currentTimeMillis() + 16000);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(this.flightTurnOn);
                if (1 > 3)
                {
                    int i = 16 * 20;
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
                    {
                        public void run()
                        {
                            p.sendMessage(ChatColor.RED + "You have 3 seconds of flight remaining");
                        }
                    }, i);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
                    {
                        public void run()
                        {
                            p.sendMessage(ChatColor.RED + "You have 2 seconds of flight remaining");
                        }
                    }, i + 20);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
                    {
                        public void run()
                        {
                            p.sendMessage(ChatColor.RED + "You have 1 second of flight remaining");
                        }
                    }, i + 40);
                }
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
                {
                    public void run()
                    {
                        p.setAllowFlight(false);
                        p.sendMessage(flightWoreOff);
                    }
                }, 16 * 20);
            }
        }
    }

    public static ConcurrentMap<String, Long> kangaroocd = new ConcurrentHashMap<String, Long>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        final Player p = event.getPlayer();
        if (p.hasPermission("mcpvp.kangaroo"))
        {
            if (event.getPlayer().getItemInHand().getType() == Material.FIREWORK)
                event.setCancelled(true);
            if(!kangaroocd.containsKey(p.getName()) && kangaroocd.get(p.getName()) > System.currentTimeMillis())
            {
                if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR)) && (event.getMaterial() == Material.FIREWORK) && (!p.isSneaking()))
                {
                    event.setCancelled(true);
                    Block b = p.getLocation().getBlock();
                    if ((b.getType() != Material.AIR) || (b.getRelative(BlockFace.DOWN).getType() != Material.AIR))
                    {
                        p.setFallDistance(-5.0F);
                        Vector vector = p.getEyeLocation().getDirection();
                        vector.multiply(0.6F);
                        vector.setY(1);
                        p.setVelocity(vector);
                        this.kangaroocd.put(p.getName(),System.currentTimeMillis() + 16000);
                    }
                }
                else if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR)) && (event.getMaterial() == Material.FIREWORK) && (p.isSneaking()))
                {
                    event.setCancelled(true);
                    Block b = p.getLocation().getBlock();
                    if ((b.getType() != Material.AIR) || (b.getRelative(BlockFace.DOWN).getType() != Material.AIR))
                    {
                        p.setFallDistance(-5.0F);
                        Vector vector = p.getEyeLocation().getDirection();
                        vector.multiply(1.0F);
                        vector.setY(0.7D);
                        p.setVelocity(vector);
                        this.kangaroocd.put(p.getName(),System.currentTimeMillis() + 16000);
                    }
                }
            } else
            {
                long millisLeft = ((Long)kangaroocd.get(event.getPlayer().getName())).longValue() - System.currentTimeMillis();
                double value = millisLeft / 1000.0D;
                double sec = Math.round(10.0D * value) / 10.0D;
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                event.getPlayer().updateInventory();
            }
        }
    }

    public static ConcurrentMap<String, Long> grappler = new ConcurrentHashMap<String, Long>();

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerFish(PlayerFishEvent event)
    {
        final Player player = event.getPlayer();
            if(grappler.containsKey(player.getName()) && grappler.get(player.getName()) > System.currentTimeMillis())
            {
                long millisLeft = ((Long)grappler.get(event.getPlayer().getName())).longValue() - System.currentTimeMillis();
                double value = millisLeft / 1000.0D;
                double sec = Math.round(10.0D * value) / 10.0D;
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                event.getPlayer().updateInventory();
            }
            else if (event.getState().equals(PlayerFishEvent.State.IN_GROUND))
            {
                Location lc = player.getLocation();
                Location to = event.getHook().getLocation();

                lc.setY(lc.getY() + 0.5D);
                player.teleport(lc);

                double g = -0.08D;
                double d = to.distance(lc);
                double t = d;
                double v_x = (1.0D + 0.07D * t) * (to.getX() - lc.getX()) / t;
                double v_y = (1.0D + 0.03D * t) * (to.getY() - lc.getY()) / t - 0.5D * g * t;
                double v_z = (1.0D + 0.07D * t) * (to.getZ() - lc.getZ()) / t;

                Vector v = player.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                player.setVelocity(v);
                this.grappler.put(player.getName(),System.currentTimeMillis() + 16000);
            }
            else if (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY))
            {
                if (!player.hasPermission("mcpvp.grappler")) {
                    return;
                }
                Location lc = player.getLocation();
                Location to = event.getHook().getLocation();

                lc.setY(lc.getY() + 0.5D);
                player.teleport(lc);

                double g = -0.08D;
                double d = to.distance(lc);
                double t = d;
                double v_x = (1.0D + 0.07D * t) * (to.getX() - lc.getX()) / t;
                double v_y = (1.0D + 0.03D * t) * (to.getY() - lc.getY()) / t - 0.5D * g * t;
                double v_z = (1.0D + 0.07D * t) * (to.getZ() - lc.getZ()) / t;

                Vector v = player.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                player.setVelocity(v);
                this.grappler.put(player.getName(),System.currentTimeMillis() + 16000);
            }
    }

    public static ConcurrentMap<String, Long> thorcooldown = new ConcurrentHashMap<String, Long>();

    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)))
        {
            ItemStack hand = p.getItemInHand();
            if ((hand.getType() == Material.WOOD_AXE) && (p.hasPermission("mcpvp.thor")))
            {
                if(thorcooldown.containsKey(p.getName()) && thorcooldown.get(p.getName()) > System.currentTimeMillis())
                {
                    long millisLeft = ((Long)thorcooldown.get(e.getPlayer().getName())).longValue() - System.currentTimeMillis();
                    double value = millisLeft / 1000.0D;
                    double sec = Math.round(10.0D * value) / 10.0D;
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                    e.getPlayer().updateInventory();
                }
                else
                {
                    Location loc = p.getTargetBlock(null, 20).getLocation();
                    p.getWorld().strikeLightning(loc);
                    this.thorcooldown.put(p.getName(),System.currentTimeMillis() + 16000);
                }
            }
        }
    }

    public static ConcurrentMap<String, Long> Flashcd = new ConcurrentHashMap<String, Long>();

    @EventHandler
    public void onFlash(PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        if ((p.getInventory().getItemInHand().getType() == Material.REDSTONE_TORCH_ON) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)))
        {
            e.setCancelled(true);
            if(Flashcd.containsKey(p.getName()) && Flashcd.get(p.getName()) > System.currentTimeMillis())
            {
                long millisLeft = ((Long)Flashcd.get(e.getPlayer().getName())).longValue() - System.currentTimeMillis();
                double value = millisLeft / 1000.0D;
                double sec = Math.round(10.0D * value) / 10.0D;
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                e.getPlayer().updateInventory();
            }
            else
            {
                Location loc = e.getPlayer().getTargetBlock(null, 100).getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                Location loc2 = new Location(e.getPlayer().getWorld(), x, y, z);
                if (loc2.getBlock().getType() != Material.AIR) {
                    loc2.setY(y + 1.5D);
                }
                if (loc.getBlock().getType() == Material.AIR)
                {
                    p.sendMessage(ChatColor.RED + "You are not looking at a block");
                    return;
                }
                if (loc2.distance(e.getPlayer().getLocation()) > 30)
                {
                    p.sendMessage(ChatColor.RED + "You may not teleport that far");
                    return;
                }
                final int s = p.getInventory().getHeldItemSlot();
                p.teleport(loc);
                int distance = (int)(p.getLocation().distance(loc) / 2.0D);
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, distance, 0));
                loc.add(0.0D, 4.0D, 0.0D);
                Bukkit.getWorld(p.getWorld().getName()).strikeLightning(loc);
                p.getInventory().setItemInHand(new ItemStack(Material.REDSTONE_TORCH_OFF));
                this.Flashcd.put(p.getName(),System.currentTimeMillis() + 16000);
            }
        }
    }

    public static ConcurrentMap<String, Long> Pyrocd = new ConcurrentHashMap<String, Long>();;

    @EventHandler
    public void onPyro(PlayerInteractEvent e)
    {
        final Player p = e.getPlayer();
        if ((p.getInventory().getItemInHand().getType() == Material.FIREBALL) && ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)))
        {
            e.setCancelled(true);
                if (p.getGameMode() == GameMode.CREATIVE)
                {
                    boolean small = false;
                    Vector direction = p.getPlayer().getEyeLocation().getDirection().multiply(2);
                    Fireball fireball = (Fireball)p.getPlayer().getWorld().spawn(p.getPlayer().getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), small ? SmallFireball.class : Fireball.class);
                    fireball.setShooter(p.getPlayer());
                }
                else if (p.getGameMode() == GameMode.SURVIVAL)
                {
                    boolean small = false;
                    Vector direction = p.getPlayer().getEyeLocation().getDirection().multiply(2);
                    Fireball fireball = (Fireball)p.getPlayer().getWorld().spawn(p.getPlayer().getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), small ? SmallFireball.class : Fireball.class);
                    fireball.setShooter(p.getPlayer());
                    int amount = p.getPlayer().getItemInHand().getAmount();
                    p.getItemInHand().setAmount(amount - 1);
                    p.updateInventory();
                    if (amount == 1)
                    {
                        PlayerInventory inventory = p.getInventory();
                        inventory.remove(Material.FIREBALL);
                        p.updateInventory();
                    }
                }
            else if(Pyrocd.containsKey(p.getName()) && Pyrocd.get(p.getName()) > System.currentTimeMillis())
                {
                    long millisLeft = ((Long)Pyrocd.get(e.getPlayer().getName())).longValue() - System.currentTimeMillis();
                    double value = millisLeft / 1000.0D;
                    double sec = Math.round(10.0D * value) / 10.0D;
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "You cannot use this for another " + ChatColor.BOLD + sec + ChatColor.RED + " seconds!");
                    e.getPlayer().updateInventory();
                }
            else
            {
                this.Pyrocd.put(p.getName(),System.currentTimeMillis() + 16000);
                if (p.getGameMode() == GameMode.CREATIVE)
                {
                    boolean small = false;
                    Vector direction = p.getPlayer().getEyeLocation().getDirection().multiply(2);
                    Fireball fireball = (Fireball)p.getPlayer().getWorld().spawn(p.getPlayer().getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), small ? SmallFireball.class : Fireball.class);
                    fireball.setShooter(p.getPlayer());
                }
                else if (p.getGameMode() == GameMode.SURVIVAL)
                {
                    boolean small = false;
                    Vector direction = p.getPlayer().getEyeLocation().getDirection().multiply(2);
                    Fireball fireball = (Fireball)p.getPlayer().getWorld().spawn(p.getPlayer().getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), small ? SmallFireball.class : Fireball.class);
                    fireball.setShooter(p.getPlayer());
                    int amount = p.getPlayer().getItemInHand().getAmount();
                    p.getItemInHand().setAmount(amount - 1);
                    p.updateInventory();
                    if (amount == 1)
                    {
                        PlayerInventory inventory = p.getInventory();
                        inventory.remove(Material.FIREBALL);
                        p.updateInventory();
                    }
                }
            }
        }
    }
}