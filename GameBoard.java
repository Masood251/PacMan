import java.util.Scanner;

public class GameBoard {
    private Maze maze;
    private PacMan pacman;
    private Ghost[] ghosts;
    private boolean isRunning;
    private Scanner sc;

    public GameBoard() {
        this.sc = new Scanner(System.in);
        this.isRunning = true;
        initializeGame();
    }

    public void initializeGame() {
        maze = new Maze(12, 20);
        String[] layout = {
                "####################",
                "#..................#",
                "#....#........#....#",
                "#....#........#....#",
                "#........#....#....#",
                "#..................#",
                "#.....#............#",
                "#..#..#.........#..#",
                "#.....#........#...#",
                "#.....####.....###.#",
                "#..................#",
                "####################"
        };


        maze.loadLayout(layout);
        pacman = new PacMan(3, 2, 4);
        ghosts = new Ghost[3];
        ghosts[0] = new Ghost("Blinky", 10, 5);
        ghosts[1] = new Ghost("Pinky", 12, 6);
        ghosts[2] = new Ghost("Inky", 8, 7);
    }

    public void display() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
        System.out.println("====================");
        System.out.println("Score: " + pacman.getScore() + "     Lives: " + pacman.getLives());
        System.out.println("Dots Left: " + Maze.getDotsRemaining());
        System.out.println("====================");
        System.out.println();

        char[][] grid = maze.getGrid();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (pacman.getX() == col && pacman.getY() == row) {
                    System.out.print("P");
                } else {
                    boolean ghostDrawn = false;
                    for (Ghost g : ghosts) {
                        if (g != null && g.getX() == col && g.getY() == row) {
                            System.out.print("G");
                            ghostDrawn = true;
                            break;
                        }
                    }
                    if (!ghostDrawn) {
                        System.out.print(grid[row][col]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Controls: ^(Up)  v(Down)  <(Left)  >(Right)");
        System.out.print("Your move: ");
    }

    public void handleInput() {
        String input = sc.nextLine();

        int direction = -1;

        if (input.equalsIgnoreCase("^")) {
            direction = 0;
        } else if (input.equalsIgnoreCase("v")) {
            direction = 1;
        } else if (input.equalsIgnoreCase(">")) {
            direction = 2;
        } else if (input.equalsIgnoreCase("<")) {
            direction = 3;
        } else {
            System.out.println("Invalid key!");
            return;
        }

        int oldX = pacman.getX();
        int oldY = pacman.getY();
        char[][] grid = maze.getGrid();
        Obstacle[] obstacles = maze.getObstacles();
        pacman.tryMove(direction, grid, obstacles);

        if (pacman.getX() != oldX || pacman.getY() != oldY) {
            if (maze.hasFood(pacman.getX(), pacman.getY())) {
                maze.eatFoodAt(pacman.getX(), pacman.getY());
                pacman.addScore();
            }
        }
    }

    public void moveGhosts() {
        char[][] grid = maze.getGrid();
        Obstacle[] obstacles = maze.getObstacles();

        for (Ghost ghost : ghosts) {
            if (ghost != null) {
                ghost.moveRandom(grid, obstacles);
            }
        }
    }

    public void checkCollisions() {
        for (Ghost ghost : ghosts) {
            if (ghost != null && ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()) {
                pacman.setLives(pacman.getLives() - 1);
                System.out.println("\n " + ghost.getName() + " caught you!");
                System.out.println("Lives remaining: " + pacman.getLives());

                if (pacman.isAlive()) {
                    respawnCharacters();
                }
                break;
            }
        }
    }

    public void respawnCharacters() {
        pacman.respawn(2, 4);
        if (ghosts[0] != null) ghosts[0].setPosition(10, 5);
        if (ghosts[1] != null) ghosts[1].setPosition(12, 6);
        if (ghosts[2] != null) ghosts[2].setPosition(8, 7);
        System.out.println("Respawning... Get ready!");
    }

    public void run() {
        System.out.println("====================");
        System.out.println("    PacMan Game     ");
        System.out.println("====================");
        while (isRunning) {
            display();
            handleInput();
            moveGhosts();
            checkCollisions();

            if (Maze.getDotsRemaining() == 0) {
                System.out.println("====================");
                System.out.println("     YOU WIN!  ");
                System.out.println("Final Score: " + pacman.getScore());
                isRunning = false;
            }

            if (!pacman.isAlive()) {
                System.out.println("====================");
                System.out.println("    GAME OVER ");
                System.out.println("Final Score: " + pacman.getScore());
                isRunning = false;
            }
        }
        sc.close();
    }
}
