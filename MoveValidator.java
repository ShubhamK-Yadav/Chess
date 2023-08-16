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
				isPieceMoveValid = isMoveValidBishop(sourceRow, sourceRow, targetRow, targetCol);
				break;
			case Piece.TYPE_KNIGHT:
				isPieceMoveValid = isMoveValidKnight(sourceRow, sourceRow, targetRow, targetCol);
				break;
			case Piece.TYPE_KING:
				isPieceMoveValid = isMoveValidKing(sourceRow, sourceRow, targetRow, targetCol);
				break;
			case Piece.TYPE_PAWN:
				if(srcPiece.getColour() == Piece.COLOR_WHITE){
					isPieceMoveValid = isMoveValidPawnWhite(sourceRow, sourceCol, targetRow, targetCol);
				} else {
					isPieceMoveValid = isMoveValidPawnBlack(sourceRow, sourceRow, targetRow, targetCol);
				}
				break;
			case Piece.TYPE_QUEEN:
				isPieceMoveValid = isMoveValidQueen(sourceRow, sourceRow, targetRow, targetCol);
				break;
			case Piece.TYPE_ROOK:
				isPieceMoveValid = isMoveValidRook(sourceRow, sourceRow, targetRow, targetCol);
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

	

	private boolean isMoveValidRook(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidQueen(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
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
		if (rowDifference <= 1 && colDifference <= 1){
			return true;
		} else if ( rowDifference == colDifference){
			//diagnal movement
			return true;
		}
		return false;
	}

	private boolean isMoveValidKnight(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidBishop(int sourceRow, int sourceCol, int targetRow, int targetCol) {
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
