import java.awt.Image;

public class GuiPiece {
	private Image pieceImg;
	private Piece piece;
	private int x, y;

	public GuiPiece(Image img, Piece piece) {
		pieceImg = img;
		this.piece = piece;

		this.setToUnderlyingPiecePositions();
	}

	public Piece getPiece() {
		return piece;
	}

	public Image getImage() {
		return pieceImg;
	}

	public void setToUnderlyingPiecePositions() {
		this.x = ChessGui.convertColumnToX(piece.getColumn());
		this.y = ChessGui.convertRowToY(piece.getRow());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}