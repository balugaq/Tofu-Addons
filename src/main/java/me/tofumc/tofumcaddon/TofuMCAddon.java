package me.tofumc.tofumcaddon;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.tofumc.tofumcaddon.events.PhoenixDown;
import me.tofumc.tofumcaddon.items.Antidote;
import me.tofumc.tofumcaddon.items.ChaosPearl;
import me.tofumc.tofumcaddon.items.FreshFlesh;
import me.tofumc.tofumcaddon.items.MobIncapacitator;
import me.tofumc.tofumcaddon.items.Quickdote;

public class TofuMCAddon extends JavaPlugin implements SlimefunAddon {
    private static TofuMCAddon instance;

    public static TofuMCAddon getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        //Category
        NamespacedKey categoryID = new NamespacedKey(this, "TofuMC");
        CustomItemStack categoryItem = new CustomItemStack(SkullUtil.getByBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU1YWFmOGYxZjhjZTg0MTlhM2Y1ZWFmODNmMmE1MWY1YTRlNThkNTc2NjRjM2VkYzFkNjI5NGZkZmY2NjBkOSJ9fX0="), "&4豆腐工艺");

        ItemGroup category = new ItemGroup(categoryID, categoryItem);

        ItemStack[] NORECIPE = {null, null, null, null, null, null, null, null, null};

        /*
        Template For Item
        SlimefunItemStack Stack = new SlimefunItemStack("TOFU_", Material., "§f", "", "&7");
        ItemStack[] tempRecipe = {
                new SlimefunItemStack(bitStack, 1), null, null,
                null, null, null,
                null, null, null
        };
        SlimefunItem sf = new SlimefunItem(category, Stack, RecipeType.NULL, NORECIPE);
        sf.register(this);
         */

        //Bit
        SlimefunItemStack bitStack = new SlimefunItemStack("TOFUMC_BIT", Material.WHITE_DYE, "§f§l豆腐", "", "&7新鲜的豆腐嘞！");
        ItemStack[] bitToChunk = {
                new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS),
                new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.PUMPKIN_SEEDS),
                new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS)
        };
        SlimefunItem sfBit = new SlimefunItem(category, bitStack, RecipeType.ENHANCED_CRAFTING_TABLE, bitToChunk);
        sfBit.register(this);

        //Chunk
        SlimefunItemStack chunkStack = new SlimefunItemStack("TOFUMC_CHUNK", Material.WHITE_CONCRETE, "§f§l豆腐块", "", "&7这么大块的豆腐肯定很好吃吧！");
        ItemStack[] chunkToBit = {
                new SlimefunItemStack(bitStack, 9), null, null,
                null, null, null,
                null, null, null
        };
        SlimefunItem sfChunk = new SlimefunItem(category, chunkStack, RecipeType.COMPRESSOR, chunkToBit);
        sfChunk.setRecipeOutput(new SlimefunItemStack(chunkStack, 1));
        sfChunk.register(this);

        //Chaos Pearl *
        SlimefunItemStack chaosPearlStack = new SlimefunItemStack("TOFU_CHAOS_PEARL", Material.ENDER_PEARL, "§2§l豆制珍珠", "", "&6右击 &7随机传送");
        ItemStack[] chaosPearl = {
                new SlimefunItemStack(chunkStack, 1), new ItemStack(Material.ENDER_PEARL), new SlimefunItemStack(chunkStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.ENDER_PEARL), new SlimefunItemStack(bitStack, 1),
                null, null, null
        };
        ChaosPearl sfChaosPearl = new ChaosPearl(category, chaosPearlStack, RecipeType.ENHANCED_CRAFTING_TABLE, chaosPearl);
        sfChaosPearl.register(this);

        //Phoenix Down *
        SlimefunItemStack phoenixDownStack = new SlimefunItemStack("TOFU_PHOENIX_DOWN", Material.BLAZE_POWDER, "§6§l地狱豆腐", "", "&7千万不要长时间拿在手上！");
        ItemStack[] phoenixDown = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.BLAZE_POWDER), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.BLAZE_POWDER), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.BLAZE_POWDER), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfPhoenixDown = new SlimefunItem(category, phoenixDownStack, RecipeType.ENHANCED_CRAFTING_TABLE, phoenixDown);
        sfPhoenixDown.register(this);
        final PhoenixDown phoenixDownEvent = new PhoenixDown(this, phoenixDownStack);


        //Dragon Eye
        SlimefunItemStack dragonEyeStack = new SlimefunItemStack("TOFU_DRAGON_EYE", Material.ENDER_EYE, "§9§l豆腐雷达", "", "&6右击 &7对遗迹进行定位");
        ItemStack[] dragonEyeRecipe = {
                new SlimefunItemStack(chaosPearlStack, 1), new SlimefunItemStack(phoenixDownStack, 1), null,
                null, null, null,
                null, null, null
        };
        SlimefunItem sfDragonEye = new SlimefunItem(category, dragonEyeStack, RecipeType.ENHANCED_CRAFTING_TABLE, dragonEyeRecipe);
        sfDragonEye.register(this);

        //Wyvern Scale *
        SlimefunItemStack wyvernScaleStack = new SlimefunItemStack("TOFU_WYVERN_SCALE", Material.DRIED_KELP, "§c§l卤豆干", "", "&7这味道...绝！");
        ItemStack[] wsRe = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.DRIED_KELP), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.DRIED_KELP), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.DRIED_KELP), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfWyvernScale = new SlimefunItem(category, wyvernScaleStack, RecipeType.ENHANCED_CRAFTING_TABLE, wsRe);
        sfWyvernScale.register(this);

        //Antidote
        SlimefunItemStack antidoteStack = new SlimefunItemStack("TOFU_ANTIDOTE", Material.HONEY_BOTTLE, "§f§l豆奶", "", "&6右击 饮用  &7移除身上所有药水效果");
        ItemStack[] antidoteRecipe = {
                new SlimefunItemStack(chunkStack, 1), null, null,
                null, null, null,
                null, null, null
        };
        Antidote sfAntidote = new Antidote(category, antidoteStack, RecipeType.JUICER, antidoteRecipe);
        sfAntidote.setRecipeOutput(new SlimefunItemStack(antidoteStack, 4));
        sfAntidote.register(this);

        //Fresh Flesh *
        SlimefunItemStack freshFleshStack = new SlimefunItemStack("TOFU_FRESH_FLESH", Material.ROTTEN_FLESH, "§e§l五平饼", "", "&7这是木曾与伊那地区的地方料理");
        ItemStack[] ffRe = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.ROTTEN_FLESH), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.ROTTEN_FLESH), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.ROTTEN_FLESH), new SlimefunItemStack(bitStack, 1)
        };
        FreshFlesh sfFreshFlesh = new FreshFlesh(category, freshFleshStack, RecipeType.ENHANCED_CRAFTING_TABLE, ffRe);
        sfFreshFlesh.register(this);

        //Quicko'dote
        SlimefunItemStack quickodoteStack = new SlimefunItemStack("TOFU_QUICKODOTE", Material.HONEY_BOTTLE, "§d§l味增汤", "", "&7真让人食欲大开！");
        ItemStack[] quickodoteRecipe = {
                new SlimefunItemStack(freshFleshStack, 1), null, new SlimefunItemStack(freshFleshStack, 1),
                new SlimefunItemStack(freshFleshStack, 1), new SlimefunItemStack(antidoteStack, 1), new SlimefunItemStack(freshFleshStack, 1),
                new SlimefunItemStack(freshFleshStack, 1), new SlimefunItemStack(freshFleshStack, 1), new SlimefunItemStack(freshFleshStack, 1)
        };
        quickodoteStack.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 1);
        var old = quickodoteStack.getItemMeta();
        old.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        quickodoteStack.setItemMeta(old);
        Quickdote sfQuickodote = new Quickdote(category, quickodoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, quickodoteRecipe);
        sfQuickodote.register(this);

        //Silk *
        SlimefunItemStack silkStack = new SlimefunItemStack("TOFU_SILK", Material.STRING, "§f§l豆腐丝", "", "&7豆腐切丝之后似乎口感更好了");
        ItemStack[] sRe = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.IRON_SWORD), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfSilk = new SlimefunItem(category, silkStack, RecipeType.ENHANCED_CRAFTING_TABLE, sRe);
        sfSilk.register(this);

        //Compound Eye *
        SlimefunItemStack compoundEyeStack = new SlimefunItemStack("TOFU_COMPOUND_EYE", Material.FERMENTED_SPIDER_EYE, "§4§l腐乳", "", "&7这色泽...下饭一定很美味吧！");
        ItemStack[] ceRe = {
                new ItemStack(Material.RED_DYE), new ItemStack(Material.BEETROOT), new ItemStack(Material.RED_DYE),
                new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1),
                new ItemStack(Material.RED_DYE), new ItemStack(Material.BEETROOT), new ItemStack(Material.RED_DYE)
        };
        SlimefunItem sfCompoundEye = new SlimefunItem(category, compoundEyeStack, RecipeType.ENHANCED_CRAFTING_TABLE, ceRe);
        sfCompoundEye.register(this);

        //Soul Essence *
        SlimefunItemStack soulEssenceStack = new SlimefunItemStack("TOFU_SOUL_ESSENCE", Material.POTION, "§f§l豆汁", "", "&7老北京传统小吃哦!");
        ItemStack[] essRe = {
                new SlimefunItemStack(silkStack, 1), new SlimefunItemStack(compoundEyeStack, 1), new SlimefunItemStack(silkStack, 1),
                new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1),
                new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE), new ItemStack(Material.GLASS_BOTTLE)
        };
        PotionMeta soulEssenceMeta = (PotionMeta) soulEssenceStack.getItemMeta();
        soulEssenceMeta.setColor(Color.WHITE);
        soulEssenceMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        soulEssenceStack.setItemMeta(soulEssenceMeta);
        SlimefunItem sfSoulEssence = new SlimefunItem(category, soulEssenceStack, RecipeType.ENHANCED_CRAFTING_TABLE, essRe);
        sfSoulEssence.register(this);

        //Ion Cell *
        SlimefunItemStack ionCellStack = new SlimefunItemStack("TOFU_ION_CELL", Material.DAYLIGHT_DETECTOR, "§6§l豆腐能电池", "", "&7用豆腐做的电池？");
        ItemStack[] icRep = {
                new SlimefunItemStack(chunkStack, 1), new ItemStack(Material.DAYLIGHT_DETECTOR), new SlimefunItemStack(chunkStack, 1),
                new SlimefunItemStack(chunkStack, 1), new ItemStack(Material.DAYLIGHT_DETECTOR), new SlimefunItemStack(chunkStack, 1),
                new SlimefunItemStack(chunkStack, 1), new ItemStack(Material.DAYLIGHT_DETECTOR), new SlimefunItemStack(chunkStack, 1)
        };
        SlimefunItem sfIonCell = new SlimefunItem(category, ionCellStack, RecipeType.ENHANCED_CRAFTING_TABLE, icRep);
        sfIonCell.register(this);

        //Ion Tube
        SlimefunItemStack ionTubeStack = new SlimefunItemStack("TOFU_ION_TUBE", Material.END_ROD, "§b§l豆腐能低压天线", "", "&7用豆腐做的天线？");
        ItemStack[] ionTubeRecipe = {
                new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(chunkStack, 1),
                null, null, null,
                null, null, null
        };
        SlimefunItem sfIonTube = new SlimefunItem(category, ionTubeStack, RecipeType.ENHANCED_CRAFTING_TABLE, ionTubeRecipe);
        sfIonTube.register(this);

        //Burnt Note
        SlimefunItemStack burntNoteStack = new SlimefunItemStack("TOFU_BURNT_NOTE", Material.PAPER, "§8§l钢豆腐");
        ItemStack[] BNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.IRON_INGOT), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.COAL), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.IRON_INGOT), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfBurntNote = new SlimefunItem(category, burntNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, BNRep);
        sfBurntNote.register(this);

        //Wet Note
        SlimefunItemStack wetNoteStack = new SlimefunItemStack("TOFU_WET_NOTE", Material.SCUTE, "§a§l毛豆泥豆腐");
        ItemStack[] WNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.SLIME_BALL), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.FERN), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.SLIME_BALL), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfWetNote = new SlimefunItem(category, wetNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, WNRep);
        sfWetNote.register(this);

        //Weightless Note
        SlimefunItemStack weightlessNoteStack = new SlimefunItemStack("TOFU_WEIGHTLESS_NOTE", Material.DIAMOND, "§b§l钻石豆腐");
        ItemStack[] DNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.DIAMOND), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.WATER_BUCKET), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.DIAMOND), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfWeightlessNote = new SlimefunItem(category, weightlessNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, DNRep);
        sfWeightlessNote.register(this);

        //Heavy Note
        SlimefunItemStack heavyNoteStack = new SlimefunItemStack("TOFU_HEAVY_NOTE", Material.CLAY_BALL, "§7§l石豆腐");
        ItemStack[] HNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.STONE), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.CLAY), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.STONE), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfHeavyNote = new SlimefunItem(category, heavyNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, HNRep);
        sfHeavyNote.register(this);

        //Light Note
        SlimefunItemStack lightNoteStack = new SlimefunItemStack("TOFU_LIGHT_NOTE", Material.ORANGE_DYE, "§6§l味增豆腐");
        ItemStack[] LNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.CARROT), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(quickodoteStack, 1), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.POTATO), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfLightNote = new SlimefunItem(category, lightNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, LNRep);
        sfLightNote.register(this);

        //Dark Note
        SlimefunItemStack darkNoteStack = new SlimefunItemStack("TOFU_DARK_NOTE", Material.GLOWSTONE_DUST, "§e§l萤豆腐");
        ItemStack[] DDNRep = {
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.GLOWSTONE_DUST), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.GOLD_INGOT), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.GLOWSTONE_DUST), new SlimefunItemStack(bitStack, 1)
        };
        SlimefunItem sfDarkNote = new SlimefunItem(category, darkNoteStack, RecipeType.ENHANCED_CRAFTING_TABLE, DDNRep);
        sfDarkNote.register(this);

        //TOFUALL
        SlimefunItemStack ToFuallStack = new SlimefunItemStack("TOFU_ALL_SOUP", Material.RABBIT_STEW, "§c§l杂烩豆腐汤");
        ItemStack[] TFARep = {
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(phoenixDownStack, 1), new SlimefunItemStack(burntNoteStack, 1),
                new SlimefunItemStack(wetNoteStack, 1), new SlimefunItemStack(weightlessNoteStack, 1), new SlimefunItemStack(heavyNoteStack, 1),
                new SlimefunItemStack(lightNoteStack, 1), new ItemStack(Material.BOWL), new SlimefunItemStack(darkNoteStack, 1)
        };
        SlimefunItem sfToFuall = new SlimefunItem(category, ToFuallStack, RecipeType.ENHANCED_CRAFTING_TABLE, TFARep);
        sfToFuall.register(this);

        //MAPOTOFU
        SlimefunItemStack MaPoToFuStack = new SlimefunItemStack("TOFU_MAPO", Material.BEETROOT_SOUP, "§4§l麻婆豆腐");
        ItemStack[] MPRep = {
                new SlimefunItemStack(phoenixDownStack, 1), new SlimefunItemStack(compoundEyeStack, 1), new SlimefunItemStack(phoenixDownStack, 1),
                new SlimefunItemStack(lightNoteStack, 1), new SlimefunItemStack(soulEssenceStack, 1), new SlimefunItemStack(lightNoteStack, 1),
                null, new ItemStack(Material.BOWL), null
        };
        SlimefunItem sfToFuMapo = new SlimefunItem(category, MaPoToFuStack, RecipeType.ENHANCED_CRAFTING_TABLE, MPRep);
        sfToFuMapo.register(this);

        //Aegis
        SlimefunItemStack aegisStack = new SlimefunItemStack("TOFU_AEGIS", Material.SHIELD, "§6§l豆腐盾", "&7由坚硬的豆腐块制成", "&8无法破坏");
        ItemStack[] aegisRecipe = {
                new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(burntNoteStack, 1), new SlimefunItemStack(chunkStack, 1),
                new SlimefunItemStack(chunkStack, 1), new ItemStack(Material.SHIELD, 1), new SlimefunItemStack(chunkStack, 1),
                new SlimefunItemStack(chunkStack, 1), new SlimefunItemStack(burntNoteStack, 1), new SlimefunItemStack(chunkStack, 1)
        };
        ItemMeta aegisMeta = aegisStack.getItemMeta();
        aegisMeta.setUnbreakable(true);
        aegisMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_UNBREAKABLE});
        aegisMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        BlockStateMeta aegisBSMeta = (BlockStateMeta) aegisMeta;
        Banner aegisBanner = (Banner) aegisBSMeta.getBlockState();
        aegisBanner.setBaseColor(DyeColor.BROWN);
        aegisBanner.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
        aegisBSMeta.setBlockState(aegisBanner);
        aegisStack.setItemMeta(aegisBSMeta);
        SlimefunItem sfAegis = new SlimefunItem(category, aegisStack, RecipeType.ENHANCED_CRAFTING_TABLE, aegisRecipe);
        sfAegis.register(this);

        //MobIncapacitator
        SlimefunItemStack mobIncapacitatorStack = new SlimefunItemStack("TOFU_MOB_INCAPACITATOR", SkullUtil.getByBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg4MzJjMTQ2NmM4NDFjYzc5ZDVmMTAyOTVkNDY0Mjc5OTY3OTc1YTI0NTFjN2E1MzNjNzk5Njg5NzQwOGJlYSJ9fX0="),
                "§5§l豆腐能涌动核心", "", "&7它涌动出一股奇怪的能量影响着周围的生物...");
        ItemStack[] incapacitatorRecipe = {
                new SlimefunItemStack(ionCellStack, 1), new SlimefunItemStack(ionTubeStack, 1), new SlimefunItemStack(ionCellStack, 1),
                new SlimefunItemStack(dragonEyeStack, 1), new SlimefunItemStack(ToFuallStack, 1), new SlimefunItemStack(dragonEyeStack, 1),
                new SlimefunItemStack(MaPoToFuStack, 1), new ItemStack(Material.NETHERITE_SWORD), new SlimefunItemStack(MaPoToFuStack, 1)
        };
        MobIncapacitator sfIncapacitator = new MobIncapacitator(category, mobIncapacitatorStack, RecipeType.ENHANCED_CRAFTING_TABLE, incapacitatorRecipe);
        sfIncapacitator.register(this);

        //Custom Recipe Registers

        ItemStack[] bottleOfEnchanting = {
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new ItemStack(Material.GLASS_BOTTLE), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1)
        };

        RecipeType.ENHANCED_CRAFTING_TABLE.register(bottleOfEnchanting, new ItemStack(Material.EXPERIENCE_BOTTLE, 2));

        ItemStack[] bitToGold = {
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1),
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1),
                null, null, null
        };

        RecipeType.ENHANCED_CRAFTING_TABLE.register(bitToGold, new ItemStack(Material.GOLD_ORE, 6));

        ItemStack[] bitToIron = {
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), null,
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), null,
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), null
        };

        RecipeType.ENHANCED_CRAFTING_TABLE.register(bitToIron, new ItemStack(Material.IRON_ORE, 10));

        ItemStack[] bitToCoal = {
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), null,
                new SlimefunItemStack(bitStack, 1), new SlimefunItemStack(bitStack, 1), null,
                null, null, null
        };

        RecipeType.ENHANCED_CRAFTING_TABLE.register(bitToCoal, new ItemStack(Material.COAL, 8));

        //Bits to Chunk (and Vica Versa Recipes)
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, new SlimefunItemStack(bitStack, 1), null,
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, new SlimefunItemStack(bitStack, 1),
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         new SlimefunItemStack(bitStack, 1), null, null,
        //         null, null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         null, new SlimefunItemStack(bitStack, 1), null,
        //         null, null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, new SlimefunItemStack(bitStack, 1),
        //         null, null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         new SlimefunItemStack(bitStack, 1), null, null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, new SlimefunItemStack(bitStack, 1), null}, new SlimefunItemStack(chunkStack, 1));
        // RecipeType.COMPRESSOR.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, null, new SlimefunItemStack(bitStack, 1)}, new SlimefunItemStack(chunkStack, 1));


        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, new SlimefunItemStack(chunkStack, 1), null,
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, new SlimefunItemStack(chunkStack, 1),
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         new SlimefunItemStack(chunkStack, 1), null, null,
        //         null, null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         null, new SlimefunItemStack(chunkStack, 1), null,
        //         null, null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, new SlimefunItemStack(chunkStack, 1),
        //         null, null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         new SlimefunItemStack(chunkStack, 1), null, null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, new SlimefunItemStack(chunkStack, 1), null}, new SlimefunItemStack(bitStack, 1));
        // RecipeType.GRIND_STONE.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, null, new SlimefunItemStack(chunkStack, 1)}, new SlimefunItemStack(bitStack, 1));

        //Antidote Recipes
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, new ItemStack(Material.MILK_BUCKET), null,
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, new ItemStack(Material.MILK_BUCKET),
        //         null, null, null,
        //         null, null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         new ItemStack(Material.MILK_BUCKET), null, null,
        //         null, null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         null, new ItemStack(Material.MILK_BUCKET), null,
        //         null, null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, new ItemStack(Material.MILK_BUCKET),
        //         null, null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         new ItemStack(Material.MILK_BUCKET), null, null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, new ItemStack(Material.MILK_BUCKET), null}, new SlimefunItemStack(antidoteStack, 4));
        // RecipeType.JUICER.register(new ItemStack[]{
        //         null, null, null,
        //         null, null, null,
        //         null, null, new ItemStack(Material.MILK_BUCKET)}, new SlimefunItemStack(antidoteStack, 4));


    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

}

