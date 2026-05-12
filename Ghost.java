import java.util.Random;

public class Ghost {
    private String name;
    private Position p;
    private Random random;

    public Ghost(String name, int x, int y) {
        this.name = name;
        this.p = new Position(x, y);
        this.random = new Random();
    }


    private int getHigherPriority(int dir1, int dir2, int[] priority) {
        int pos1 = -1, pos2 = -1;
        for (int i = 0; i < priority.length; i++) {
            if (priority[i] == dir1) pos1 = i;
            if (priority[i] == dir2) pos2 = i;
        }
        return (pos1 < pos2) ? dir1 : dir2;
    }

    private boolean isValidMove(int x, int y, char[][] mazeGrid, Obstacle[] obstacles) {
        if (y < 0 || y >= mazeGrid.length) {
            return false;
        }
        if (x < 0 || x >= mazeGrid[0].length) {
            return false;
        }
        if (mazeGrid[y][x] == '#') {
            return false;
        }
        for (Obstacle obs : obstacles) {
            if (obs != null) {
                if (obs.getX() == x && obs.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getX() {
        return this.p.getX();
    }

    public int getY() {
        return this.p.getY();
    }

    public String getName() {
        return name;
    }

    public void setPosition(int x, int y) {
        this.p.setX(x);
        this.p.setY(y);
    }
}
