package cc.canyi.qilingsuit.utils;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttrStringUtils {
    @Getter
    private static final Pattern pattern = Pattern.compile("\\d+(\\.\\d)?");
    @Getter
    private static final List<String> buffNames =
            Arrays.asList
                    (
                    "伤害吸收", "失明", "反胃", "急迫", "火焰保护",
                    "瞬间恢复", "隐身", "跳跃提升", "夜视", "中毒",
                    "生命恢复", "缓慢", "挖掘疲劳", "速度", "水下呼吸",
                    "凋零", "抗性提升"
                    );
    @Getter
    private static final HashMap<String, PotionEffectType> buffMap = new HashMap<>();

    public static void initBuffHashMap() {
        buffMap.put("伤害吸收", PotionEffectType.ABSORPTION);
        buffMap.put("失明", PotionEffectType.BLINDNESS);
        buffMap.put("反胃", PotionEffectType.CONFUSION);
        buffMap.put("急迫", PotionEffectType.FAST_DIGGING);
        buffMap.put("火焰保护", PotionEffectType.FIRE_RESISTANCE);
        buffMap.put("瞬间恢复", PotionEffectType.HEAL);
        buffMap.put("隐身", PotionEffectType.INVISIBILITY);
        buffMap.put("跳跃提升", PotionEffectType.JUMP);
        buffMap.put("夜视", PotionEffectType.NIGHT_VISION);
        buffMap.put("中毒", PotionEffectType.POISON);
        buffMap.put("生命恢复", PotionEffectType.REGENERATION);
        buffMap.put("缓慢", PotionEffectType.SLOW);
        buffMap.put("挖掘疲劳", PotionEffectType.SLOW_DIGGING);
        buffMap.put("速度", PotionEffectType.SPEED);
        buffMap.put("最大血量", PotionEffectType.WATER_BREATHING);
        buffMap.put("凋零", PotionEffectType.WITHER);
        buffMap.put("抗性提升", PotionEffectType.DAMAGE_RESISTANCE);
    }

    /**
     * 获得属性工厂
     * @return 属性工厂实例
     */
    public static AttrFactory getAttrFactory() {
        return new AttrFactory();
    }

    /**
     * 获取Lore中的数值
     * @param lore lore
     * @return 返回数值
     */
    public static double getLoreHasNumber(String lore){
        Matcher matcher = pattern.matcher(ChatColor.stripColor(lore));
        if(matcher.find()){
            return Double.parseDouble(matcher.group());
        }else{
            return 0.0;
        }
    }
}
