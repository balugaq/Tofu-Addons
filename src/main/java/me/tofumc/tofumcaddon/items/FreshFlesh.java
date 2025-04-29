package me.tofumc.tofumcaddon.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class FreshFlesh extends SlimefunItem {
    public FreshFlesh(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {

        ItemConsumptionHandler itemConsumptionHandler = this::onItemConsumption;
        addItemHandler(itemConsumptionHandler);


    }

    private void onItemConsumption(PlayerItemConsumeEvent event, Player player, ItemStack itemStack) {
        event.setCancelled(true);
        float saturation = player.getSaturation() - 10;
        player.setFoodLevel(20);
        player.setSaturation(saturation);
        PlayerInventory pi = player.getInventory();
        if (player.getItemInHand().getAmount() == 1) {
            pi.setItem(pi.getHeldItemSlot(), new ItemStack(Material.AIR, 0));
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }

    }


}
