package cc.canyi.qilingsuit.api;

import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.AttrStringUtils;
import cc.canyi.qilingsuit.utils.EquipmentUtils;
import cc.canyi.qilingsuit.utils.PluginUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class QiLingSuitPapi extends PlaceholderExpansion {
    public boolean canRegister() {
        return PluginUtils.pluginIsActive("PlaceholderAPI");
    }

    public String getAuthor() {
        return "CanYi";
    }

    public String getIdentifier() {
        return "qilingsuit";
    }

    public String getVersion() {
        return "1.0.0";
    }


    /**
     * 返回PapiStr
     *
     * %qilingsuit_has_<suitName>%
     * %qilingsuit_attr_<suitName>%
     *
     * @param player 玩家
     * @param s 请求参数
     * @return 处理好的字符串
     */
    public String onPlaceholderRequest(Player player, String s) {
        PlayerSuitUpdateTask.Cache cache = PlayerSuitUpdateTask.getFactory_cache().get(player);
        if(cache != null) {
            if(s.split("_").length > 1) {
                String suitName = s.split("_")[1];
                if (s.contains("has")) {
                    for(Suit suit : cache.getPlayerEquipSuitMap().keySet()) {
                        if(suit.getName().equals(suitName)) {
                            //玩家目前穿了 返回穿了几个
                            return cache.getPlayerEquipSuitMap().get(suit).size() + "";
                        }
                    }
                    return "0";
                } else if (s.contains("attr")) {
                    for(Suit suit : cache.getPlayerEquipSuitMap().keySet()) {
                        if(suit.getName().equals(suitName)) {
                            //玩家目前穿了 返回属性
                            return AttrStringUtils.getNotPrefixStrList(EquipmentUtils.getStrAttrsBySuitAndPartMap(cache.getPlayerEquipSuitMap())).toString();
                        }
                    }
                    return "[]";
                }
            }
        }
        return null;
    }

}
