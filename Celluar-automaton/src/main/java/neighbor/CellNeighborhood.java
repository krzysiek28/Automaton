package neighbor;

import cell.coordinates.CellCoordinates;

import java.util.Set;

public interface CellNeighborhood {

    Set<CellCoordinates> cellNeighbors(CellCoordinates cellCoordinates);
}
