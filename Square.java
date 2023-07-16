/**
 * Represents a square on the chessboard.
 */
public class Square {
    private Pieces piece;
    private int x, y;
    private boolean occupied;

    /**
     * Constructs a Square object with the specified coordinates, piece, and occupancy status.
     * @param coordX   the x-coordinate of the square
     * @param coordY   the y-coordinate of the square
     * @param piece    the piece occupying the square
     * @param occupied the occupancy status of the square
     */
    public Square(int coordX, int coordY, Pieces piece, boolean occupied) {
        x = coordX;
        y = coordY;
        this.piece = piece;
        this.occupied = occupied;
    }

    /**
     * Sets the piece occupying the square.
     * @param pieces the piece to set on the square
     * @return the previous piece occupying the square, or null if there was none
     */
    public Pieces setPiece(Pieces pieces) {
        return this.piece = pieces;
    }

    /**
     * Returns the coordinates of the square.
     * @return an array containing the x-coordinate at index 0 and the y-coordinate at index 1
     */
    public int[] getCoords() {
        return new int[]{x, y};
    }

    /**
     * Returns the piece occupying the square.
     * @return the piece occupying the square, or null if there is none
     */
    public Pieces getPiece() {
        return piece;
    }

    /**
     * Checks if the square is occupied by a piece.
     * @return true if the square is occupied, false otherwise
     */
    public boolean isOccupied() {
        return occupied;
    }
}

