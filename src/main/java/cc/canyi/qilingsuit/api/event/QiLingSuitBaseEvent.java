package cc.canyi.qilingsuit.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class QiLingSuitBaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
