import java.awt.Image;

/**
 * Represents a chess piece.
 */
public abstract class Pieces {
    private char colour;
    private boolean killed;
    private Image piece;
    private int id;
    private int[] possiblePos;

    /**
     * Constructs a Pieces object with the specified color, ID, killed status, and piece image.
     *
     * @param colour the color of the piece ('B' for black, 'W' for white)
     * @param id     the ID of the piece
     * @param killed the killed status of the piece
     * @param piece  the image representing the piece
     */
    public Pieces(char colour, int id, boolean killed, Image piece) {
        this.colour = colour;
        this.id = id;
        this.killed = false;
        this.piece = piece;
    }

    /**
     * Finds and returns the possible moves for the piece.
     *
     * @return an array of possible moves
     */
    public abstract int[] findPossibleMoves();

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece ('B' for black, 'W' for white)
     */
    public char getColour() {
        return colour;
    }

    /**
     * Returns the ID of the piece.
     *
     * @return the ID of the piece
     */
    public int getId() {
        return id;
    }

    /**
     * Checks if the piece is killed.
     *
     * @return true if the piece is killed, false otherwise
     */
    public boolean isKilled() {
        return killed;
    }

    /**
     * Sets the killed status of the piece.
     *
     * @param status the killed status of the piece
     */
    public void setKilled(boolean status) {
        this.killed = status;
    }

    /**
     * Returns the image representing the piece.
     *
     * @return the image representing the piece
     */
    public Image getImage() {
        return piece;
    }

    /**
     * Sets the image representing the piece.
     *
     * @param img the image representing the piece
     */
    public void setImage(Image img) {
        piece = img;
    }
}