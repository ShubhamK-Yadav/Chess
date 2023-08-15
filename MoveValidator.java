public class MoveValidator {
	private ChessGame chessGame;

	public MoveValidator(ChessGame chessGame){
		this.chessGame = chessGame;
	}

	public boolean isMoveValid(int sourceRow, int sourceCol, int targetRow, int targetCol){
		Piece srcPiece = chessGame.getNonCapturedPieceOnPosition(sourceRow, sourceCol);
		Piece tarPiece = chessGame.getNonCapturedPieceOnPosition(targetRow, targetCol);

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
				isPieceMoveValid = isMoveValidPawn(sourceRow, sourceRow, targetRow, targetCol);
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
		return true;
	}

	private boolean isMoveValidRook(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidQueen(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidPawn(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		return false;
	}

	private boolean isMoveValidKing(int sourceRow, int sourceCol, int targetRow, int targetCol) {
		if (!isTargetLocationFree(targetRow, targetCol) && !isTargetLocationCapturable(targetRow, targetCol)){
			System.out.println("The target location is not free");
			System.out.println("OR target location is not capturable");
			return false;
		} else {
			System.out.println("The location is free and capturable");
		}

		if (sourceCol+1 == targetCol && sourceRow + 1 == targetRow){
			//move up and right
			return true;
		} else if (sourceCol - 1 == targetCol && sourceRow + 1 == targetRow){
			//move up and left
			return true;
		} else if (sourceCol - 1 == targetCol && sourceRow - 1 == targetRow){
			//move down and left
			return true;
		} else if (sourceCol + 1 == targetCol && sourceRow - 1 == targetRow){
			//move down and right
			return true;
		} else if (sourceRow + 1 == targetRow && sourceCol == targetCol){
			//move up
			return true;
		} else if (sourceRow == targetRow && sourceCol + 1 == targetCol){
			//move right
			return true;
		} else if (sourceRow - 1 == targetRow && sourceCol == targetCol){
			//move down
			return true;
		} else if (sourceRow == targetRow && sourceCol -1 == targetCol){
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
