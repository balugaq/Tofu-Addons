package me.tofumc.tofumcaddon.events;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.tofumc.tofumcaddon.TofuMCAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

public class PhoenixDown implements Listener {
    public static SlimefunItemStack myItem;
    private final TofuMCAddon plugin;

    public PhoenixDown(TofuMCAddon main, SlimefunItemStack item) {
        this.plugin = main;
        myItem = item;
        checkInv();
    }

    private void checkInv() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getInventory().getItemInMainHand().getItemMeta() == null) {
                        continue;
                    }
                    if (p.getInventory().getItemInMainHand().getItemMeta().equals(myItem.getItemMeta()))
                        p.setFireTicks(25);
                }
            }
        }, 0L, 20L);
    }


}
