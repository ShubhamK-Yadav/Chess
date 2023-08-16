import java.util.ArrayList;
import java.util.List;

public class ChessGame {
	private List<Piece> pieces = new ArrayList<Piece>();

	private int gameState = GAME_STATE_WHITE;
	static final int GAME_STATE_WHITE = 0;
	static final int GAME_STATE_BLACK = 1;

    private MoveValidator moveValidator;

	public ChessGame(){
        populateInitialChessBoard();
        moveValidator = new MoveValidator(this);
	}

	/**
     * Populates the chessboard with pieces in the correct positions.
     */
    public void populateInitialChessBoard() {
        // white pieces
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_B);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_C);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_1, Piece.COLUMN_D);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_F);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_G);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);

        // White pawns
		int currentColumn = Piece.COLUMN_A;
        for (int i = 0; i < 8; i++) {
            createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, currentColumn);
			currentColumn++;
        }

        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_B);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_C);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, Piece.ROW_8, Piece.COLUMN_D);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_8, Piece.COLUMN_E);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_F);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_G);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);
 
        // pawns
        currentColumn = Piece.COLUMN_A;
        for (int i = 0; i < 8; i++) {
            createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, currentColumn);
            currentColumn++;
        }
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_3, Piece.COLUMN_A);
    }

    private void createAndAddPiece(int colour, int type, int row, int column) {
        Piece piece = new Piece(row, column, colour, type);
        this.pieces.add(piece);
		this.movePiece(piece, row, column);
    }

    // Two things to do: 
    //Check if there is a opponent piece on the tile, if there is, mark that piece as captured. (Find non-captured pieces)
	//Change the pieces, row and column
    public void movePiece(Piece piece, int row, int column){
        // if current colour is white then opponent is the other colour
        int opponentColour = (piece.getColour()==Piece.COLOR_BLACK ? Piece.COLOR_WHITE:Piece.COLOR_BLACK);
        if (isPieceNonCapturedOnPosition(opponentColour, row, column)){
            Piece opponentPiece = getNonCapturedPieceOnPosition(row, column);
            opponentPiece.setCaptured(true);
        }
        piece.setRow(row);
        piece.setColumn(column);
    }

    public void movePiece(int sourceRow, int sourceCol, int targetRow, int targetCol){
        if (!this.moveValidator.isMoveValid(sourceRow, sourceCol, targetRow, targetCol)){
            System.out.println("Invalid move!");
            return;
        }

        Piece piece = this.getNonCapturedPieceOnPosition(sourceRow, sourceCol);

        int opponentColour = (piece.getColour()==Piece.COLOR_BLACK ? Piece.COLOR_WHITE:Piece.COLOR_BLACK);
        if (this.isPieceNonCapturedOnPosition(opponentColour, targetRow, targetCol)){
            Piece oppPiece = getNonCapturedPieceOnPosition(targetRow, targetCol);
            oppPiece.setCaptured(true);
        }
        piece.setRow(targetRow);
        piece.setColumn(targetCol);
    }

    //iterate through the pieces and find the pieces that match the parameters and return those pieces.
    public Piece getNonCapturedPieceOnPosition(int row, int column){
        for (Piece piece : pieces){
            if(piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false){
                return piece;
            }
        }
        return null;
    }

    public boolean isPieceNonCapturedOnPosition(int colour, int row, int column){
        for (Piece piece : pieces){
            if (piece.getColour() == colour && piece.getRow() == row &&
                 piece.getColumn() == column && piece.isCaptured() == false){
                return true;
            }
        }
        return false;
    }
	/**
    * @return current game state
    */
    public int getGameState() {
        return this.gameState;
    }


	public void changeGameState() {
        switch (this.gameState){
            case GAME_STATE_BLACK:
                this.gameState = GAME_STATE_WHITE;
                break;
            case GAME_STATE_WHITE:
                this.gameState = GAME_STATE_BLACK;
                break;
            default:
                throw new IllegalStateException("Unknown game state: " + this.gameState);
        }
        System.out.println(this.gameState);
    }

    public List<Piece> getPieces(){
        return pieces;
    }
}