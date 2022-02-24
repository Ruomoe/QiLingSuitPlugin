package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.suit.Suit;
import cc.canyi.qilingsuit.suit.SuitLoreSetting;
import cc.canyi.qilingsuit.suit.SuitManager;
import cc.canyi.qilingsuit.suit.SuitPassives;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.serverct.ersha.jd.S;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ConfigUtils {
    @Getter
    private static String statsGuiName;


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

        statsGuiName = config.getString("StatsGuiName").replace("&", "§");

        SuitLoreSetting suitLoreSetting = new SuitLoreSetting();
        suitLoreSetting.setEnable(config.getBoolean("SuitLore.enable"));
        suitLoreSetting.setLoc(SuitLoreSetting.Location.getLocationFromString(config.getString("SuitLore.location")));
        suitLoreSetting.setLoreFindTop(config.getString("SuitLore.loreFindTop").replace("&", "§"));
        HashMap<String, SuitLoreSetting.LoreFormat> formatHashMap = new HashMap<>();
        for(String partName : config.getConfigurationSection("SuitLore.loreFormat").getKeys(false)) {
            formatHashMap.put(partName, new SuitLoreSetting.LoreFormat(
                    config.getString("SuitLore.loreFormat." + partName + ".have").replace("&", "§"),
                    config.getString("SuitLore.loreFormat." + partName + ".notHave").replace("&", "§")
            ));
        }
        suitLoreSetting.setFormats(formatHashMap);

        loadAllSuit();
//        for(String suitConfigName : config.getConfigurationSection("Suit").getKeys(false)) {
//            String suitName = config.getString("Suit." + suitConfigName + ".Name");
//            List<String> containPart = config.getStringList("Suit." + suitConfigName + ".SuitContain");
//            HashMap<String, String> partNeedLoreMap = new HashMap<>();
//            for(String partName : config.getConfigurationSection("Suit." + suitConfigName + ".SuitItemNeedLore").getKeys(false))
//                partNeedLoreMap.put(partName, config.getString("Suit." + suitConfigName + ".SuitItemNeedLore." + partName));
//            boolean isExtendsAttr = false;
//            HashMap<Integer, List<String>> attrMap = new HashMap<>();
//            for(String key : config.getConfigurationSection("Suit." + suitConfigName + ".SuitAttr").getKeys(false)) {
//                if(key.equals("ExtendsAttr")) {
//                    isExtendsAttr = config.getBoolean("Suit." + suitConfigName + ".SuitAttr." + key, false);
//                    continue;
//                }
//                attrMap.put(Integer.parseInt(key), config.getStringList("Suit." + suitConfigName + ".SuitAttr." + key));
//            }
//            SuitPassives.SuitPassivesBuilder passivesBuilder = SuitPassives.builder();
//
//            if(config.get("SuitPassives." + suitConfigName + ".beAttack") != null) {
//                HashMap<String, Double> beAttack = new HashMap<>();
//                for(String command : config.getConfigurationSection("SuitPassives." + suitConfigName + ".beAttack").getKeys(false)) {
//                    beAttack.put(command, config.getDouble("SuitPassives." + suitConfigName + ".beAttack." + command));
//                }
//                passivesBuilder.beAttack(beAttack);
//            }
//            if(config.get("SuitPassives." + suitConfigName + ".attacked") != null) {
//                HashMap<String, Double> attack = new HashMap<>();
//                for(String command : config.getConfigurationSection("SuitPassives." + suitConfigName + ".attacked").getKeys(false)) {
//                    attack.put(command, config.getDouble("SuitPassives." + suitConfigName + ".attacked." + command));
//                }
//                passivesBuilder.attacked(attack);
//            }
//            if (config.get("SuitPassives." + suitConfigName + ".equipAll") != null) {
//                passivesBuilder.equipAll(config.getStringList("SuitPassives." + suitConfigName + ".equipAll"));
//            }
//            if (config.get("SuitPassives." + suitConfigName + ".changeFromAllEquipStats") != null) {
//                passivesBuilder.takeoffFromAll(config.getStringList("SuitPassives." + suitConfigName + ".changeFromAllEquipStats"));
//            }
//
//            Suit suit = new Suit(suitName, containPart, partNeedLoreMap, isExtendsAttr, attrMap, passivesBuilder.build());
//            SuitManager.addSuit(suitConfigName, suit);
//            Logger.systemOutWithPrefix("Loaded " + suitConfigName + " success.");
//            Logger.systemOutWithPrefix("This suit name is " + suit.getName());
//        }
    }

    /**
     * 加载所有套装
     */
    public static void loadAllSuit() {
        File dir = new File(QiLingSuitPlugin.getRoot(), "suits");
        if(!dir.exists() || !dir.isDirectory()) dir.mkdir();
        File[] suitFiles = dir.listFiles();
        if(suitFiles != null && suitFiles.length > 0) {
            for(File suitFile : suitFiles) {
                YamlConfiguration suitYaml = YamlConfiguration.loadConfiguration(suitFile);
                String suitConfigName = getFileNotSuffixName(suitFile);
                String suitName = suitYaml.getString("Name");
                List<String> containPart = suitYaml.getStringList("SuitContain");
                HashMap<String, String> partNeedLoreMap = new HashMap<>();
                for(String partName : suitYaml.getConfigurationSection("SuitItemNeedLore").getKeys(false))
                    partNeedLoreMap.put(partName, suitYaml.getString("SuitItemNeedLore." + partName));
                boolean isExtendsAttr = false;
                HashMap<Integer, List<String>> attrMap = new HashMap<>();
                for(String key : suitYaml.getConfigurationSection("SuitAttr").getKeys(false)) {
                    if(key.equals("ExtendsAttr")) {
                        isExtendsAttr = suitYaml.getBoolean("SuitAttr." + key, false);
                        continue;
                    }
                    attrMap.put(Integer.parseInt(key), suitYaml.getStringList("SuitAttr." + key));
                }
                SuitPassives.SuitPassivesBuilder passivesBuilder = SuitPassives.builder();

                if(suitYaml.get("SuitPassives." + suitConfigName + ".beAttack") != null) {
                    HashMap<String, Double> beAttack = new HashMap<>();
                    for(String command : suitYaml.getConfigurationSection("SuitPassives." + suitConfigName + ".beAttack").getKeys(false)) {
                        beAttack.put(command, suitYaml.getDouble("SuitPassives." + suitConfigName + ".beAttack." + command));
                    }
                    passivesBuilder.beAttack(beAttack);
                }
                if(suitYaml.get("SuitPassives." + suitConfigName + ".attacked") != null) {
                    HashMap<String, Double> attack = new HashMap<>();
                    for(String command : suitYaml.getConfigurationSection("SuitPassives." + suitConfigName + ".attacked").getKeys(false)) {
                        attack.put(command, suitYaml.getDouble("SuitPassives." + suitConfigName + ".attacked." + command));
                    }
                    passivesBuilder.attacked(attack);
                }
                if (suitYaml.get("SuitPassives." + suitConfigName + ".equipAll") != null) {
                    passivesBuilder.equipAll(suitYaml.getStringList("SuitPassives." + suitConfigName + ".equipAll"));
                }
                if (suitYaml.get("SuitPassives." + suitConfigName + ".changeFromAllEquipStats") != null) {
                    passivesBuilder.takeoffFromAll(suitYaml.getStringList("SuitPassives." + suitConfigName + ".changeFromAllEquipStats"));
                }

                Suit suit = new Suit(suitName, containPart, partNeedLoreMap, isExtendsAttr, attrMap, passivesBuilder.build());
                SuitManager.addSuit(suitConfigName, suit);
                Logger.systemOutWithPrefix("Loaded " + suitConfigName + " success.");
                Logger.systemOutWithPrefix("This suit name is " + suit.getName());
            }
        }
    }

    public static String getFileNotSuffixName(File file) {
        String allName = file.getName();
        return allName.substring(0, allName.lastIndexOf(".") + 1);
    }
}
