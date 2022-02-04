package cc.canyi.qilingsuit.listener;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class QiLingSuitListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity entity_damager = event.getDamager();
        Player player;
        if(entity instanceof Player) {
            //玩家被攻击
            player = (Player) entity;
            PlayerSuitUpdateTask.Cache cache = PlayerSuitUpdateTask.getFactory_cache().get(player);
            if(cache != null) {
                for(Suit suit : cache.getPlayerEquipSuitMap().keySet()) {
                    //身穿套装
                    if(suit.getContainPart().size() == cache.getPlayerEquipSuitMap().get(suit).size()) {
                        //套装全满
                        if(suit.getPassives().getBeAttack() != null) {
                            //有被动
                            suit.getPassives().getBeAttack().forEach((command, percent) -> {
                                if(percent / 100.0 >= QiLingSuitPlugin.getRandom().nextDouble()) {
                                    PlayerUtils.runCommand(player, command
                                               .replace("<playerName>", player.getName()
                                               .replace("<suitName>", suit.getName())
                                    ));
                                }
                            });
                        }
                    }
                }
            }
        }else if(entity_damager instanceof Player) {
            //玩家攻击
            player = (Player) entity_damager;
            PlayerSuitUpdateTask.Cache cache = PlayerSuitUpdateTask.getFactory_cache().get(player);
            if(cache != null) {
                for(Suit suit : cache.getPlayerEquipSuitMap().keySet()) {
                    //身穿套装
                    if(suit.getContainPart().size() == cache.getPlayerEquipSuitMap().get(suit).size()) {
                        //套装全满
                        if(suit.getPassives().getAttacked() != null) {
                            //有被动
                            suit.getPassives().getAttacked().forEach((command, percent) -> {
                                if(percent / 100.0 >= QiLingSuitPlugin.getRandom().nextDouble()) {
                                    PlayerUtils.runCommand(player, command
                                            .replace("<playerName>", player.getName()
                                                    .replace("<suitName>", suit.getName())
                                            ));
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}
