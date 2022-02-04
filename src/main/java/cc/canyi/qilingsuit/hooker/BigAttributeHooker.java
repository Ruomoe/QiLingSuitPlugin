package cc.canyi.qilingsuit.hooker;

import cc.canyi.qilingsuit.utils.PluginUtils;
import com.killercraft.jimy.BAAPI.AttributeSupplier;
import com.killercraft.jimy.BAAPI.AttributeSupplierManager;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BigAttributeHooker implements AttributeSupplier {
    static {
        if(PluginUtils.pluginIsActive("BigAttribute"))
            AttributeSupplierManager.registerSupplier("QiLingSuit_BigAttributeHooker", new BigAttributeHooker());
    }
    @Getter
    private static final HashMap<Player, List<String>> attrMap = new HashMap<>();

    @Override
    public List<String> getAttackAtr(Player player) {
        return attrMap.getOrDefault(player, new ArrayList<>());
    }

    @Override
    public List<String> getDamageAtr(Player player) {
        return attrMap.getOrDefault(player, new ArrayList<>());
    }

    @Override
    public List<String> getTaskAtr(Player player) {
        return attrMap.getOrDefault(player, new ArrayList<>());
    }
}
