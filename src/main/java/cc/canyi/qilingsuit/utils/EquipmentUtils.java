package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.suit.SuitManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EquipmentUtils {
    /**
     * 获取玩家穿戴装备属性Str
     *
     * @param playerSuitEquipMap 传入玩家穿戴的套装以及穿戴部位
     * @return 返回穿戴装备套装属性List
     */
    public static List<String> getStrAttrsBySuitAndPartMap(HashMap<Suit, List<String>> playerSuitEquipMap) {
        List<String> strAttrs = new ArrayList<>();
        //找到玩家目前穿了多少套装 并且每个套装穿的部位
        playerSuitEquipMap.forEach((suit, parts) -> {
            int count = parts.size();
            if (suit.isExtendsAttr()) {
                for(int i = 1; i <= count; i++) {
                    List<String> attrStr = suit.getAttrStrMap().getOrDefault(count, null);
                    if (attrStr != null)
                        strAttrs.addAll(attrStr);
                }
            } else {
                List<String> attrStr = suit.getAttrStrMap().getOrDefault(count, null);
                if (attrStr != null)
                    strAttrs.addAll(attrStr);
            }
        });
        return strAttrs;
    }

    /**
     * 获取玩家穿戴的套装以及穿戴部位
     *
     * @param player 传入玩家
     * @return 返回玩家穿戴的套装以及穿戴部位
     */
    public static HashMap<Suit, List<String>> getSuitAndPartNameByPlayer(Player player) {
        ItemStack[] stacks = new ItemStack[5];
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    stacks[i] = player.getInventory().getHelmet();
                    break;
                case 1:
                    stacks[i] = player.getInventory().getChestplate();
                    break;
                case 2:
                    stacks[i] = player.getInventory().getLeggings();
                    break;
                case 3:
                    stacks[i] = player.getInventory().getBoots();
                    break;
                case 4:
                    stacks[i] = player.getItemInHand();
                    break;
            }
        }
        HashMap<Suit, List<String>> playerEquipSuitMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            ItemStack stack = stacks[i];
            for (String suitConfigName : SuitManager.getAllSuitConfigName()) {
                Suit suit = SuitManager.getSuitByConfigName(suitConfigName);
                String partName = getPartNameByIndex(i);
                if (suit.getContainPart().contains(partName)) {
                    if (stack != null && !stack.getType().equals(Material.AIR) && stack.getItemMeta().hasLore()) {
                        List<String> lores = stack.getItemMeta().getLore();
                        if (lores.stream().anyMatch(lore -> lore.contains(suit.getPartNeedLoreMap().get(partName)))) {
                            List<String> parts;
                            if (playerEquipSuitMap.containsKey(suit)) parts = playerEquipSuitMap.get(suit);
                            else parts = new ArrayList<>();
                            parts.add(partName);
                            playerEquipSuitMap.put(suit, parts);
                            break;
                        }
                    }
                }
            }
        }
        return playerEquipSuitMap;
    }


    /**
     * 更新玩家装备栏以及手部物品Lore -更新当前身着什么套装 缺少什么套装 按config设置
     *
     * @param player 玩家
     * @param suits  玩家穿戴的套装以及穿戴部位
     */
    public static void updatePlayerEquipmentAndItemInHandLore(Player player, HashMap<Suit, List<String>> suits) {
        ItemStack[] stacks = player.getEquipment().getArmorContents();
        ItemStack[] cloneStack = new ItemStack[stacks.length];
        for (int i = 0; i < player.getEquipment().getArmorContents().length; i++) {
            ItemStack stack = stacks[i];
        }
    }

    /**
     * 根据下标获取当前需要判断什么部位
     *
     * @param index 下标
     * @return 部位名称
     */
    public static String getPartNameByIndex(int index) {
        switch (index) {
            case 0:
                return "头部";
            case 1:
                return "胸部";
            case 2:
                return "腿部";
            case 3:
                return "脚部";
            case 4:
                return "手部";
        }
        return "Unknown";
    }
}
