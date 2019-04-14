package game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class GameOfLifeHelper {

    private GameOfLifeHelper(){}

    public static List<Integer> convertStringToCellRulesList(String rulesAsString) {
        String[] ruleAsStringList = rulesAsString.split(",");
        return Arrays.stream(ruleAsStringList).map(Integer::parseInt).collect(Collectors.toList());
    }
}
