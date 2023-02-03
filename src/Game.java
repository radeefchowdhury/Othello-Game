import java.util.Scanner;
public class Game {
    private Player play1;
    private Player play2;

    public Player getPlay1() {
        return play1;
    }
    public Player getPlay2() {
        return play2;
    }

    public Game(Player p1, Player p2) {
        play1 = p1;
        play2 = p2;
    }

    public void start() {
                Scanner scan = new Scanner(System.in);

                System.out.println("Choose a board to start: ");

                Player p1 = new Player("Mike");
                Player p2 = new Player("Peter");

                for (int i = 0; i < 5; i++) {

                    System.out.print("Option " );
                    System.out.print(i+1);
                    System.out.println(": ");
                    Board num2 = new Board(p1, p2, i);
                    num2.drawBoard();
                    System.out.println("""
                                    
                                    """);
                }
        }

    public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);

        System.out.println("""
                1. Quit
                2. Load a Game
                3. Start a New Game""");

        int answer = 0;

        if (scan.hasNextInt())
            answer = scan.nextInt();
        else
            System.out.println("Please enter a valid value.");



        switch (answer) {
            case 1:
                System.out.println("Thank you for playing Othello!");
                System.exit(1);
                break;
            case 2:
                Board b = Board.load();
                b.play();
                break;
            case 3:
                System.out.println("Player 1 name: ");
                Player p1 = new Player(scan.next());
                System.out.println("Player 2 name: ");
                Player p2 = new Player(scan.next());
                Game othello = new Game(p1, p2);
                othello.start();

                int version = scan.nextInt() - 1;

                Board num1 = new Board(p1, p2, version);

                System.out.println(p1 + " will be the Blacks and " + p2 + " will be the Whites. ");

                num1.play();


                break;

            default:
                System.out.println("Please enter a valid value.");

        }

    }
}