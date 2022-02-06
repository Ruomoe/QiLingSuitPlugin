package cc.canyi.qilingsuit.gui;

import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.serverct.ersha.jd.I;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QiLingSuitStatsGui {
    private static final int[] FRAME_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };

    private static final int SUIT_SLOT = 19;
    private static final int ATTR_SLOT = 21;
    private static final int BUFF_SLOT = 23;
    private static final int PASSIVES_SLOT = 25;

    /**
     * 尚未完成
     * @param player 玩家
     * @return 状态GUI
     */
    public static Inventory getStatsGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, ConfigUtils.getStatsGuiName());

        ItemStack black_frame = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta black_frameMate = black_frame.getItemMeta();
        black_frameMate.setDisplayName("  ");
        black_frame.setItemMeta(black_frameMate);

        ItemStack suitItem = new ItemStack(Material.DIRT);
        ItemMeta suitItemMeta = suitItem.getItemMeta();
        suitItemMeta.setDisplayName(" §7已激活套装");
        List<String> suitLore = new ArrayList<>(Arrays.asList(" "));

        //put frame
        for(int i : FRAME_SLOTS) inventory.setItem(i, black_frame);

        PlayerSuitUpdateTask.Cache cache = PlayerSuitUpdateTask.getFactory_cache().get(player);
        if(cache != null) {
            for(Suit suit : cache.getPlayerEquipSuitMap().keySet()) {
                List<String> parts = cache.getPlayerEquipSuitMap().get(suit);

            }
        }

        //set suit meta
        suitItem.setItemMeta(suitItemMeta);
        //put suit item
        inventory.setItem(SUIT_SLOT, suitItem);

        return inventory;
    }


    public static class GuiListener implements Listener {
        //禁止交互

        @EventHandler
        public void clickInv(InventoryClickEvent event) {
            if(event.getInventory().getTitle() != null && event.getInventory().getTitle().equals(ConfigUtils.getStatsGuiName())) {
                event.setCancelled(true);
            }
        }

        @EventHandler
        public void invDrag(InventoryDragEvent event) {
            if(event.getInventory().getTitle() != null && event.getInventory().getTitle().equals(ConfigUtils.getStatsGuiName())) {
                event.setCancelled(true);
            }
        }
    }
}
