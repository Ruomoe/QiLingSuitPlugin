package cc.canyi.qilingsuit.suit;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class SuitPassives {
    private HashMap<String, Double> beAttack;
    private HashMap<String, Double> attacked;
}
