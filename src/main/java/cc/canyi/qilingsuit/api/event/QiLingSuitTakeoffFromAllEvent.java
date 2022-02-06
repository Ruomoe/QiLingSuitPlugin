package cc.canyi.qilingsuit.api.event;

import cc.canyi.qilingsuit.suit.Suit;
import lombok.Getter;
import org.bukkit.entity.Player;

public class QiLingSuitTakeoffFromAllEvent extends QiLingSuitBaseEvent{
    @Getter
    private final Suit suit;

    public QiLingSuitTakeoffFromAllEvent(Player player, Suit suit) {
        super(player);
        this.suit = suit;
    }
}
