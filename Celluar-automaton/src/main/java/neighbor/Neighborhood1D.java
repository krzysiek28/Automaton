package neighbor;

import cell.coordinates.CellCoordinates;
import cell.coordinates.CellCoordinates1D;

import java.util.HashSet;
import java.util.Set;

public class Neighborhood1D implements CellNeighborhood {

    private int size;

    public Neighborhood1D(int size){
        this.size = size;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cellCoordinates) {
        Set<CellCoordinates> cellNeighs = new HashSet<>();
        CellCoordinates1D coordinates = (CellCoordinates1D) cellCoordinates;

        if(coordinates.getX()-1 >= 0 && coordinates.getX()+1 < size){
            addNeighborWithTheSameCoordinate(cellNeighs, coordinates);
        } else if(coordinates.getX()-1 < 0){
            cellNeighs.add(new CellCoordinates1D(size-1));
            cellNeighs.add(new CellCoordinates1D(coordinates.getX()));
            cellNeighs.add(new CellCoordinates1D(coordinates.getX()+1));
        } else {
            cellNeighs.add(new CellCoordinates1D(coordinates.getX()-1));
            cellNeighs.add(new CellCoordinates1D(coordinates.getX()));
            cellNeighs.add(new CellCoordinates1D(0));
        }
        return cellNeighs;
    }

    private void addNeighborWithTheSameCoordinate(Set<CellCoordinates> cellNeighs, CellCoordinates1D coordinates) {
        for(int xCoordinate = coordinates.getX()-1; xCoordinate <= coordinates.getX()+1; xCoordinate++) {
            cellNeighs.add(new CellCoordinates1D(xCoordinate));
        }
    }
}
