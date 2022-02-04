package cc.canyi.qilingsuit.suit;

import java.util.HashMap;
import java.util.Set;

public class SuitManager {
    /**
        存放所有的套装
        k->v : 套装CONFIG名称->套装对象
     */
    private static final HashMap<String, Suit> SUIT_MAP = new HashMap<>();

    /**
     * 添加套装对象
     * @param configName 套装config中的名字
     * @param suit 套装对象
     */
    public static void addSuit(String configName, Suit suit) {
        SUIT_MAP.put(configName, suit);
    }

    /**
     * 用configName获取套装对象
     * @param configName 套装config中的名字
     * @return 套装对象
     */
    public static Suit getSuitByConfigName(String configName) {
        return SUIT_MAP.get(configName);
    }

    /**
     * 获取所有目前生效的套装configName
     * @return configName集合
     */
    public static Set<String> getAllSuitConfigName() {
        return SUIT_MAP.keySet();
    }
}
