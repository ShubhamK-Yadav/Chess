import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {
    private List<Piece> pieces;
    private ChessGui chessGUI;

    private Piece dragPiece;
    private int dragOffsetX, dragOffsetY;

    public PiecesDragAndDropListener(List<Piece> pieces, ChessGui chessGUI) {
        this.pieces = pieces;
        this.chessGUI = chessGUI;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.dragPiece != null) {
            this.dragPiece.setX(e.getPoint().x - this.dragOffsetX);
            this.dragPiece.setY((e.getPoint().y - this.dragOffsetY));
            this.chessGUI.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int coordX = e.getPoint().x;
        int coordY = e.getPoint().y;

        for (int i = this.pieces.size() - 1; i >= 0; i--) {
            Piece piece = this.pieces.get(i);

            if (mouseOverPiece(piece, coordX, coordY)) {
                this.dragOffsetX = coordX - piece.getX();
                this.dragOffsetY = coordY - piece.getY();
                System.out.println("DragOffsetX: " + this.dragOffsetX + " and " + "DragOffsetY: " + this.dragOffsetY);
                this.dragPiece = piece;
                break;
            }
        }

        if (dragPiece != null) {
            this.pieces.remove(this.dragPiece);
            this.pieces.add(this.dragPiece);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.dragPiece = null;
    }

    public boolean mouseOverPiece(Piece piece, int x, int y) {
        return piece.getX() <= x
                && piece.getX() + piece.getWidth() >= x
                && piece.getY() <= y
                && piece.getY() + piece.getHeight() >= y;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
