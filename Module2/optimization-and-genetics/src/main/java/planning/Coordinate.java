package planning;

public class Coordinate {
    public int x;
    public int y;
    public double value = 0;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setValue(double value){
        this.value = value;
    }
}
