package gui;

import cell.coordinates.CellCoordinates2D;
import cell.states.BinaryState;
import cell.states.CellState;

import java.util.HashMap;
import java.util.Map;

public final class StructureHelper {

    private StructureHelper(){}

    public static Map<CellCoordinates2D, CellState> putBlock(CellCoordinates2D coordinates, int width, int height){
        int x = coordinates.getX();
        int y = coordinates.getY();
        return parseAndCreateMapEntries(String.format("(%d,%d);(%d,%d);(%d,%d);(%d,%d)", x, y, x+1, y+1, x,y+1, x+1, y), width, height);
    }

    public static Map<CellCoordinates2D, CellState> getBoat(CellCoordinates2D coordinates, int width, int height){
        int x = coordinates.getX();
        int y = coordinates.getY();
        return parseAndCreateMapEntries(String.format("(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)", x, y, x+1, y, x, y+1, x+2, y+1, x+1, y+2), width, height);
    }

    public static Map<CellCoordinates2D, CellState> putKite(CellCoordinates2D coordinates, int width, int height) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return parseAndCreateMapEntries(String.format("(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)", x, y, x+1, y, x, y+1, x+2, y, x, y+1, x+1, y+2), width, height);
    }

    public static Map<CellCoordinates2D, CellState> putGun(CellCoordinates2D coordinates, int width, int height) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return parseAndCreateMapEntries(String.format("(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)" +
                        ";(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d);(%d,%d)",
        x, y + 4, x, y + 5, x + 1, y + 4, x + 1, y + 5, x + 10, y + 4,
        x + 10, y + 5, x + 10, y + 6, x + 11, y + 3, x + 12, y + 2, x + 13, y + 2,
        x + 11, y + 7, x + 12, y + 8, x + 13, y + 8, x + 14, y + 5, x + 15, y + 3,
        x + 16, y + 4, x + 16, y + 5, x + 16, y + 6, x + 15, y + 7, x + 17, y + 5,
        x + 20, y + 2, x + 20, y + 3, x + 20, y + 4, x + 21, y + 2, x + 21, y + 3,
        x + 21, y + 4, x + 22, y + 1, x + 22, y + 5, x + 24, y, x + 24, y + 1,
        x + 24, y + 5, x + 24, y + 6, x + 34, y + 2, x + 34, y + 3, x + 35, y + 2,
        x + 35, y + 3), width, height);
    }

    private static void putIfCellIsOnBoard(Map<CellCoordinates2D, CellState> map, int x, int y,  int width, int height){
        if(x < width && y < height){
            map.put(new CellCoordinates2D(x , y), BinaryState.ALIVE);
        }
    }

    /**
     * This method resolving map entries using string template. This method doesn't have validation!
     * @param value accept a narrow scope prepared value. Template value is in form "(x,y);(x2,y2)"
     * @return cooridnates 2 dim map with alive cells
     */
    private static Map<CellCoordinates2D, CellState> parseAndCreateMapEntries(String value, int width, int height){
        Map<CellCoordinates2D, CellState> result = new HashMap<>();
        String regex = value.replace("(", "");
        regex = regex.replace(")", "");
        String[] pointPairs = regex.split(";");
        for (String pointPair : pointPairs){
            String[] point = pointPair.split(",");
            int x = Integer.parseInt(point[0]);
            int y = Integer.parseInt(point[1]);
            if(x < width && y < height){
                result.put(new CellCoordinates2D(x , y), BinaryState.ALIVE);
            }
        }
        return result;
    }
}
