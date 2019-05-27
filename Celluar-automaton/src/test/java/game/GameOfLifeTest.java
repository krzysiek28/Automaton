package game;

import automaton.Automaton;
import cell.Cell;
import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;
import cell.states.BinaryState;
import cell.states.CellState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import neighbor.MooreNeighborhood;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameOfLifeTest {

    private static final int WIDTH = 10;
    
    private static final int HEIGHT = 10;

    private static final int RADIUS = 1;

    private static final boolean MAP_WRAPPING = false;

    private static final String ALIVE_RULES = "3,2";

    private static final String DEAD_RULES = "2";

    Automaton automaton;

    private static final Map<CellCoordinates, CellState> CELL_MAP;

    private static final Map<CellCoordinates, CellState> NEXT_STATE_CELL_MAP;

    static {
        CELL_MAP = new HashMap<>();
        CELL_MAP.put(new CellCoordinates2D(2,2), BinaryState.ALIVE);
        CELL_MAP.put(new CellCoordinates2D(2,3), BinaryState.ALIVE);
        CELL_MAP.put(new CellCoordinates2D(2,4), BinaryState.ALIVE);
        CELL_MAP.put(new CellCoordinates2D(5,6), BinaryState.ALIVE);
        CELL_MAP.put(new CellCoordinates2D(6,7), BinaryState.ALIVE);
        CELL_MAP.put(new CellCoordinates2D(7,8), BinaryState.ALIVE);

        NEXT_STATE_CELL_MAP = new HashMap<>();
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(1,3), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(1,2), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(1,4), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(5,7), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(6,6), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(6,8), BinaryState.ALIVE);
        NEXT_STATE_CELL_MAP.put(new CellCoordinates2D(7,7), BinaryState.ALIVE);
    }

    @Before
    public void setUp(){
        automaton = new GameOfLife(
                new UniformStateFactory(BinaryState.DEAD),
                new MooreNeighborhood(RADIUS, MAP_WRAPPING, WIDTH, HEIGHT),
                WIDTH, HEIGHT,
                GameOfLifeHelper.convertStringToCellRulesList(ALIVE_RULES),
                GameOfLifeHelper.convertStringToCellRulesList(DEAD_RULES));
        CELL_MAP.entrySet().forEach(entry -> automaton.changeCellState(entry.getKey(), entry.getValue()));
    }

    @Test
    public void nextStateTest(){
        automaton.nextState();
        Set<Cell> aliveCells = automaton.getCellSet().stream()
                .filter(cell -> cell.getState().equals(BinaryState.ALIVE))
                .collect(Collectors.toSet());
        Map<CellCoordinates2D, CellState> resultMap = aliveCells.stream()
                .collect(HashMap<CellCoordinates2D, CellState>::new,
                        (m, c) -> m.put((CellCoordinates2D) c.getCoordinates(), c.getState()),
                        (m, u) -> {});

        assertTrue(mapEquals(resultMap, NEXT_STATE_CELL_MAP));
    }

    private boolean mapEquals(Map<?, ?> firstMap, Map<?, ?> secondMap){
        if(firstMap.size() != secondMap.size()){
            return false;
        }

        Set<?> keySet = firstMap.keySet();
        if(!keySet.equals(secondMap.keySet())){
            return false;
        }
        return keySet.stream()
                .allMatch(key -> Objects.equals(firstMap.get(key), secondMap.get(key)));
    }
}
