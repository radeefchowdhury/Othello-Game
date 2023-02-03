public abstract class Position {
    private char piece;
    public static final char EMPTY ='_';
    public static final char  BLACK ='B';
    public static final char WHITE ='W';

    public abstract boolean canPlay();

    public char getPiece() {return piece;}

    public void setPiece(char piece) {this.piece = piece;}

    @Override
    public String toString(){
//        if(piece != BLACK && piece != WHITE)
//            return ""+UnplayablePosition.UNPLAYABLE;
        return ""+piece;
    }

}
