package neighbor;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates2D;

import java.util.HashSet;
import java.util.Set;

public class MooreNeighborhood implements CellNeighborhood {

    private int radius;

    private boolean mapWrap;

    private int width;

    private int height;

    public MooreNeighborhood(int radius, boolean mapWrap, int width, int height){
        this.radius = radius;
        this.mapWrap = mapWrap;
        this.width = width;
        this.height = height;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cellCoordinates) {
        Set<CellCoordinates> cellNeighborhood = new HashSet<>();
        CellCoordinates2D coordinates = (CellCoordinates2D) cellCoordinates;

        for(int yCoord=coordinates.getY()-radius; yCoord<=coordinates.getY()+radius; yCoord++){
            for(int xCoord = coordinates.getX()-radius; xCoord <= coordinates.getX()+radius; xCoord++){
                if(mapWrap == false){
                    addCoordinateWithoutMapWrapping(cellNeighborhood, coordinates, xCoord, yCoord);
                } else{
                    addCoordinateWithMapWrapping(cellNeighborhood, coordinates, xCoord, yCoord);
                }
            }
        }
        return cellNeighborhood;
    }

    private void addCoordinateWithoutMapWrapping(Set<CellCoordinates> cellNeighborhood, CellCoordinates2D coordinates, int xCoord, int yCoord){
        if(xCoord>=0 && xCoord<width && yCoord>=0 && yCoord<height){
            if(isTheSameCoordinate(coordinates, xCoord, yCoord)){
                cellNeighborhood.add(new CellCoordinates2D(xCoord,yCoord));
            }
        }
    }

    private void addCoordinateWithMapWrapping(Set<CellCoordinates> cellNeighborhood, CellCoordinates2D coordinates, int yCoord, int xCoord) {
        int newXCoordinate = xCoord;
        int newYCoordinate = yCoord;
        if(xCoord<0)
            newXCoordinate = xCoord+width;
        if(xCoord>=width)
            newXCoordinate = xCoord-width;
        if(yCoord<0)
            newYCoordinate = yCoord+height;
        if(yCoord>=height)
            newYCoordinate = yCoord-height;
        if(isTheSameCoordinate(coordinates, newXCoordinate, newYCoordinate))
            cellNeighborhood.add(new CellCoordinates2D(newXCoordinate,newYCoordinate));
    }

    private boolean isTheSameCoordinate(CellCoordinates2D coordinates, int xCoordinate, int yCoordinate) {
        return xCoordinate != coordinates.getX() || yCoordinate != coordinates.getY();
    }
}
