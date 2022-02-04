package cc.canyi.qilingsuit.task;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;

public class PlayerSuitUpdateTask implements Runnable {
    @Setter
    @Getter
    private boolean over = false;
    @Override
    public void run() {
        if(over) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            HashMap<Suit, List<String>> playerEquipSuitMap = EquipmentUtils.getSuitAndPartNameByPlayer(player);
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
        });
    }
}
