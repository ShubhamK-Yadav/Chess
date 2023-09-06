import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {
    private List<GuiPiece> pieces;
    private ChessGui chessGUI;

    private int dragOffsetX, dragOffsetY;

    public PiecesDragAndDropListener(List<GuiPiece> pieces, ChessGui chessGUI) {
        this.pieces = pieces;
        this.chessGUI = chessGUI;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.chessGUI.getDragPiece() != null) {
            this.chessGUI.getDragPiece().setX(e.getPoint().x - this.dragOffsetX);
            this.chessGUI.getDragPiece().setY((e.getPoint().y - this.dragOffsetY));
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
            GuiPiece piece = this.pieces.get(i);

            if (mouseOverPiece(piece, coordX, coordY)) {
                this.dragOffsetX = coordX - piece.getX();
                this.dragOffsetY = coordY - piece.getY();
                //System.out.println("DragOffsetX: " + this.dragOffsetX + " and " + "DragOffsetY: " + this.dragOffsetY);
                this.chessGUI.setDragPiece(piece);
                break;
            }
        }

        if (this.chessGUI.getDragPiece() != null) {
            this.pieces.remove(this.chessGUI.getDragPiece());
            this.pieces.add(this.chessGUI.getDragPiece());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.chessGUI.setDragPiece(null);
    }

    public boolean mouseOverPiece(GuiPiece piece, int x, int y) {
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