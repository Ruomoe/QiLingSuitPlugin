package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.suit.SuitManager;
import cc.canyi.qilingsuit.suit.SuitPassives;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ConfigUtils {
    /**
     * 初始化config
     */
    public static void init() {
        updateAllSuitFromConfig();
    }

    /**
     * 更新config配置
     */
    public static void updateAllSuitFromConfig() {
        File file = new File(QiLingSuitPlugin.getRoot(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        //check debug mode.
        QiLingSuitPlugin.setDebug(config.getBoolean("DebugMode"));

        QiLingSuitPlugin.setCheckTime(config.getLong("CheckPlayerEquipTime", 100));

        for(String suitConfigName : config.getConfigurationSection("Suit").getKeys(false)) {
            String suitName = config.getString("Suit." + suitConfigName + ".Name");
            List<String> containPart = config.getStringList("Suit." + suitConfigName + ".SuitContain");
            HashMap<String, String> partNeedLoreMap = new HashMap<>();
            for(String partName : config.getConfigurationSection("Suit." + suitConfigName + ".SuitItemNeedLore").getKeys(false))
                partNeedLoreMap.put(partName, config.getString("Suit." + suitConfigName + ".SuitItemNeedLore." + partName));
            boolean isExtendsAttr = false;
            HashMap<Integer, List<String>> attrMap = new HashMap<>();
            for(String key : config.getConfigurationSection("Suit." + suitConfigName + ".SuitAttr").getKeys(false)) {
                if(key.equals("ExtendsAttr")) {
                    isExtendsAttr = config.getBoolean("Suit." + suitConfigName + ".SuitAttr." + key, false);
                    continue;
                }
                attrMap.put(Integer.parseInt(key), config.getStringList("Suit." + suitConfigName + ".SuitAttr." + key));
            }
            SuitPassives.SuitPassivesBuilder passivesBuilder = SuitPassives.builder();

            if(config.get("SuitPassives." + suitConfigName + ".beAttack") != null) {
                HashMap<String, Double> beAttack = new HashMap<>();
                for(String command : config.getConfigurationSection("SuitPassives." + suitConfigName + ".beAttack").getKeys(false)) {
                    beAttack.put(command, config.getDouble("SuitPassives." + suitConfigName + ".beAttack." + command));
                }
                passivesBuilder.beAttack(beAttack);
            }
            if(config.get("SuitPassives." + suitConfigName + ".attacked") != null) {
                HashMap<String, Double> attack = new HashMap<>();
                for(String command : config.getConfigurationSection("SuitPassives." + suitConfigName + ".attacked").getKeys(false)) {
                    attack.put(command, config.getDouble("SuitPassives." + suitConfigName + ".attacked." + command));
                }
                passivesBuilder.attacked(attack);
            }

            Suit suit = new Suit(suitName, containPart, partNeedLoreMap, isExtendsAttr, attrMap, passivesBuilder.build());
            SuitManager.addSuit(suitConfigName, suit);
            Logger.systemOutWithPrefix("Loaded " + suitConfigName + " success.");
            Logger.systemOutWithPrefix("This suit name is " + suit.getName());
        }
    }
}
