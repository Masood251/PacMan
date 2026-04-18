public class Maze {
    private static int dotsRemaining;
    private char[][] grid;
    private int height;
    private int width;
    private Food[] foods;
    private Obstacle[] obstacles;

    public Maze(int height, int width) {
        this.grid = new char[height][width];
        this.height = height;
        this.width = width;
        Maze.dotsRemaining = 0;
    }

    public void loadLayout(String[] layout) {
        if (layout.length != height) {
            System.out.println("Error: Layout height does not match maze height");
            return;
        }

        int foodCount = 0;
        int obstacleCount = 0;

        for (int i = 0; i < height; i++) {
            if (layout[i].length() != width) {
                System.out.println("Error: Layout width mismatch at row " + i);
                return;
            }
            for (int j = 0; j < width; j++) {
                char ch = layout[i].charAt(j);
                if (ch == '.') {
                    foodCount++;
                } else if (ch == '#') {
                    obstacleCount++;
                }
            }
        }

        foods = new Food[foodCount];
        obstacles = new Obstacle[obstacleCount];

        int foodIndex = 0;
        int obstacleIndex = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char ch = layout[i].charAt(j);
                grid[i][j] = ch;

                if (ch == '.') {
                    foods[foodIndex] = new Food(j, i);
                    foodIndex++;
                    Maze.dotsRemaining++;
                } else if (ch == '#') {
                    obstacles[obstacleIndex] = new Obstacle(j, i);
                    obstacleIndex++;
                }
            }
        }
    }

    public boolean hasFood(int x, int y) {
        if (x < 0 || x >= width) {
            return false;
        }
        if (y < 0 || y >= height) {
            return false;
        }
        for (Food f : foods) {
            if (f != null && !f.isEaten() && f.getX() == x && f.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public int eatFoodAt(int x, int y) {
        for (Food f : foods) {
            if (f != null && !f.isEaten() && f.getX() == x && f.getY() == y) {
                f.eat();
                Maze.dotsRemaining--;
                grid[y][x] = ' ';
                return 1;
            }
        }
        return 0;
    }

    public static int getDotsRemaining() {
        return dotsRemaining;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }
}