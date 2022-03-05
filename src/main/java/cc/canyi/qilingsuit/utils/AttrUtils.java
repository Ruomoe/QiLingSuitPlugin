package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.hooker.BigAttributeHooker;
import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.api.SXAttributeAPI;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import github.saukiya.sxattribute.data.condition.SXConditionType;
import org.bukkit.entity.Player;
import org.serverct.ersha.jd.AttributeAPI;

import java.lang.reflect.Field;

public class AttrUtils {
    /**
     * 生效玩家BigAttr属性
     * @param player 玩家
     */
    public static void addBigAttr(Player player, AttrFactory attrFactory) {
        try {
            Class.forName("cc.canyi.qilingsuit.hooker.BigAttributeHooker");
            BigAttributeHooker.getAttrMap().put(player, attrFactory.getBigAttrList());
        } catch (Exception ignore) {}
    }

    /**
     * 生效玩家AP属性
     * @param player 玩家
     */
    public static void addAP(Player player, AttrFactory attrFactory) {
        try{
            Class.forName("org.serverct.ersha.jd.AttributeAPI");
            AttributeAPI.addAttribute(player, "QiLingSuitPlugin", attrFactory.getApAttrList());

        }catch (Exception ignore){}
    }

    /**
     * 生效玩家SX属性
     * @param player 玩家
     */
    public static void addSX(Player player, AttrFactory attrFactory) {
        try{
            Class.forName("github.saukiya.sxattribute.data.attribute.SXAttributeData");
            Class.forName("github.saukiya.sxattribute.SXAttribute");
            Class.forName("github.saukiya.sxattribute.api.SXAPI");
            SXAttributeData data = SXAttribute.getApi().loadListData(attrFactory.getSxAttrList());
            SXAttribute.getApi().setEntityAPIData(QiLingSuitPlugin.class, player.getUniqueId(), data);
        }catch (Exception ignore){
            try{
                Class.forName("github.saukiya.sxattribute.api.SXAttributeAPI");
                SXAttributeAPI apiClass = null;
                for(Field field : SXAttribute.class.getDeclaredFields()) {
                    if(field.getName().equals("api")) {
                        field.setAccessible(true);
                        apiClass = (SXAttributeAPI) field.get(PluginUtils.getPlugin("SX-Attribute"));
                    }
                }
                if(apiClass != null){
                    SXAttributeData data = apiClass.getLoreData(player, new SXConditionType(SXConditionType.Type.OTHER), attrFactory.getSxAttrList());
                    apiClass.setEntityAPIData(QiLingSuitPlugin.class, player.getUniqueId(), data);
                }
            }catch (ClassNotFoundException ignore2){} catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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

    /**
     * 移除加成属性 用于刷新
     * @param player 需要移除的玩家
     */
    public static void clearAllAttribute(Player player) {
        if (PluginUtils.pluginIsActive("SX-Attribute")) {
            try {
                Class.forName("github.saukiya.sxattribute.data.attribute.SXAttributeData");
                Class.forName("github.saukiya.sxattribute.SXAttribute");
                Class.forName("github.saukiya.sxattribute.api.SXAPI");
                SXAttribute.getApi().removeEntityAPIData(QiLingSuitPlugin.class, player.getUniqueId());
            }catch (Exception ignore) {
                try{
                    Class.forName("github.saukiya.sxattribute.api.SXAttributeAPI");
                    SXAttributeAPI apiClass = null;
                    for(Field field : SXAttribute.class.getDeclaredFields()) {
                        if(field.getName().equals("api")) {
                            field.setAccessible(true);
                            apiClass = (SXAttributeAPI) field.get(PluginUtils.getPlugin("SX-Attribute"));
                        }
                    }
                    if(apiClass != null){
                        apiClass.removeEntityAPIData(QiLingSuitPlugin.class, player.getUniqueId());
                    }
                }catch (ClassNotFoundException ignore2){} catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (PluginUtils.pluginIsActive("AttributePlus")) {
            AttributeAPI.deleteAttribute(player, "QiLingSuitPlugin");
        }
        if (PluginUtils.pluginIsActive("BigAttribute")) {
            BigAttributeHooker.getAttrMap().remove(player);
        }
        if (PluginUtils.pluginIsActive("ItemLoreOrigin")) {
            com.mchim.ItemLoreOrigin.API.AttributeAPI.removeItems(QiLingSuitPlugin.class, player.getUniqueId());
        }
    }
}

