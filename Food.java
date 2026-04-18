public class Food {
    Position p;
    boolean eaten;

    public Food(int x, int y) {
        this.p = new Position(x,y);
        this.eaten = false;
    }

    public int getX() {
        return p.getX();
    }

    public int getY() {
        return p.getY();
    }

    public boolean isEaten() {
        return eaten;
    }

    public void eat() {
        this.eaten = true;
    }
}