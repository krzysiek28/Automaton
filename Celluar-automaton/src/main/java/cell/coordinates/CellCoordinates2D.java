package cell.coordinates;

import java.util.Objects;

public class CellCoordinates2D implements CellCoordinates {

    private int x;

    private int y;

    public CellCoordinates2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellCoordinates2D)) return false;
        CellCoordinates2D that = (CellCoordinates2D) o;
        return getX() == that.getX() &&
                getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
