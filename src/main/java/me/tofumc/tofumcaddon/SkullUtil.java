package me.tofumc.tofumcaddon;

import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import org.bukkit.inventory.ItemStack;

public class SkullUtil {
    public static ItemStack getByBase64(String base64) {
        PlayerSkin skin = PlayerSkin.fromBase64(base64);
        return PlayerHead.getItemStack(skin);
    }

    public static ItemStack getByHash(String hash) {
        PlayerSkin skin = PlayerSkin.fromHashCode(hash);
        return PlayerHead.getItemStack(skin);
    }

    public static ItemStack getByURL(String url) {
        PlayerSkin skin = PlayerSkin.fromURL(url);
        return PlayerHead.getItemStack(skin);
    }
}
