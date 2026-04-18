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

    public void moveRandom(char[][] mazeGrid, Obstacle[] obstacles) {
        int currentX = this.p.getX();
        int currentY = this.p.getY();

        for (int attempt = 0; attempt < 20; attempt++) {
            int direction = random.nextInt(4);
            int newX = currentX;
            int newY = currentY;

            if (direction == 0) {
                newY = currentY - 1;
            } else if (direction == 1) {
                newY = currentY + 1;
            } else if (direction == 2) {
                newX = currentX + 1;
            } else if (direction == 3) {
                newX = currentX - 1;
            }

            if (isValidMove(newX, newY, mazeGrid, obstacles)) {
                this.p.setX(newX);
                this.p.setY(newY);
                return;
            }
        }
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