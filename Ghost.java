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
    
    public void chasePacman(Position pacmanPos, char[][] mazeGrid, Obstacle[] obstacles) {
        int currentX = this.p.getX();
        int currentY = this.p.getY();

        int bestDir = -1;
        int bestDistance = Integer.MAX_VALUE;

        int[][] dirs = {{0, -1, 0}, {0, 1, 1}, {1, 0, 2}, {-1, 0, 3}};

        for (int[] dir : dirs) {
            int newX = currentX + dir[0];
            int newY = currentY + dir[1];
            int dirCode = dir[2];

            if (isValidMove(newX, newY, mazeGrid, obstacles)) {
                Position newPos = new Position(newX, newY);
                int distance = newPos.calculateDistance(pacmanPos);

                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestDir = dirCode;
                } else if (distance == bestDistance && bestDir != -1) {
                    bestDir = break_tie(dirCode, bestDir);
                }
            }
        }

        if (bestDir == 0) {
            this.p.setY(currentY - 1);
        } else if (bestDir == 1) {
            this.p.setY(currentY + 1);
        } else if (bestDir == 2) {
            this.p.setX(currentX + 1);
        } else if (bestDir == 3) {
            this.p.setX(currentX - 1);
        }
    }

    private int break_tie(int newDir, int currentDir) {
        if (this.name.equals("Blinky")) {
            int[] priority = {0, 3, 1, 2};
            return getHigherPriority(newDir, currentDir, priority);
        } else if (this.name.equals("Pinky")) {
            int[] priority = {0, 2, 1, 3};
            return getHigherPriority(newDir, currentDir, priority);
        } else if (this.name.equals("Inky")) {
            int[] priority = {1, 3, 0, 2};
            return getHigherPriority(newDir, currentDir, priority);
        }
        return currentDir;
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
