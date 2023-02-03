import java.util.Scanner;
import java.io.*;

public class Board {

    private  Position[][] gameboard;
    private  Player first;
    private  Player second;
    private  Player current;

    static Scanner scan = new Scanner(System.in);

    public Player getCurrent() {
        return current;
    }

    public Player getFirst() {
        return first;
    }

    public Player getSecond() {
        return second;
    }

    public void drawBoard(){
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println();
            for (int j = 0; j < 8; j++) {
                System.out.print(gameboard[i][j] + " ");
            }
        }
        System.out.println();
    }

    public static Board load(){
        System.out.print("Enter save file name: ");
        String fileName = scan.nextLine();

        return new Board(fileName);
    }

    public Board(Player p1, Player p2, int start){
        first = p1;
        second = p2;
        current = p1;

        gameboard = new Position[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position p = new UnplayablePosition();
                p.setPiece(UnplayablePosition.UNPLAYABLE);
                gameboard[i][j] = p;
            }
        }

        Position white = new UnplayablePosition();
        white.setPiece(Position.WHITE);
        Position black = new UnplayablePosition();
        black.setPiece(Position.BLACK);

            if (start == 0) {

                gameboard[2][2] = white;
                gameboard[2][3] = white;
                gameboard[2][4] = black;
                gameboard[2][5] = black;
                gameboard[3][2] = white;
                gameboard[3][3] = white;
                gameboard[3][4] = black;
                gameboard[3][5] = black;
                gameboard[4][2] = black;
                gameboard[4][3] = black;
                gameboard[4][4] = white;
                gameboard[4][5] = white;
                gameboard[5][2] = black;
                gameboard[5][3] = black;
                gameboard[5][4] = white;
                gameboard[5][5] = white;
            }
            else if (start == 1){
                gameboard[2][2] = white;
                gameboard[2][3] = black;
                gameboard[3][2] = black;
                gameboard[3][3] = white;
            }
            else if (start == 2){
                gameboard[2][4] = white;
                gameboard[2][5] = black;
                gameboard[3][4] = black;
                gameboard[3][5] = white;
            }
            else if (start == 3){
                gameboard[4][2] = white;
                gameboard[4][3] = black;
                gameboard[5][2] = black;
                gameboard[5][3] = white;
            }
            else if (start == 4){
                gameboard[4][4] = white;
                gameboard[4][5] = black;
                gameboard[5][4] = black;
                gameboard[5][5] = white;
            }
        }

    public Board(String save_file){
        gameboard = new Position[8][8];
        File saveFile = new File(save_file);
        try {
            Scanner fileScan = new Scanner(saveFile);
            for (int i = 0; i < 8; i++) {
                String row = fileScan.nextLine();
                for (int j = 0; j < 8; j++) {
                    char piece = row.charAt(j);
                    Position p = null;
                    if(piece == '*'){
                        p = new UnplayablePosition();
                        p.setPiece(UnplayablePosition.UNPLAYABLE);
                    }
                    if(piece == 'W'){
                        p = new UnplayablePosition();
                        p.setPiece(Position.WHITE);
                    }
                    if(piece == 'B'){
                        p = new UnplayablePosition();
                        p.setPiece(Position.BLACK);
                    }
                    gameboard[i][j] = p;
                }
            }
            first = new Player(fileScan.nextLine());
            second = new Player(fileScan.nextLine());
            String c = fileScan.nextLine();
            current = c.equals(first.toString()) ? first : second;

        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. Program will now exit.");
            System.exit(0);
        }
    }

    public void play(){
        boolean running = true;
        while(running) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (isCurrent(i, j)) {
                        //N
                        if (i != 0 && isOpposite(i - 1, j)) {
                            checkFlank(i, j, "N");
                        }
                        //NE
                        if (i != 0 && j != 7 && isOpposite(i - 1, j + 1)) {
                            checkFlank(i, j, "NE");
                        }
                        //E
                        if (j != 7 && isOpposite(i, j + 1)) {
                            checkFlank(i, j, "E");
                        }
                        //SE
                        if (i != 7 && j != 7 && isOpposite(i + 1, j + 1)) {
                            checkFlank(i, j, "SE");
                        }
                        //S
                        if (i != 7 && isOpposite(i + 1, j)) {
                            checkFlank(i, j, "S");
                        }
                        //SW
                        if (i != 7 && j != 0 && isOpposite(i + 1, j - 1)) {
                            checkFlank(i, j, "SW");
                        }
                        //W
                        if (j != 0 && isOpposite(i, j - 1)) {
                            checkFlank(i, j, "W");
                        }
                        //NW
                        if (i != 0 && j != 0 && isOpposite(i - 1, j - 1)) {
                            checkFlank(i, j, "NW");
                        }
                    }
                }
            }

            drawBoard();

            String color = "Black";
            if(current == second) color = "White";

            System.out.println("\n"+color+"'s turn: ");
            System.out.println("""
                Please choose one of the following options: 
                1. Make a move
                2. Concede
                3. Save""");


            switch (scan.nextInt()){

                case 1:
                    boolean run = true;

                    while (run) {
                    System.out.println(getCurrent() + " please make a move by typing the row [0-7] and column [0-7] seperated by a space.");

                    int row = scan.nextInt();
                    int col = scan.nextInt();

                        if (gameboard[row][col].getPiece() == Position.EMPTY) {

                            if (current == first)
                                gameboard[row][col].setPiece(Position.BLACK);

                            else if (current == second)
                                gameboard[row][col].setPiece(Position.WHITE);
                            checkDirection(row, col);
                            isOver();
                            takeTurn();
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (gameboard[i][j].getPiece() == Position.EMPTY)
                                        gameboard[i][j].setPiece(UnplayablePosition.UNPLAYABLE);
                                }
                            }
                            run = false;
                        } else
                            System.out.println("Please enter a valid move.");
                    }

                    break;
                case 2:
                    if(color == "Black") color = "White";
                    System.out.println(getCurrent() + " has conceded. " + color + " wins.");
                    running = false;
                    takeTurn();
                    break;
                case 3:
                    save();
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid value");


            }
        }
    }

    private boolean flipCoins(int i, int j, int steps, String direction){
        steps++;
        int a = 0;
        int b = 0;
        if(direction.contains("N"))
            a = -1;
        if(direction.contains("S"))
            a = 1;
        if(direction.contains("E"))
            b = 1;
        if(direction.contains("W"))
            b = -1;
        i += a;
        j += b;
        if(i < 0 || i > 7 || j < 0 || j > 7) return false;
        if(isOpposite(i,j)){
            return flipCoins(i, j, steps, direction);
        }
        if(isCurrent(i,j)){
            for(int step = 0; step < steps; step++){
                i -= a;
                j -= b;
                UnplayablePosition p = new UnplayablePosition();
                if(current == first) p.setPiece(Position.BLACK);
                else p.setPiece(Position.WHITE);
                gameboard[i][j] = p;
            }
            return true;
        }
        return false;
    }

    private void checkDirection(int i, int j){
        //N
        if (i != 0 && isOpposite(i - 1, j))
            flipCoins(i, j, 0, "N");
        //NE
        if (i != 0 && j != 7 && isOpposite(i - 1, j + 1))
            flipCoins(i, j, 0, "NE");
        //E
        if (j != 7 && isOpposite(i, j + 1))
            flipCoins(i, j, 0, "E");
        //SE
        if (i != 7 && j != 7 && isOpposite(i + 1, j + 1))
            flipCoins(i, j, 0, "SE");
        //S
        if (i != 7 && isOpposite(i + 1, j))
            flipCoins(i, j, 0, "S");
        //SW
        if (i != 7 && j != 0 && isOpposite(i + 1, j - 1))
            flipCoins(i, j, 0, "SW");
        //W
        if (j != 0 && isOpposite(i, j - 1))
            flipCoins(i, j, 0, "W");
        //NW
        if (i != 0 && j != 0 && isOpposite(i - 1, j - 1))
            flipCoins(i, j, 0, "NW");
    }

    public boolean checkFlank(int i, int j, String direction){
        int a = 0;
        int b = 0;
        if(direction.contains("N"))
            a = -1;
        if(direction.contains("S"))
            a = 1;
        if(direction.contains("E"))
            b = 1;
        if(direction.contains("W"))
            b = -1;
        i += a;
        j += b;
        if(i < 0 || i > 7 || j < 0 || j > 7) return false;
        if(isOpposite(i,j)){
            return checkFlank(i, j, direction);
        }
        if(!isOpposite(i,j) && !isCurrent(i,j)){
            PlayablePosition p = new PlayablePosition();
            p.setPiece(Position.EMPTY);
            gameboard[i][j] = p;
            return true;
        }
        return false;
    }

    public boolean isOpposite(int i, int j){
        return current == first ? gameboard[i][j].getPiece() == Position.WHITE : gameboard[i][j].getPiece() == Position.BLACK;
    }

    public boolean isCurrent(int i, int j){
        return current == first ? gameboard[i][j].getPiece() == Position.BLACK : gameboard[i][j].getPiece() == Position.WHITE;
    }

    public boolean isWhite(int i, int j){
        return gameboard[i][j].getPiece() == Position.WHITE;
    }

    public boolean isBlack(int i, int j) {
        return gameboard[i][j].getPiece() == Position.BLACK;
    }

    public boolean isUnplayable(int i, int j){
        return gameboard[i][j].getPiece() == UnplayablePosition.UNPLAYABLE;
    }

    public boolean isEmpty(int i, int j){
        return gameboard[i][j].getPiece() == Position.EMPTY;
    }

    private void save(){
        File saveFile = new File("save.txt");
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create a new save file. The program will now exit.");
            System.exit(0);
        }
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(saveFile));
            for (int i = 0; i < 8; i++) {
                String row = "";
                for (int j = 0; j < 8; j++) {
                    if(gameboard[i][j].getPiece() == '_')
                        row += '*';
                    else row += gameboard[i][j].getPiece();
                }
                pw.println(row);
            }
            pw.println(first);
            pw.println(second);
            pw.println(current);
            pw.flush();
            pw.close();
            System.out.println("Game saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Could not write in save file. The program will now exit.");
            System.exit(0);
        }


    }

    private void takeTurn(){

        if (current == first)
            current = second;
        else
            current = first;

        boolean stuck = false;
        int count = 0;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (gameboard[i][j].getPiece() == '_')
                        count++;
                }
            }
            if (count == 0)
                stuck = true;

        if (stuck) {
            System.out.println(getCurrent() + "'s turn has been forfeited.");

            if (current == first)
                current = second;
            else
                current = first;
        }
    }

    private void isOver() {

        boolean over = true;
        Player initial = new Player("First");

        initial = current;


        current = first;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    if (gameboard[i][j].getPiece() == '_')
                        over = false;
            }
        }
        current = second;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameboard[i][j].getPiece() == '_')
                    over = false;
            }
        }
        current = initial;

        if (over) {
            counter(first, second);
        }
    }

    private void counter(Player p1, Player p2){
        int whitePieces = 0;
        int blackPieces = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameboard[i][j].getPiece() == Position.WHITE)
                    whitePieces++;
                else if (gameboard[i][j].getPiece() == Position.BLACK)
                    blackPieces++;
            }
        }

        if(blackPieces>whitePieces)
            System.out.println(getFirst() + " has won the game!");
        else if(whitePieces>blackPieces)
            System.out.println(getSecond() + " has won the game!");
        else
        System.out.println("Tie!");
        System.out.println("Thank you for playing!");
        System.exit(0);
    }
}
