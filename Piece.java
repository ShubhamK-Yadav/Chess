/**
 * Represents a chess piece.
 */
public class Piece{
    private int colour;
    public static final int COLOR_WHITE = 0;
    public static final int COLOR_BLACK = 1;

    private int type;
    public static final int TYPE_ROOK = 1;
    public static final int TYPE_KNIGHT = 2;
    public static final int TYPE_BISHOP = 3;
    public static final int TYPE_QUEEN = 4;
    public static final int TYPE_KING = 5;
    public static final int TYPE_PAWN = 6;

    //Chess is played on a square board of
    //eight rows (denoted with numbers 1 to 8 )
    //and eight columns (denoted with letters a to h)
    private int row;
    public static final int ROW_1 = 0;
    public static final int ROW_2 = 1;
    public static final int ROW_3 = 2;
    public static final int ROW_4 = 3;
    public static final int ROW_5 = 4;
    public static final int ROW_6 = 5;
    public static final int ROW_7 = 6;
    public static final int ROW_8 = 7;
 
    private int column;
    public static final int COLUMN_A = 0;
    public static final int COLUMN_B = 1;
    public static final int COLUMN_C = 2;
    public static final int COLUMN_D = 3;
    public static final int COLUMN_E = 4;
    public static final int COLUMN_F = 5;
    public static final int COLUMN_G = 6;
    public static final int COLUMN_H = 7;
 
    private boolean isCaptured = false;

    public Piece(int row, int column, int color, int type){
        this.row = row;
        this.column = column;
        colour = color;
        this.type = type;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece ('B' for black, 'W' for white)
     */
    public int getColour() {
        return colour;
    }

    public int getType(){
        return type;
    }
    /**
     * Checks if the piece is killed.
     *
     * @return true if the piece is killed, false otherwise
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Sets the killed status of the piece.
     *
     * @param status the killed status of the piece
     */
    public void setCaptured(boolean status) {
        this.isCaptured = status;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setColumn(int column){
        this.column = column;
    }
}