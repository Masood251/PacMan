public class Obstacle {
    Position p;

    public Obstacle(int x, int y) {
        this.p = new Position(x, y);
    }

    public int getX() {
        return this.p.getX();
    }

    public int getY() {
        return this.p.getY();
    }
}