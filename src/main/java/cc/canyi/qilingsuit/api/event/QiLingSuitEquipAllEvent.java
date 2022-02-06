package cc.canyi.qilingsuit.api.event;

import cc.canyi.qilingsuit.suit.Suit;
import lombok.Getter;
import org.bukkit.entity.Player;

public class QiLingSuitEquipAllEvent extends QiLingSuitBaseEvent{

    @Getter
    private final Suit suit;

    public QiLingSuitEquipAllEvent(Player player, Suit suit) {
        super(player);
        this.suit = suit;
    }

}
