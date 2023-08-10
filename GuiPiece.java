import java.awt.Image;

public class GuiPiece {
	private Image pieceImg;
	private Piece piece;
	private int x, y;
	private int width = 64, height = 64;

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

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}