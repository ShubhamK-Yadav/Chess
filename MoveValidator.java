public class MoveValidator {
	private ChessGame chessGame;

	public MoveValidator(ChessGame chessGame){
		this.chessGame = chessGame;
	}

	public boolean isMoveValid(int sourceRow, int sourceCol, int targetRow, int targetCol){
		Piece srcPiece = chessGame.getNonCapturedPieceOnPosition(sourceRow, sourceCol);
		Piece tarPiece = chessGame.getNonCapturedPieceOnPosition(targetRow, targetCol);
		int opponentColour = (chessGame.getGameState() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE : Piece.COLOR_BLACK);

		if (srcPiece == null){
			return false;
		}

		if (srcPiece.getColour() != chessGame.getGameState()){
			return false;
		}

		if (targetRow > Piece.ROW_8 && targetRow < Piece.ROW_1 && targetCol > Piece.COLUMN_H && targetCol < Piece.COLUMN_A){
			return false;
		}

		boolean isPieceMoveValid = false;

		switch(srcPiece.getType()){
			case Piece.TYPE_BISHOP:
				isPieceMoveValid = isMoveValidBishop(sourceRow, sourceCol, targetRow, targetCol);
				break;
			case Piece.TYPE_KNIGHT:
				isPieceMoveValid = isMoveValidKnight(sourceRow, sourceCol, targetRow, targetCol);
				break;
			case Piece.TYPE_KING:
				isPieceMoveValid = isMoveValidKing(sourceRow, sourceCol, targetRow, targetCol);
				break;
			case Piece.TYPE_PAWN:
				if(srcPiece.getColour() == Piece.COLOR_WHITE){
					isPieceMoveValid = isMoveValidPawnWhite(sourceRow, sourceCol, targetRow, targetCol);
				} else {
					isPieceMoveValid = isMoveValidPawnBlack(sourceRow, sourceCol, targetRow, targetCol);
				}
				break;
			case Piece.TYPE_QUEEN:
				isPieceMoveValid = isMoveValidQueen(sourceRow, sourceCol, targetRow, targetCol);
				break;
			case Piece.TYPE_ROOK:
				isPieceMoveValid = isMoveValidRook(sourceRow, sourceCol, targetRow, targetCol);
				break;
			default: 
				break;
		}

		if (!isPieceMoveValid){
			return false;
		}

		if(tarPiece != null && !tarPiece.isCaptured() && tarPiece.getColour() == opponentColour){
			tarPiece.setCaptured(true);
		}
		return true;
	}

	private boolean isMoveValidPawnWhite(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		int rowDifference = targetRow - sourceRow;
		int colDifference = targetCol - sourceCol;
		
		//capturing with a pawn
		if (rowDifference == 1 && (colDifference == -1 || colDifference == 1)){
			//move down left or down right if there is a capturable piece there
			return isTargetLocationCapturable(targetRow, targetCol);
		}

		if (sourceRow == Piece.ROW_2){
			if (rowDifference == 1){
				return isTargetLocationFree(targetRow, targetCol);
			} else if (rowDifference == 2){
				return isTargetLocationFree(targetRow, targetCol) && isTargetLocationFree(targetRow-1 , targetCol);
			}
		} else {
			return rowDifference == 1 && isTargetLocationFree(targetRow, targetCol);
		}
		return false;
	}

	private boolean isMoveValidPawnBlack(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		//calculates the difference between the rows and cols to validate movement.
		int rowDifference = targetRow - sourceRow;
		int colDifference = targetCol - sourceCol;

		//capturing with a pawn
		if (rowDifference == -1 && (colDifference == -1 || colDifference == 1)){
			//move down left or down right if there is a capturable piece there
			return isTargetLocationCapturable(targetRow, targetCol);
		}

		//movement forward
		if (sourceRow == Piece.ROW_7){
			// if piece at start, it can move 1 step or 2 steps.
			if (rowDifference == -1){
				return isTargetLocationFree(targetRow, targetCol);
			} else if (rowDifference == -2){
				return isTargetLocationFree(targetRow, targetCol) && isTargetLocationFree(targetRow+1 , targetCol);
			}
		} else {
			return rowDifference == -1 && isTargetLocationFree(targetRow, targetCol);
		}
		return false;
	}

	private boolean isMoveValidRook(int sourceRow, int sourceCol, int targetRow, int targetCol) {

		int rowDifference = Math.abs(targetRow - sourceRow);
		int colDifference = Math.abs(targetCol - sourceCol);

		if (rowDifference != 0 && colDifference != 0) {
			// Rooks can only move along rows or columns, not diagonally
			return false;
		}
		

		int start, end, step;
		//check for pieces from its location to target location
		// if there are any pieces in its path, invalid move.
		if (rowDifference != 0){
			start = Math.min(sourceRow, targetRow);
			end = Math.max(sourceRow, targetRow);
			step = 1;
        
			for (int row = start; row < end; row += step) {
				if (!isTargetLocationFree(row, targetCol) && !isTargetLocationCapturable(row, targetCol)) {
					return false;
				}
			}	
		} else if (colDifference != 0){
			start = Math.min(sourceCol, targetCol) + 1;
			end = Math.max(sourceCol, targetCol);
			step = sourceCol < targetCol ? 1 : -1;
        
			for (int col = start; col < end; col += step) {
				if (!isTargetLocationFree(targetRow, col) && !isTargetLocationCapturable(targetRow, col)) {
					return false;
				}
			}		
		}
		return false;
	}

	private boolean isMoveValidKnight(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		int rowDifference = targetRow - sourceRow;
		int colDifference = targetCol - sourceCol;

		if(!isTargetLocationCapturable(targetRow, targetCol) && !isTargetLocationFree(targetRow, targetCol)){
			return false;
		}

		if ((rowDifference == 2 || rowDifference == -2) && (colDifference == 1 || colDifference == -1)){
			//up down movement (2 up/down and 1 left/right)
			return true;
		} else if ((colDifference == 2 || colDifference == -2) && (rowDifference == 1 || rowDifference == -1)){
			return true;
		}
		return false;
	}

	private boolean isMoveValidBishop(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidQueen(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidKing(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		if (!isTargetLocationFree(targetRow, targetCol) && !isTargetLocationCapturable(targetRow, targetCol)){
			System.out.println("The target location is not free");
			System.out.println("OR target location is not capturable");
			return false;
		} else {
			System.out.println("The location is free or capturable");
		}

		int rowDifference = Math.abs(targetRow - sourceRow);
		int colDifference = Math.abs(targetCol - sourceCol);

		//no movement
		if (rowDifference == 0 && colDifference == 0){
			return false;
		}

		//movement in a direction
		if ((rowDifference > 1 && rowDifference < -1) || (colDifference > 1 && colDifference < -1)){
			return false;
		} else if ( rowDifference == colDifference && rowDifference == 1 && colDifference == 1){
			//diagnal movement
			return true;
		}
		return false;
	}

	private boolean isTargetLocationFree(int targetRow, int targetCol){
		for (Piece piece : chessGame.getPieces()){
			if(piece.getRow() == targetRow && piece.getColumn() == targetCol){
				return false;
			}
		}
		return true;
	}

	private boolean isTargetLocationCapturable(int targetRow, int targetCol){
		int gameState = chessGame.getGameState();
		int opponentColour = (gameState == Piece.COLOR_BLACK ? Piece.COLOR_WHITE : Piece.COLOR_BLACK);
		for (Piece piece : chessGame.getPieces()){
			if(piece.getRow() == targetRow && piece.getColumn() == targetCol){
				if (chessGame.isPieceNonCapturedOnPosition(opponentColour, targetRow, targetCol)){
					return true;
				}
			}
		}
		return false;
	}
}
