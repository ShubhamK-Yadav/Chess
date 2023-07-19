import java.awt.Image;

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

    private int row;
    private boolean isCaptured = false;
    private Image img;
    private int x, y;
    private int width = 64, height = 64;
    // private int[] possiblePos;

    public Piece(Image img, int x, int y, int color, int type){
        this.img = img;
        this.x = x;
        this.y = y;
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

    /**
     * Returns the image representing the piece.
     *
     * @return the image representing the piece
     */
    public Image getImage() {
        return img;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int coordX){
        this.x = coordX;
    }

    public void setY(int coordY){
        this.y = coordY;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }
}