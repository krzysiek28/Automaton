package cell.coordinates;

import java.util.Objects;

public class CellCoordinates1D implements CellCoordinates {

    private int x;

    public CellCoordinates1D(int x){
        this.x=x;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellCoordinates1D)) return false;
        CellCoordinates1D that = (CellCoordinates1D) o;
        return getX() == that.getX();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX());
    }
}
