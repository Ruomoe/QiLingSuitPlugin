package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.hooker.BigAttributeHooker;
import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import org.bukkit.entity.Player;
import org.serverct.ersha.jd.AttributeAPI;

public class AttrUtils {
    /**
     * 生效玩家BigAttr属性
     * @param player 玩家
     */
    public static void addBigAttr(Player player, AttrFactory attrFactory) {
        try {
            Class.forName(BigAttributeHooker.class.getName());
            BigAttributeHooker.getAttrMap().put(player, attrFactory.getBigAttrList());
        } catch (ClassNotFoundException ignore) {}
    }

    /**
     * 生效玩家AP属性
     * @param player 玩家
     */
    public static void addAP(Player player, AttrFactory attrFactory) {
        try{
            Class.forName(AttributeAPI.class.getName());
            AttributeAPI.addAttribute(player, "QiLingSuitPlugin", attrFactory.getApAttrList());

        }catch (ClassNotFoundException ignore){}
    }

    /**
     * 生效玩家SX属性
     * @param player 玩家
     */
    public static void addSX(Player player, AttrFactory attrFactory) {
        try{
            Class.forName(SXAttributeData.class.getName());
            Class.forName(SXAttribute.class.getName());
            SXAttributeData data = SXAttribute.getApi().loadListData(attrFactory.getSxAttrList());
            SXAttribute.getApi().setEntityAPIData(QiLingSuitPlugin.class, player.getUniqueId(), data);
        }catch (ClassNotFoundException ignore){}
    }

    /**
     * 生效玩家ILO属性
     * @param player 玩家
     */
    public static void addILO(Player player, AttrFactory attrFactory) {
        try{
            Class.forName(com.mchim.ItemLoreOrigin.API.AttributeAPI.class.getName());
            com.mchim.ItemLoreOrigin.API.AttributeAPI.addItem(QiLingSuitPlugin.class, player.getUniqueId(), PlayerUtils.generateItem(attrFactory.getIloAttrList()));
        }catch (ClassNotFoundException ignore){}
    }
}
