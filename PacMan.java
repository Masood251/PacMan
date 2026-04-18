public class PacMan {
    int lives;
    Position p;
    int score;

    public PacMan(int lives, int x, int y) {
        this.lives = lives;
        this.p = new Position(x, y);
        this.score = 0;
    }

    public void tryMove(int direction, char[][] mazeGrid, Obstacle[] obstacles) {
        int currentX = this.p.getX();
        int currentY = this.p.getY();

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

    public void addScore() {
        this.score++;
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

    public void respawn(int startX, int startY) {
        this.p.setX(startX);
        this.p.setY(startY);
    }

    public int getX() {
        return this.p.getX();
    }

    public int getY() {
        return this.p.getY();
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }
}