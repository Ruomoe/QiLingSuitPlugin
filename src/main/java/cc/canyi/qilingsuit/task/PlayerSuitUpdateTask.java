package cc.canyi.qilingsuit.task;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.api.event.QiLingSuitEquipAllEvent;
import cc.canyi.qilingsuit.api.event.QiLingSuitTakeoffFromAllEvent;
import cc.canyi.qilingsuit.hooker.BigAttributeHooker;
import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.utils.*;
import github.saukiya.sxattribute.SXAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.serverct.ersha.jd.AttributeAPI;
import org.serverct.ersha.jd.C;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerSuitUpdateTask implements Runnable {
    @Getter
    private static final HashMap<Player, Cache> factory_cache = new HashMap<>();

    @AllArgsConstructor
    @Data
    public static class Cache {
        private HashMap<Suit, List<String>> playerEquipSuitMap;
        private AttrFactory factory;
    }

    @Setter
    @Getter
    private boolean over = false;
    @Override
    public void run() {
        if(over) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            //Clear player attribute
            AttrUtils.clearAllAttribute(player);

            HashMap<Suit, List<String>> playerEquipSuitMap = EquipmentUtils.getSuitAndPartNameByPlayer(player);

            playerEquipSuitMap.forEach((suit, parts) -> {
                if(factory_cache.containsKey(player) && parts.size() == suit.getContainPart().size() && factory_cache.get(player).getPlayerEquipSuitMap().getOrDefault(suit, new ArrayList<>()).size() != suit.getContainPart().size()) {
                    //全满 且是第一次全满 缓存不是全满 call event
                    QiLingSuitEquipAllEvent event = new QiLingSuitEquipAllEvent(player, suit);
                    Bukkit.getPluginManager().callEvent(event);
                }else if(factory_cache.containsKey(player) && parts.size() < suit.getContainPart().size() && factory_cache.get(player).getPlayerEquipSuitMap().getOrDefault(suit, new ArrayList<>()).size() == suit.getContainPart().size()) {
                    //不全满 且缓存是全满 call event
                    QiLingSuitTakeoffFromAllEvent event = new QiLingSuitTakeoffFromAllEvent(player, suit);
                    Bukkit.getPluginManager().callEvent(event);
                }
            });

            List<String> strAttrs = EquipmentUtils.getStrAttrsBySuitAndPartMap(playerEquipSuitMap);
            AttrFactory factory = AttrStringUtils.getAttrFactory();
            for (String str : strAttrs) {
                if (!factory.setPlayer(player).setStr(str).analyseType().buildLoreOrEffect().returnSelf().isSuccess()) {
                    if (QiLingSuitPlugin.isDebug()) {
                        Logger.systemOutErrorWithPrefix("Player " + player.getName() + " Result False, please check out! ↓↓↓↓↓↓↓");
                        Logger.systemOutErrorWithPrefix("str: " + str);
                        Logger.systemOutErrorWithPrefix("[map: " + playerEquipSuitMap);
                        Logger.systemOutErrorWithPrefix("allStrs: " + strAttrs);
                    }
                    break;
                }
            }
            if(QiLingSuitPlugin.isDebug()) {
                player.sendMessage("Now has " + playerEquipSuitMap.size() + " suits!");
                playerEquipSuitMap.forEach((suit, list) -> {
                    player.sendMessage("Suit: " + suit.getName() + " has " + list.toString()+ " parts!");
                });
            }
            if(!factory.getApAttrList().isEmpty()) AttrUtils.addAP(player, factory);
            if(!factory.getBigAttrList().isEmpty()) AttrUtils.addBigAttr(player, factory);
            if(!factory.getSxAttrList().isEmpty()) AttrUtils.addSX(player, factory);
            if(!factory.getIloAttrList().isEmpty()) AttrUtils.addILO(player, factory);

            //缓存用于读取目前玩家的套装加成
            factory_cache.put(player, new Cache(playerEquipSuitMap, factory));
        });
    }

}
