package neighborhood;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;
import neighbor.MooreNeighborhood;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MooreNeighborhoodTest {

    private static final int WIDTH = 10;

    private static final int HEIGHT = 10;

    private CellCoordinates2D coords = new CellCoordinates2D(0,4);

    @Test
    public void cellNeighborsWithMapWrappingTest(){
        MooreNeighborhood neighborhood = new MooreNeighborhood(1,true, WIDTH, HEIGHT);
        Set<CellCoordinates> result = neighborhood.cellNeighbors(coords);
        Set<CellCoordinates> expectedResult = new HashSet<>();
        expectedResult.add(new CellCoordinates2D(0,3));
        expectedResult.add(new CellCoordinates2D(0,5));
        expectedResult.add(new CellCoordinates2D(1,3));
        expectedResult.add(new CellCoordinates2D(1,4));
        expectedResult.add(new CellCoordinates2D(1,5));
        expectedResult.add(new CellCoordinates2D(9,3));
        expectedResult.add(new CellCoordinates2D(9,4));
        expectedResult.add(new CellCoordinates2D(9,5));
        assertEquals(expectedResult, result);
    }

    @Test
    public void cellNeighborsWithoutMapWrappingTest(){
        MooreNeighborhood neighborhood = new MooreNeighborhood(1,false, WIDTH, HEIGHT);
        Set<CellCoordinates> result = neighborhood.cellNeighbors(coords);
        Set<CellCoordinates> expectedResult = new HashSet<>(Arrays.asList(new CellCoordinates2D(0,3), new CellCoordinates2D(0,5),
                new CellCoordinates2D(1,3), new CellCoordinates2D(1,4), new CellCoordinates2D(1,5)));
        assertEquals(expectedResult, result);
    }

    @Test
    public void cellNeighborsWithMapWrappingAndGreaterRadiusTest(){
        MooreNeighborhood neighborhood = new MooreNeighborhood(2,true, WIDTH, HEIGHT);
        Set<CellCoordinates> result = neighborhood.cellNeighbors(coords);
        Set<CellCoordinates> expectedResult = new HashSet<>();
        expectedResult.add(new CellCoordinates2D(0,2));
        expectedResult.add(new CellCoordinates2D(0,3));
        expectedResult.add(new CellCoordinates2D(0,5));
        expectedResult.add(new CellCoordinates2D(0,6));
        expectedResult.add(new CellCoordinates2D(1,2));
        expectedResult.add(new CellCoordinates2D(1,3));
        expectedResult.add(new CellCoordinates2D(1,4));
        expectedResult.add(new CellCoordinates2D(1,5));
        expectedResult.add(new CellCoordinates2D(1,6));
        expectedResult.add(new CellCoordinates2D(2,2));
        expectedResult.add(new CellCoordinates2D(2,3));
        expectedResult.add(new CellCoordinates2D(2,4));
        expectedResult.add(new CellCoordinates2D(2,5));
        expectedResult.add(new CellCoordinates2D(2,6));
        expectedResult.add(new CellCoordinates2D(9,2));
        expectedResult.add(new CellCoordinates2D(9,3));
        expectedResult.add(new CellCoordinates2D(9,4));
        expectedResult.add(new CellCoordinates2D(9,5));
        expectedResult.add(new CellCoordinates2D(9,6));
        expectedResult.add(new CellCoordinates2D(8,2));
        expectedResult.add(new CellCoordinates2D(8,3));
        expectedResult.add(new CellCoordinates2D(8,4));
        expectedResult.add(new CellCoordinates2D(8,5));
        expectedResult.add(new CellCoordinates2D(8,6));
        assertEquals(expectedResult, result);
    }

    @Test
    public void cellNeighborsWithoutMapWrappingAndGreaterRadiusTest(){
        MooreNeighborhood neighborhood = new MooreNeighborhood(2,false, WIDTH, HEIGHT);
        Set<CellCoordinates> result = neighborhood.cellNeighbors(coords);
        Set<CellCoordinates> expectedResult = new HashSet<>();
        expectedResult.add(new CellCoordinates2D(0,2));
        expectedResult.add(new CellCoordinates2D(0,3));
        expectedResult.add(new CellCoordinates2D(0,5));
        expectedResult.add(new CellCoordinates2D(0,6));
        expectedResult.add(new CellCoordinates2D(1,2));
        expectedResult.add(new CellCoordinates2D(1,3));
        expectedResult.add(new CellCoordinates2D(1,4));
        expectedResult.add(new CellCoordinates2D(1,5));
        expectedResult.add(new CellCoordinates2D(1,6));
        expectedResult.add(new CellCoordinates2D(2,2));
        expectedResult.add(new CellCoordinates2D(2,3));
        expectedResult.add(new CellCoordinates2D(2,4));
        expectedResult.add(new CellCoordinates2D(2,5));
        expectedResult.add(new CellCoordinates2D(2,6));
        assertEquals(expectedResult, result);
    }
}
