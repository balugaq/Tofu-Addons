package me.tofumc.tofumcaddon.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import me.tofumc.tofumcaddon.TofuMCAddon;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Timer;
import java.util.TimerTask;

public class MobIncapacitator extends SlimefunItem {
    public MobIncapacitator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {

        addItemHandler(onBlockPlace());

    }


    private BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(true) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent event) {
                incapacitate(event);
            }
        };
    }

    private void incapacitate(BlockPlaceEvent event) {
        Bukkit.getScheduler().runTask(TofuMCAddon.getInstance(), () -> {
            Block block = event.getBlock();
            for (Entity en : block.getWorld().getNearbyEntities(block.getLocation(), 5, 5, 5)) {
                if (en instanceof Mob) {
                    Mob mob = (Mob) en;
                    if (mob.getMaxHealth() <= 20) {
                        mob.setHealth(1);
                    }
                }
            }
        });

    }


}
