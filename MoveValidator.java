public class MoveValidator {
	private ChessGame chessGame;

	public MoveValidator(ChessGame chessGame){
		this.chessGame = chessGame;
	}

	public boolean isMoveValid(Move move){
		// System.out.println("Move source: " + move.sourceRow + ", " + move.sourceCol);
		// System.out.println("Move target: " + move.targetRow + ", " + move.targetRow);
		// System.out.println(chessGame.getNonCapturedPieceOnPosition(move.sourceRow, move.sourceCol));
		// System.out.println(chessGame.getNonCapturedPieceOnPosition(move.targetRow, move.targetCol));
		Piece srcPiece = chessGame.getNonCapturedPieceOnPosition(move.sourceRow, move.sourceCol);
		Piece tarPiece = chessGame.getNonCapturedPieceOnPosition(move.targetRow, move.targetCol);
		int opponentColour = (chessGame.getGameState() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE : Piece.COLOR_BLACK);

		if (srcPiece == null){
			return false;
		}

		if (srcPiece.getColour() != chessGame.getGameState()){
			return false;
		}

		if (move.targetRow > Piece.ROW_8 && move.targetRow < Piece.ROW_1 && move.targetCol > Piece.COLUMN_H && move.targetCol < Piece.COLUMN_A){
			return false;
		}

		boolean isPieceMoveValid = false;

		switch(srcPiece.getType()){
			case Piece.TYPE_BISHOP:
				isPieceMoveValid = isMoveValidBishop(move);
				break;
			case Piece.TYPE_KNIGHT:
				isPieceMoveValid = isMoveValidKnight(move);
				break;
			case Piece.TYPE_KING:
				isPieceMoveValid = isMoveValidKing(move);
				break;
			case Piece.TYPE_PAWN:
				if(srcPiece.getColour() == Piece.COLOR_WHITE){
					isPieceMoveValid = isMoveValidPawnWhite(move);
				} else {
					isPieceMoveValid = isMoveValidPawnBlack(move);
				}
				break;
			case Piece.TYPE_QUEEN:
				isPieceMoveValid = isMoveValidQueen(move);
				break;
			case Piece.TYPE_ROOK:
				isPieceMoveValid = isMoveValidRook(move);
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

	private boolean isMoveValidPawnWhite(Move move) {
		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);
		
		//capturing with a pawn
		if (rowDifference == 1 && colDifference == 1){
			//move down left or down right if there is a capturable piece there
			return isTargetLocationCapturable(move.targetRow, move.targetCol);
		}

		if (move.sourceRow == Piece.ROW_2){
			if (rowDifference == 1 && colDifference == 0){
				return isTargetLocationFree(move.targetRow, move.targetCol);
			} else if (rowDifference == 2 && colDifference == 0){
				return isTargetLocationFree(move.targetRow, move.targetCol) && isTargetLocationFree(move.targetRow-1 , move.targetCol);
			}
		} else {
			return rowDifference == 1 && colDifference == 0 && isTargetLocationFree(move.targetRow, move.targetCol);
		}
		return false;
	}

	private boolean isMoveValidPawnBlack(Move move) {
		//calculates the difference between the rows and cols to validate movement.
		int rowDifference = move.targetRow - move.sourceRow;
		int colDifference = Math.abs(move.targetCol - move.sourceCol);

		//capturing with a pawn
		if (rowDifference == -1 &&  colDifference == 1){
			//move down left or down right if there is a capturable piece there
			return isTargetLocationCapturable(move.targetRow, move.targetCol);
		}

		//movement forward
		if (move.sourceRow == Piece.ROW_7){
			// if piece at start, it can move 1 step or 2 steps.
			if (rowDifference == -1 && colDifference == 0){
				return isTargetLocationFree(move.targetRow, move.targetCol);
			} else if (rowDifference == -2 && colDifference == 0){
				return isTargetLocationFree(move.targetRow, move.targetCol) && isTargetLocationFree(move.targetRow+1 , move.targetCol);
			}
		} else {
			return rowDifference == -1 && colDifference == 0 &&  isTargetLocationFree(move.targetRow, move.targetCol);
		}
		return false;
	}

	private boolean isMoveValidRook(Move move) {
		if(!isTargetLocationFree(move.targetRow, move.targetCol) && !isTargetLocationCapturable(move.targetRow, move.targetCol)){
			return false;
		}
		//return (!isTargetLocationFree(targetRow, targetCol) && !isTargetLocationFree(targetRow, targetCol));

		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);

		if (rowDifference != 0 && colDifference != 0) {
			// Rooks can only move along rows or columns, not diagonally
			return false;
		}
		
		int start, end;
		//check for pieces from its location to target location
		// if there are any pieces in its path, invalid move.
		if (rowDifference != 0){
			start = move.sourceRow < move.targetRow ? move.sourceRow + 1 : move.targetRow;
			end = move.sourceRow > move.targetRow ? move.sourceRow : move.targetRow;
			System.out.println("Start is " + start + " and End is: " + end);
        
			for (int row = start; row < end; row ++) {
				if (!isTargetLocationFree(row, move.targetCol)){
					return false;
				}
			}
		} else if (colDifference != 0){
			start = move.sourceCol < move.targetCol ? move.sourceCol : move.targetCol;
			end = move.sourceCol > move.targetCol ? move.sourceCol : move.targetCol;

			for (int col = start; col < end; col++) {
				if (!isTargetLocationFree(move.targetRow, col)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isMoveValidKnight(Move move) {
		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);

		if(!isTargetLocationCapturable(move.targetRow, move.targetCol) && !isTargetLocationFree(move.targetRow, move.targetCol)){
			return false;
		}

		if ((rowDifference == 2) && (colDifference == 1)){
			//up down movement (2 up/down and 1 left/right)
			return true;
		} else if ((colDifference == 2) && (rowDifference == 1)){
			return true;
		}
		return false;
	}

	private boolean isMoveValidBishop(Move move) {
		if(!isTargetLocationFree(move.targetRow, move.targetCol) && !isTargetLocationCapturable(move.targetRow, move.targetCol)){
			return false;
		}
		
		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);

		if(rowDifference == colDifference){
			//checks a condition and assign a positive/negative value to the stepping variable.
			int stepRow = (move.targetRow > move.sourceRow) ? 1 : -1;
      int stepCol = (move.targetCol > move.sourceCol) ? 1 : -1;

			for (int step = 1; step < rowDifference; step++){
				//calculating which row and col to check
				int checkRow = move.sourceRow + step * stepRow;
				int checkCol = move.sourceCol + step * stepCol;
				//System.out.println( checkRow + ", " + checkCol);
				if (!isTargetLocationFree(checkRow, checkCol)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private boolean isMoveValidQueen(Move move) {
		if (!isTargetLocationFree(move.targetRow, move.targetCol) && !isTargetLocationCapturable(move.targetRow, move.targetCol)){
			return false;
		}

		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);
		int start, end;

		// diagonal path checks
		if(rowDifference == colDifference){
			//checks a condition and assign a positive/negative value to the stepping variable.
			int stepRow = (move.targetRow > move.sourceRow) ? 1 : -1;
      int stepCol = (move.targetCol > move.sourceCol) ? 1 : -1;

			for (int step = 1; step < rowDifference; step++){
				//calculating which row and col to check
				int checkRow = move.sourceRow + step * stepRow;
				int checkCol = move.sourceCol + step * stepCol;
				//System.out.println( checkRow + ", " + checkCol);
				if (!isTargetLocationFree(checkRow, checkCol)) {
					return false;
				}
			}
			return true;
		} 
		
		//checking straight paths from source to target
		if (rowDifference != 0){
			start = move.sourceRow < move.targetRow ? move.sourceRow + 1 : move.targetRow;
			end = move.sourceRow > move.targetRow ? move.sourceRow : move.targetRow;
			System.out.println("Start is " + start + " and End is: " + end);
        
			for (int row = start; row < end; row ++) {
				if (!isTargetLocationFree(row, move.targetCol)){
					return false;
				}
			}
			return true;
		} else if (colDifference != 0){
			start = move.sourceCol < move.targetCol ? move.sourceCol : move.targetCol;
			end = move.sourceCol > move.targetCol ? move.sourceCol : move.targetCol;

			for (int col = start; col < end; col++) {
				if (!isTargetLocationFree(move.targetRow, col)) {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	private boolean isMoveValidKing(Move move) {
		if (!isTargetLocationFree(move.targetRow, move.targetCol) && !isTargetLocationCapturable(move.targetRow, move.targetCol)){
			return false;
		}

		int rowDifference = Math.abs(move.targetRow - move.sourceRow);
		int colDifference = Math.abs(move.targetCol - move.sourceCol);

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
