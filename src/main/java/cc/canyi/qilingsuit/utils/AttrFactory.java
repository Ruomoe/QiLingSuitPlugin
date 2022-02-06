package cc.canyi.qilingsuit.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class AttrFactory {

    private String lore;
    private PotionEffect effect;
    private String type;
    private String str;
    private Player player;

    @Getter
    private boolean success;

    @Getter
    private final List<String> bigAttrList = new ArrayList<>();
    @Getter
    private final List<String> sxAttrList = new ArrayList<>();
    @Getter
    private final List<String> apAttrList = new ArrayList<>();
    @Getter
    private final List<String> iloAttrList = new ArrayList<>();
    /**
     * 流式设置config原本Str
     * @param str config 原本Str
     * @return 返回自身
     */
    public AttrFactory setStr(String str) {
        this.str = str;
        return this;
    }

    /**
     * 流式设置player
     * @param player 玩家
     * @return 返回自身
     */
    public AttrFactory setPlayer(Player player) {
        this.player = player;
        return this;
    }

    /**
     * 流式分析configStr的前缀类型
     * @return 返回自身
     */
    public AttrFactory analyseType() {
        if (str.contains("[") && str.contains("]")) {
            type = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        }
        return this;
    }

    /**
     * 流式构建对应插件的属性Lore或者mc原版buff
     * @return 返回自身
     */
    public AttrFactory buildLoreOrEffect() {
        if (type == null) return this;
        //System.out.println("获取到 " + type);
        if (Arrays.asList("BIGATTR", "SX", "AP", "ILO").contains(type)) {
            lore = str.substring(type.length() + 2);
        } else if (type.equals("BUFF")) {
            String effectName = str.substring(type.length() + 2);
            String buffName = effectName.substring(0, effectName.length() - 1);
            if (AttrStringUtils.getBuffNames().contains(buffName)) {
                PotionEffectType type = AttrStringUtils.getBuffMap().get(buffName);
                int level = Math.max((int) AttrStringUtils.getLoreHasNumber(str) - 1, 0);
                effect = new PotionEffect(type, 100, level);
            }
        }
        return this;
    }

    /**
     * 获取添加到各自插件的属性集Map
     * @return 返回本身
     */
    public AttrFactory returnSelf() {
        if (lore == null && effect == null)
            return returnSelfAndSetResult(false);
        if (type == null)
            return returnSelfAndSetResult(false);
        if (player == null || !player.isOnline()) return returnSelfAndSetResult(false);
        switch (type) {
            case "BUFF": {
                player.removePotionEffect(effect.getType());
                player.addPotionEffect(effect);
                return returnSelfAndSetResult(true);
            }
            case "BIGATTR": {
                if (PluginUtils.pluginIsActive("BigAttribute")) {
                    bigAttrList.add(lore);
                    return returnSelfAndSetResult(true);
                }
                return returnSelfAndSetResult(false);
            }
            case "SX": {
                if (PluginUtils.pluginIsActive("SX-Attribute")) {
                    sxAttrList.add(lore);
                    return returnSelfAndSetResult(true);
                }
                return returnSelfAndSetResult(false);
            }
            case "AP": {
                if (PluginUtils.pluginIsActive("AttributePlus")) {
                    apAttrList.add(lore);
                    return returnSelfAndSetResult(true);
                }
                return returnSelfAndSetResult(false);
            }
            case "ILO": {
                if (PluginUtils.pluginIsActive("ItemLoreOrigin")) {
                    iloAttrList.add(lore);
                    return returnSelfAndSetResult(true);
                }
                return returnSelfAndSetResult(false);
            }
        }
        return returnSelfAndSetResult(false);
    }

    /**
     * 设置result 并返回本身
     * @param result 设置的结果
     * @return 返回本身
     */
    private AttrFactory returnSelfAndSetResult(boolean result) {
        this.success = result;
        return this;
    }

}
