package neighbor;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;

import java.util.HashSet;
import java.util.Set;

public class VonNeumanNeighborhood implements CellNeighborhood {

    private int radius;

    private boolean mapWrap;

    private int width;

    private int height;

    public VonNeumanNeighborhood(int radius, boolean mapWrap, int width, int height){
        this.radius = radius;
        this.mapWrap = mapWrap;
        this.width = width;
        this.height = height;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cellCoordinates) {
        Set<CellCoordinates> cellNeighs = new HashSet<>();
        CellCoordinates2D coordinates = (CellCoordinates2D) cellCoordinates;


        int count = 0;
        for(int xCoord = coordinates.getX()-radius; xCoord < coordinates.getX(); xCoord++, count++){
            for(int yCoord = coordinates.getY()-count; yCoord <= coordinates.getY()+count; yCoord++){
                addCoordinate(cellNeighs, coordinates, xCoord, yCoord);
            }
        }
        count = radius;
        for(int xCoord = coordinates.getX(); xCoord <= coordinates.getX()+radius; xCoord++, count--){
            for(int yCoord = coordinates.getY()-count; yCoord <= coordinates.getY()+count; yCoord++){
                addCoordinate(cellNeighs, coordinates, xCoord, yCoord);
            }
        }
        return cellNeighs;
    }

    private void addCoordinate(Set<CellCoordinates> cellNeighs, CellCoordinates2D coordinates, int xCoord, int yCoord) {
        if (mapWrap == false) {
            addCoordinateWithoutMapWrapping(cellNeighs, coordinates, xCoord, yCoord);
        } else {
            addCoordinateWithMapWrapping(cellNeighs, coordinates, xCoord, yCoord);
        }
    }

    private void addCoordinateWithoutMapWrapping(Set<CellCoordinates> cellNeighs, CellCoordinates2D coordinates, int xCoord, int yCoord) {
        if (xCoord >= 0 && xCoord < width && yCoord >= 0 && yCoord < height) {
            if (isTheSameCoordinate(coordinates, xCoord, yCoord)) {
                cellNeighs.add(new CellCoordinates2D(xCoord, yCoord));
            }
        }
    }

    private void addCoordinateWithMapWrapping(Set<CellCoordinates> cellNeighs, CellCoordinates2D coordinates, int xCoord, int yCoord) {
        int newXCoordinate = xCoord;
        int newYCoordinate = yCoord;
        if (xCoord < 0)
            newXCoordinate = xCoord + width;
        if (xCoord >= width)
            newXCoordinate = xCoord - width;
        if (yCoord < 0)
            newYCoordinate = yCoord + height;
        if (yCoord >= height)
            newYCoordinate = yCoord - height;
        if (isTheSameCoordinate(coordinates, newXCoordinate, newYCoordinate))
            cellNeighs.add(new CellCoordinates2D(newXCoordinate, newYCoordinate));
    }

    private boolean isTheSameCoordinate(CellCoordinates2D coordinates, int newXCoordinate, int newYCoordinate) {
        return newXCoordinate != coordinates.getX() || newYCoordinate != coordinates.getY();
    }
}
