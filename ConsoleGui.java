import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleGui {
	private ChessGame chessGame;

	public ConsoleGui() {
		this.chessGame = new ChessGame();
	}

	public void run() {
		String input = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		this.printCurrentGameState();
		System.out.println("Please enter a valid move. e.g: e2-e5");
		while (true) {
			try {
				input = reader.readLine();
				input = input.replaceAll("\s+", "").toLowerCase();
				if (input.equalsIgnoreCase("exit")) {
					return;
				} else {
					this.handleMove(input);
					this.chessGame.changeGameState();
				}
			} catch (Exception e) {
				System.out.println(e.getClass() + ": " + e.getMessage());
			}
		}
	}

	public void handleMove(String input){
		String srcColString = input.substring(0, 1);
		String srcRowString = input.substring(1, 2);
		String targetColString = input.substring(3, 4);
		String targetRowString = input.substring(4, 5);

		int srcCol = 0;
		int srcRow = 0;
		int tarCol = 0;
		int tarRow = 0;

		if (!validColumnChecker(srcColString.charAt(0)) || !validColumnChecker(targetColString.charAt(0))){
			System.out.println("Invalid column entered");
		} else if (!validRowChecker(srcRowString.charAt(0)) || !validRowChecker(targetRowString.charAt(0))){
			System.out.println("Invalid row entered");
		} else {
			srcCol = convertColumnStrToColumnInt(srcColString);
			srcRow = convertRowStrToRowInt(srcRowString);
			tarCol = convertColumnStrToColumnInt(targetColString);
			tarRow = convertRowStrToRowInt(targetRowString);
			//chessGame.movePiece(srcRow, srcCol, tarRow, tarCol);
		}
	}

	public int convertColumnStrToColumnInt(String col){
		if (col.equalsIgnoreCase("a")){
			return Piece.COLUMN_A;
		} else if (col.equalsIgnoreCase("b")){
			return Piece.COLUMN_B;
		} else if (col.equalsIgnoreCase("c")){
			return Piece.COLUMN_C;
		} else if (col.equalsIgnoreCase("d")){
			return Piece.COLUMN_D;
		} else if (col.equalsIgnoreCase("e")){
			return Piece.COLUMN_E;
		} else if (col.equalsIgnoreCase("f")){
			return Piece.COLUMN_F;
		} else if (col.equalsIgnoreCase("g")){
			return Piece.COLUMN_G;
		} else if (col.equalsIgnoreCase("h")){
			return Piece.COLUMN_H;
		} else {
			return 0;
		}
	}

	public int convertRowStrToRowInt(String row){
		if (row.equalsIgnoreCase("1")){
			return Piece.ROW_1;
		} else if (row.equalsIgnoreCase("2")){
			return Piece.ROW_2;
		} else if (row.equalsIgnoreCase("3")){
			return Piece.ROW_3;
		} else if (row.equalsIgnoreCase("4")){
			return Piece.ROW_4;
		} else if (row.equalsIgnoreCase("5")){
			return Piece.ROW_5;
		} else if (row.equalsIgnoreCase("6")){
			return Piece.ROW_6;
		} else if (row.equalsIgnoreCase("7")){
			return Piece.ROW_7;
		} else if (row.equalsIgnoreCase("8")){
			return Piece.ROW_8;
		} else {
			return 0;
		}
	}

	public boolean validColumnChecker(char col){
		if (Character.isLetter(col)){
			if (col < 'i'){
				return true;
			}
		}
		return false;
	}

	public boolean validRowChecker(char row){
		if (Character.isDigit(row)){
			if ((Character.getNumericValue(row) > 0) && (Character.getNumericValue(row) < 9)){
				return true;
			}
		}
		return false;
	}

	private void printCurrentGameState(){
		System.out.println("   a  b  c  d  e  f  g  h  ");

		for (int row = Piece.ROW_8; row >= Piece.ROW_1; row --){
			System.out.println("  +--+--+--+--+--+--+--+--+  ");
			String strRow = (row + 1) + "| ";
			for (int col = Piece.COLUMN_A; col <= Piece.COLUMN_H; col++){
				Piece piece = this.chessGame.getNonCapturedPieceOnPosition(row, col);
				String pieceStr = getNameOfPiece(piece);
				strRow += pieceStr + "|";
			}
			System.out.println(strRow + (row + 1));
        }
        System.out.println("  +--+--+--+--+--+--+--+--+ ");
        System.out.println("   a  b  c  d  e  f  g  h  ");
		String strTurn = (chessGame.getGameState() == ChessGame.GAME_STATE_BLACK ? "black" : "white");
		System.out.println("Turn: " + strTurn);

	}

	private String getNameOfPiece(Piece piece){
		if (piece == null){
			return "  ";
		}

		String strColour = "";
		switch(piece.getColour()){
			case Piece.COLOR_BLACK:
                strColour = "B";
                break;
            case Piece.COLOR_WHITE:
                strColour = "W";
                break;
            default:
                strColour = "";
				break;
        }

		String strType = "";
		switch(piece.getType()){
			case Piece.TYPE_BISHOP:
				strType = "B";
				break;
			case Piece.TYPE_KING:
				strType = "K";
				break;
			case Piece.TYPE_KNIGHT:
				strType = "N";
				break;
			case Piece.TYPE_PAWN:
				strType = "P";
				break;
			case Piece.TYPE_QUEEN:
				strType = "Q";
				break;
			case Piece.TYPE_ROOK:
				strType = "R";
				break;
			default:
				strType = "";
				break;
		}
		return strColour + strType;
	}
	
}