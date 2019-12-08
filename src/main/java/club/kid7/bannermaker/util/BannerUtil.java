package club.kid7.bannermaker.util;

import club.kid7.bannermaker.BannerMaker;
import com.google.common.collect.Maps;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import java.util.*;

import static club.kid7.bannermaker.configuration.Language.tl;

public class BannerUtil {
    /**
     * æª¢æŸ¥ItemStackæ˜¯å�¦ç‚ºæ——å¹Ÿ
     *
     * @param itemStack æ¬²æª¢æŸ¥çš„ç‰©å“�
     * @return boolean
     */
    static public boolean isBanner(ItemStack itemStack) {
        //FIXME: éœ€è¦�æº–ç¢ºçš„åˆ¤æ–·æ–¹å¼�
        return itemStack != null && itemStack.getType().name().contains("BANNER");
    }

    /**
     * æª¢æŸ¥ItemStackæ˜¯å�¦ç‚ºå­—æ¯�æ——å¹Ÿ
     * FIXME: æš«æ™‚å�ªæª¢æŸ¥å��ç¨±æ˜¯å�¦ç‚ºå–®ä¸€å­—å…ƒå¸¶é¡�è‰²ï¼Œåˆ¤æ–·ä¾�æ“šé ˆæ›´ç²¾ç¢º
     *
     * @param itemStack æ¬²æª¢æŸ¥çš„ç‰©å“�
     * @return boolean
     */
    static public boolean isAlphabetBanner(ItemStack itemStack) {
        if (!isBanner(itemStack)) {
            return false;
        }
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasDisplayName()) {
            return false;
        }
        String displayName = itemMeta.getDisplayName();
        if (displayName.equals(ChatColor.stripColor(displayName))) {
            return false;
        }
        if (ChatColor.stripColor(displayName).length() != 1) {
            return false;
        }

        return true;
    }

    /**
     * å�–å¾—æ——å¹Ÿæ��æ–™æ¸…å–®
     *
     * @param banner æ¬²å�–å¾—æ��æ–™æ¸…å–®ä¹‹æ——å¹Ÿ
     * @return List<ItemStack>
     */
    static public List<ItemStack> getMaterials(ItemStack banner) {
        List<ItemStack> materialList = new ArrayList<>();
        //å�ªæª¢æŸ¥æ——å¹Ÿ
        if (!isBanner(banner)) {
            return materialList;
        }
        //åŸºæœ¬æ��æ–™
        //æœ¨æ£’
        ItemStack stick = new ItemStack(Material.STICK, 1);
        materialList.add(stick);
        //ç¾Šæ¯›
        //é¡�è‰²
        DyeColor baseColor = DyeColorUtil.of(banner.getType());
        //ç¾Šæ¯›
        ItemStack wool = new ItemStack(DyeColorUtil.toWoolMaterial(baseColor), 6);
        materialList.add(wool);
        //Patternæ��æ–™
        Inventory materialInventory = Bukkit.createInventory(null, 54);
        BannerMeta bm = (BannerMeta) banner.getItemMeta();
        //é€�Patternè¨ˆç®—
        for (Pattern pattern : bm.getPatterns()) {
            //æ‰€éœ€æŸ“æ–™
            Dye dye = new Dye();
            dye.setColor(pattern.getColor());
            switch (pattern.getPattern()) {
                case SQUARE_BOTTOM_LEFT:
                case SQUARE_BOTTOM_RIGHT:
                case SQUARE_TOP_LEFT:
                case SQUARE_TOP_RIGHT:
                case CIRCLE_MIDDLE:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    break;
                case STRIPE_BOTTOM:
                case STRIPE_TOP:
                case STRIPE_LEFT:
                case STRIPE_RIGHT:
                case STRIPE_CENTER:
                case STRIPE_MIDDLE:
                case STRIPE_DOWNRIGHT:
                case STRIPE_DOWNLEFT:
                case TRIANGLE_BOTTOM:
                case TRIANGLE_TOP:
                case TRIANGLES_BOTTOM:
                case TRIANGLES_TOP:
                case DIAGONAL_LEFT:
                case DIAGONAL_RIGHT:
                case DIAGONAL_LEFT_MIRROR:
                case DIAGONAL_RIGHT_MIRROR:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 3));
                    break;
                case STRIPE_SMALL:
                case RHOMBUS_MIDDLE:
                case GRADIENT:
                case GRADIENT_UP:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 4));
                    break;
                case CROSS:
                case STRAIGHT_CROSS:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 5));
                    break;
                case HALF_VERTICAL:
                case HALF_HORIZONTAL:
                case HALF_VERTICAL_MIRROR:
                case HALF_HORIZONTAL_MIRROR:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 6));
                    break;
                case BORDER:
                    materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 8));
                    break;
                case CURLY_BORDER:
                    materialInventory.addItem(new ItemStack(Material.VINE));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
                case CREEPER:
                    materialInventory.addItem(new ItemStack(Material.CREEPER_HEAD));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
                case BRICKS:
                    materialInventory.addItem(new ItemStack(Material.BRICK));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
                case SKULL:
                    materialInventory.addItem(new ItemStack(Material.WITHER_SKELETON_SKULL));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
                case FLOWER:
                    materialInventory.addItem(new ItemStack(Material.OXEYE_DAISY));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
                case MOJANG:
                    materialInventory.addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        materialInventory.addItem(DyeColorUtil.toDyeItemStack(dye.getColor(), 1));
                    }
                    break;
            }
        }
        //åŠ åˆ°æš«å­˜æ¸…å–®
        List<ItemStack> patternMaterials = new ArrayList<>();
        Collections.addAll(patternMaterials, materialInventory.getContents());
        //é‡�æ–°æŽ’åº�
        InventoryUtil.sort(patternMaterials);
        //å°‡æ��æ–™åŠ åˆ°æ¸…å–®ä¸­
        materialList.addAll(patternMaterials);

        return materialList;
    }

    /**
     * æª¢æŸ¥æ˜¯å�¦æ“�æœ‰è¶³å¤ æ��æ–™
     *
     * @param inventory æŒ‡å®šç‰©å“�æ¬„
     * @param banner    æ——å¹Ÿ
     * @return æ˜¯å�¦æ“�æœ‰è¶³å¤ æ��æ–™
     */
    static public boolean hasEnoughMaterials(Inventory inventory, ItemStack banner) {
        //å�ªæª¢æŸ¥æ——å¹Ÿ
        if (!isBanner(banner)) {
            return false;
        }
        //æ��æ–™æ¸…å–®
        List<ItemStack> materials = getMaterials(banner);
        for (ItemStack material : materials) {
            //ä»»ä½•ä¸€é …ä¸�è¶³
            if (!inventory.containsAtLeast(material, material.getAmount())) {
                //ç›´æŽ¥å›žå‚³false
                return false;
            }
        }
        return true;
    }

    /**
     * æ˜¯å�¦å�¯ä»¥åœ¨ç”Ÿå­˜æ¨¡å¼�å�ˆæˆ�ï¼ˆä¸�è¶…é�Ž6å€‹patternï¼‰
     *
     * @param banner æ——å¹Ÿ
     * @return æ˜¯å�¦å�¯ä»¥å�ˆæˆ�
     */
    static public boolean isCraftableInSurvival(ItemStack banner) {
        //å�ªæª¢æŸ¥æ——å¹Ÿ
        if (!isBanner(banner)) {
            return false;
        }
        int patternCount = ((BannerMeta) banner.getItemMeta()).numberOfPatterns();
        return patternCount <= 6;
    }

    /**
     * å¾žç‰©å“�æ¬„ç§»é™¤æ��æ–™
     *
     * @param inventory æŒ‡å®šç‰©å“�æ¬„
     * @param banner    æ——å¹Ÿ
     * @return æ˜¯å�¦é †åˆ©ç§»é™¤æ��æ–™
     */
    static private boolean removeMaterials(Inventory inventory, ItemStack banner) {
        //å�ªæª¢æŸ¥æ——å¹Ÿ
        if (!isBanner(banner)) {
            return false;
        }
        //æ��æ–™å¿…é ˆè¶³å¤ 
        if (!hasEnoughMaterials(inventory, banner)) {
            return false;
        }
        //æ��æ–™æ¸…å–®
        List<ItemStack> materials = getMaterials(banner);
        HashMap<Integer, ItemStack> itemCannotRemoved = inventory.removeItem(materials.toArray(new ItemStack[0]));
        if (!itemCannotRemoved.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * çµ¦äºˆçŽ©å®¶å–®ä¸€æ——å¹Ÿ
     *
     * @param player è¦�çµ¦äºˆç‰©å“�çš„çŽ©å®¶
     * @param banner è¦�çµ¦äºˆçš„æ——å¹Ÿ
     * @return æ˜¯å�¦æˆ�åŠŸçµ¦äºˆ
     */
    public static boolean buy(Player player, ItemStack banner) {
        //æª¢æŸ¥æ˜¯å�¦å•Ÿç”¨ç¶“æ¿Ÿ
        if (BannerMaker.getInstance().econ == null) {
            //æœªå•Ÿç”¨ç¶“æ¿Ÿï¼Œå¼·åˆ¶å¤±æ•—
            player.sendMessage(MessageUtil.format(true, "&cError: Economy not supported"));
            return false;
        }
        //åƒ¹æ ¼
        double price = EconUtil.getPrice(banner);
        //æª¢æŸ¥è²¡ç”¢æ˜¯å�¦è¶³å¤ 
        if (!BannerMaker.getInstance().econ.has(player, price)) {
            //è²¡ç”¢ä¸�è¶³
            player.sendMessage(MessageUtil.format(true, "&c" + tl("general.no-money")));
            return false;
        }
        //æ‰£æ¬¾
        EconomyResponse response = BannerMaker.getInstance().econ.withdrawPlayer(player, price);
        //æª¢æŸ¥äº¤æ˜“æ˜¯å�¦æˆ�åŠŸ
        if (!response.transactionSuccess()) {
            //äº¤æ˜“å¤±æ•—
            player.sendMessage(MessageUtil.format(true, "&cError: " + response.errorMessage));
            return false;
        }
        InventoryUtil.give(player, banner);
        player.sendMessage(MessageUtil.format(true, "&a" + tl("general.money-transaction", BannerMaker.getInstance().econ.format(response.amount), BannerMaker.getInstance().econ.format(response.balance))));
        return true;
    }

    /**
     * ä½¿ç”¨æ��æ–™å�ˆæˆ�æ——å¹Ÿ
     * FIXME: å�³ä½¿patterné�Žå¤šï¼Œä¹Ÿä»�ç„¶èƒ½å�ˆæˆ�ï¼Œå�¯èƒ½éœ€è¦�æ¬Šé™�é™�åˆ¶
     *
     * @param player è¦�çµ¦äºˆç‰©å“�çš„çŽ©å®¶
     * @param banner è¦�çµ¦äºˆçš„æ——å¹Ÿ
     * @return æ˜¯å�¦æˆ�åŠŸçµ¦äºˆ
     */
    public static boolean craft(Player player, ItemStack banner) {
        //æª¢æŸ¥æ��æ–™
        if (!hasEnoughMaterials(player.getInventory(), banner) && player.getGameMode() != GameMode.CREATIVE) {
            return false;
        }
        //ç§»é™¤æ��æ–™
        removeMaterials(player.getInventory(), banner);

        InventoryUtil.give(player, banner);
        return true;
    }

    /**
     * å�–å¾—æ——å¹Ÿåœ¨çŽ©å®¶å­˜æª”ä¸­çš„Key
     *
     * @param banner æ¬²æª¢æŸ¥ä¹‹æ——å¹Ÿ
     * @return String
     */
    static public String getKey(ItemStack banner) {
        //å�ªè™•ç�†æ——å¹Ÿ
        if (!isBanner(banner)) {
            return null;
        }
        String key;
        //å˜—è©¦å�–å‡ºkey
        try {
            key = HiddenStringUtil.extractHiddenString(banner.getItemMeta().getLore().get(0));
        } catch (Exception exception) {
            return null;
        }
        return key;
    }

    /**
     * å�–å¾—æ——å¹Ÿå��ç¨±ï¼Œè‹¥ç„¡å��ç¨±å‰‡å˜—è©¦å�–å¾—KEY
     *
     * @param banner æ¬²æª¢æŸ¥ä¹‹æ——å¹Ÿ
     * @return String
     */
    static public String getName(ItemStack banner) {
        //å�ªè™•ç�†æ——å¹Ÿ
        if (!isBanner(banner)) {
            return null;
        }
        //å…ˆè©¦è‘—å�–å¾—è‡ªè¨‚å��ç¨±
        if (banner.hasItemMeta() && banner.getItemMeta().hasDisplayName()) {
            return banner.getItemMeta().getDisplayName();
        }
        //å˜—è©¦å�–å¾—key
        String key = BannerUtil.getKey(banner);
        if (key != null) {
            return key;
        }
        //è‹¥éƒ½æ²’æœ‰ï¼Œå›žå‚³ç©ºå­—ä¸²
        return "";
    }

    public static List<PatternType> getPatternTypeList() {
        List<PatternType> list = Arrays.asList(
            PatternType.BORDER,
            PatternType.BRICKS,
            PatternType.CIRCLE_MIDDLE,
            PatternType.CREEPER,
            PatternType.CROSS,
            PatternType.CURLY_BORDER,
            PatternType.DIAGONAL_LEFT,
            PatternType.DIAGONAL_LEFT_MIRROR,
            PatternType.DIAGONAL_RIGHT,
            PatternType.DIAGONAL_RIGHT_MIRROR,
            PatternType.FLOWER,
            PatternType.GRADIENT,
            PatternType.GRADIENT_UP,
            PatternType.HALF_HORIZONTAL,
            PatternType.HALF_HORIZONTAL_MIRROR,
            PatternType.HALF_VERTICAL,
            PatternType.HALF_VERTICAL_MIRROR,
            PatternType.MOJANG,
            PatternType.RHOMBUS_MIDDLE,
            PatternType.SKULL,
            PatternType.SQUARE_BOTTOM_LEFT,
            PatternType.SQUARE_BOTTOM_RIGHT,
            PatternType.SQUARE_TOP_LEFT,
            PatternType.SQUARE_TOP_RIGHT,
            PatternType.STRAIGHT_CROSS,
            PatternType.STRIPE_BOTTOM,
            PatternType.STRIPE_CENTER,
            PatternType.STRIPE_DOWNLEFT,
            PatternType.STRIPE_DOWNRIGHT,
            PatternType.STRIPE_LEFT,
            PatternType.STRIPE_MIDDLE,
            PatternType.STRIPE_RIGHT,
            PatternType.STRIPE_SMALL,
            PatternType.STRIPE_TOP,
            PatternType.TRIANGLE_BOTTOM,
            PatternType.TRIANGLE_TOP,
            PatternType.TRIANGLES_BOTTOM,
            PatternType.TRIANGLES_TOP
        );
        return list;
    }

    static public HashMap<Integer, ItemStack> getPatternRecipe(final ItemStack banner, int step) {
        HashMap<Integer, ItemStack> recipe = Maps.newHashMap();
        //å¡«æ»¿ç©ºæ°£
        for (int i = 0; i < 10; i++) {
            recipe.put(i, new ItemStack(Material.AIR));
        }
        //å�ªè™•ç�†æ——å¹Ÿ
        if (!isBanner(banner)) {
            return recipe;
        }
        BannerMeta bm = (BannerMeta) banner.getItemMeta();
        int totalStep = bm.numberOfPatterns() + 1;
        //é¡�è‰²
        DyeColor baseColor = DyeColorUtil.of(banner.getType());
        if (step == 1) {
            //ç¬¬ä¸€æ­¥ï¼Œæ——å¹Ÿå�ˆæˆ�
            //ç¾Šæ¯›
            ItemStack wool = new ItemStack(DyeColorUtil.toWoolMaterial(baseColor));
            for (int i = 0; i < 6; i++) {
                recipe.put(i, wool.clone());
            }
            //æœ¨æ£’
            ItemStack stick = new ItemStack(Material.STICK);
            recipe.put(7, stick);
        } else if (step <= totalStep) {
            //æ–°å¢žPattern
            //ç•¶å‰�banner
            ItemStack prevBanner = new ItemStack(DyeColorUtil.toBannerMaterial(baseColor));
            BannerMeta pbm = (BannerMeta) prevBanner.getItemMeta();
            //æ–°å¢žè‡³ç›®å‰�çš„Pattern
            for (int i = 0; i < step - 2; i++) {
                pbm.addPattern(bm.getPattern(i));
            }
            prevBanner.setItemMeta(pbm);
            //ç•¶å‰�Pattern
            Pattern pattern = bm.getPattern(step - 2);
            //æ‰€éœ€æŸ“æ–™
            Dye dye = new Dye();
            dye.setColor(pattern.getColor());
            ItemStack dyeItem = DyeColorUtil.toDyeItemStack(dye.getColor(), 1);
            //æ——å¹Ÿä½�ç½®
            int bannerPosition = 4;
            //æŸ“æ–™ä½�ç½®
            List<Integer> dyePosition = Collections.emptyList();
            //æ ¹æ“šPatternæ±ºå®šä½�ç½®
            switch (pattern.getPattern()) {
                case SQUARE_BOTTOM_LEFT:
                    dyePosition = Collections.singletonList(6);
                    break;
                case SQUARE_BOTTOM_RIGHT:
                    dyePosition = Collections.singletonList(8);
                    break;
                case SQUARE_TOP_LEFT:
                    dyePosition = Collections.singletonList(0);
                    break;
                case SQUARE_TOP_RIGHT:
                    dyePosition = Collections.singletonList(2);
                    break;
                case STRIPE_BOTTOM:
                    dyePosition = Arrays.asList(6, 7, 8);
                    break;
                case STRIPE_TOP:
                    dyePosition = Arrays.asList(0, 1, 2);
                    break;
                case STRIPE_LEFT:
                    dyePosition = Arrays.asList(0, 3, 6);
                    break;
                case STRIPE_RIGHT:
                    dyePosition = Arrays.asList(2, 5, 8);
                    break;
                case STRIPE_CENTER:
                    bannerPosition = 3;
                    dyePosition = Arrays.asList(1, 4, 7);
                    break;
                case STRIPE_MIDDLE:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(3, 4, 5);
                    break;
                case STRIPE_DOWNRIGHT:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(0, 4, 8);
                    break;
                case STRIPE_DOWNLEFT:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(2, 4, 6);
                    break;
                case STRIPE_SMALL:
                    dyePosition = Arrays.asList(0, 2, 3, 5);
                    break;
                case CROSS:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(0, 2, 4, 6, 8);
                    break;
                case STRAIGHT_CROSS:
                    bannerPosition = 0;
                    dyePosition = Arrays.asList(1, 3, 4, 5, 7);
                    break;
                case TRIANGLE_BOTTOM:
                    bannerPosition = 7;
                    dyePosition = Arrays.asList(4, 6, 8);
                    break;
                case TRIANGLE_TOP:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(0, 2, 4);
                    break;
                case TRIANGLES_BOTTOM:
                    dyePosition = Arrays.asList(3, 5, 7);
                    break;
                case TRIANGLES_TOP:
                    dyePosition = Arrays.asList(1, 3, 5);
                    break;
                case DIAGONAL_LEFT:
                    dyePosition = Arrays.asList(0, 1, 3);
                    break;
                case DIAGONAL_RIGHT:
                    dyePosition = Arrays.asList(5, 7, 8);
                    break;
                case DIAGONAL_LEFT_MIRROR:
                    dyePosition = Arrays.asList(3, 6, 7);
                    break;
                case DIAGONAL_RIGHT_MIRROR:
                    dyePosition = Arrays.asList(1, 2, 5);
                    break;
                case CIRCLE_MIDDLE:
                    bannerPosition = 1;
                    dyePosition = Collections.singletonList(4);
                    break;
                case RHOMBUS_MIDDLE:
                    dyePosition = Arrays.asList(1, 3, 5, 7);
                    break;
                case HALF_VERTICAL:
                    bannerPosition = 5;
                    dyePosition = Arrays.asList(0, 1, 3, 4, 6, 7);
                    break;
                case HALF_HORIZONTAL:
                    bannerPosition = 7;
                    dyePosition = Arrays.asList(0, 1, 2, 3, 4, 5);
                    break;
                case HALF_VERTICAL_MIRROR:
                    bannerPosition = 3;
                    dyePosition = Arrays.asList(1, 2, 4, 5, 7, 8);
                    break;
                case HALF_HORIZONTAL_MIRROR:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(3, 4, 5, 6, 7, 8);
                    break;
                case BORDER:
                    dyePosition = Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8);
                    break;
                case CURLY_BORDER:
                    recipe.put(1, new ItemStack(Material.VINE));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
                case CREEPER:
                    recipe.put(1, new ItemStack(Material.CREEPER_HEAD));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
                case GRADIENT:
                    bannerPosition = 1;
                    dyePosition = Arrays.asList(0, 2, 4, 7);
                    break;
                case GRADIENT_UP:
                    bannerPosition = 7;
                    dyePosition = Arrays.asList(1, 4, 6, 8);
                    break;
                case BRICKS:
                    recipe.put(1, new ItemStack(Material.BRICK));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
                case SKULL:
                    recipe.put(1, new ItemStack(Material.WITHER_SKELETON_SKULL));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
                case FLOWER:
                    recipe.put(1, new ItemStack(Material.OXEYE_DAISY));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
                case MOJANG:
                    recipe.put(1, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
                    if (!pattern.getColor().equals(DyeColor.BLACK)) {
                        dyePosition = Collections.singletonList(7);
                    }
                    break;
            }
            //æ”¾ç½®æ——å¹Ÿèˆ‡æŸ“æ–™
            recipe.put(bannerPosition, prevBanner);
            for (int i : dyePosition) {
                recipe.put(i, dyeItem.clone());
            }
        }
        //å�ˆæˆ�çµ�æžœ
        //ç•¶å‰�banner
        ItemStack currentBanner = new ItemStack(DyeColorUtil.toBannerMaterial(baseColor));
        BannerMeta cbm = (BannerMeta) currentBanner.getItemMeta();
        //æ–°å¢žè‡³ç›®å‰�çš„Pattern
        for (int i = 0; i < step - 1; i++) {
            cbm.addPattern(bm.getPattern(i));
        }
        currentBanner.setItemMeta(cbm);
        recipe.put(9, currentBanner);

        return recipe;
    }
}
