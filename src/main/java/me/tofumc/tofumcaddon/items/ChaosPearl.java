package me.tofumc.tofumcaddon.items;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ChaosPearl extends SlimefunItem {
    public ChaosPearl(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {

        ItemUseHandler itemUseHandler = this::onItemUseRightClick;
        addItemHandler(itemUseHandler);


    }

    private void onItemUseRightClick(PlayerRightClickEvent event) {
        event.cancel();
        Player player = event.getPlayer();
        PlayerInventory pi = player.getInventory();
        if (player.getItemInHand().getAmount() == 1) {
            pi.setItem(pi.getHeldItemSlot(), new ItemStack(Material.AIR, 0));
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        int x = player.getLocation().getBlockX() - ((int) (Math.random() * 2000) - 1000);
        int z = player.getLocation().getBlockZ() - ((int) (Math.random() * 2000) - 1000);
        int y = player.getLocation().getBlockY();
        player.teleport(new Location(player.getWorld(), x, y, z));
    }


}
