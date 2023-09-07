import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for a chess game.
 */
public class ChessGui extends JPanel {
    private static final int WINDOW_WIDTH = 700, WINDOW_HEIGHT = 700;
    private static final int START_X = 100, START_Y = 100;
    private static final int BOARD_WIDTH = 512, BOARD_HEIGHT = 512;
    private static final int SQUARE_SIZE = 64;
    
    private int gameState = GAME_STATE_WHITE;
    static final int GAME_STATE_WHITE = 0;
	static final int GAME_STATE_BLACK = 1;

    private JLabel lblGameState;
    
    private ChessGame chessGame;
    private List<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    private GuiPiece dragPiece;
    private Move lastMove;
    /**
     * Constructs a new GameBoard instance.
     */
    public ChessGui() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
        this.setVisible(true);

        this.chessGame = new ChessGame();
        for (Piece piece : chessGame.getPieces()){
            createAndAddPieceGui(piece);
        }

        PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.getGuiPieces(), this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        JButton btnChangeState = new JButton("End turn");
        btnChangeState.addActionListener(new ChangeGameStateButtonActionListener(this));
        btnChangeState.setBounds(0,0, 100, 30);
        this.add(btnChangeState);

        // label to display game state
        String labelText = this.getGameStateAsText();
        this.lblGameState = new JLabel(labelText);
        this.lblGameState.setBounds(0, 30, 80, 30);
        this.add(this.lblGameState);
        System.out.println(this.lblGameState.isShowing());
    }

    public static int convertColumnToX(int column){
        return START_X + SQUARE_SIZE * column;
    }

    public static int convertRowToY(int row){
        return START_Y + SQUARE_SIZE * row;
    }

    public static int convertXToColumn(int x){
        return (x - START_X) / SQUARE_SIZE;
    }

    public static int convertYToRow(int y){
        return Piece.ROW_8 - (y - START_Y) / SQUARE_SIZE;
    }

    private boolean isUserDraggingPiece() {
        return this.dragPiece != null;
    }

    public GuiPiece getDragPiece(){
        return dragPiece;
    }

    public GuiPiece setDragPiece(GuiPiece piece){
        return this.dragPiece = piece;
    }
    /**
     * create a game piece
     *
     * @param color color constant
     * @param type type constant
     * @param x x position of upper left corner
     * @param y y position of upper left corner
     */
    private void createAndAddPieceGui(Piece piece) {
        Image img = this.getImageForPiece(piece.getColour(), piece.getType());
        GuiPiece guiPiece = new GuiPiece(img, piece);
        this.guiPieces.add(guiPiece);
    }

    public void setNewPieceLocation(GuiPiece dragPiece, int x, int y) {
        int targetRow = ChessGui.convertYToRow(y);
        int targetColumn = ChessGui.convertXToColumn(x);
    
        if (targetRow < Piece.ROW_1 || targetRow > Piece.ROW_8 || targetColumn < Piece.COLUMN_A || targetColumn > Piece.COLUMN_H) {
            // Reset piece position if the move is not valid
            dragPiece.setToUnderlyingPiecePositions();
    
        } else {
            // Change model and update GUI piece afterwards
            System.out.println("Moving piece to " + targetRow + "/" + targetColumn);
            Move currentMove = new Move(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(), 
                    targetRow, targetColumn);
            // this.chessGame.movePiece(currentMove);
            this.lastMove = currentMove;
            // Assuming that resetToUnderlyingPiecePosition() sets the new position for the dragPiece
            dragPiece.setToUnderlyingPiecePositions();
        }
    }   
 
    /**
     * switches between the different game states
     */
    public void changeGameState() {
        this.chessGame.changeGameState();
        this.lblGameState.setText(this.getGameStateAsText());
    }

    private String getGameStateAsText() {
        return (this.gameState == GAME_STATE_BLACK ? "black" : "white");
    }

    /**
     * Loads an image from the specified URL.
     * 
     * @param url the URL of the image to load
     * @return the loaded BufferedImage instance
     */
    public static BufferedImage loadImage(String url) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch (IOException e) {
            System.out.println("Could not load " + url);
        }
        return img;
    }

    private Image getImageForPiece(int colour, int type) {
        String filename = "img/";

        filename += (colour == Piece.COLOR_WHITE ? "w" : "d");
        switch (type) {
            case Piece.TYPE_BISHOP:
                filename += "b";
                break;
            case Piece.TYPE_KING:
                filename += "k";
                break;
            case Piece.TYPE_KNIGHT:
                filename += "n";
                break;
            case Piece.TYPE_PAWN:
                filename += "p";
                break;
            case Piece.TYPE_QUEEN:
                filename += "q";
                break;
            case Piece.TYPE_ROOK:
                filename += "r";
                break;
        }

        filename += ".png";
        Image img = loadImage(filename);
        return img;
    }

    public void paint(Graphics g) {
        // Load all the piece images.
        Color lightBrown = new Color(241, 194, 192);
        Color burntUmber = new Color(154, 77, 43);
        g.setColor(new Color (41, 44, 44));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // render the chess board
        for (int i = 0; i < BOARD_WIDTH / SQUARE_SIZE; i++) {
            // Creating a grid for the chess board
            for (int j = 0; j < BOARD_HEIGHT / SQUARE_SIZE; j++) {
                // System.out.println("J value is: " + j);
                g.setColor(burntUmber);

                // check if the tile is a even tile, it is then chaange colour to lightBrown.
                if ((i + j) % 2 == 0) {
                    g.setColor(lightBrown);
                }
                g.fillRect(i * SQUARE_SIZE + START_X, j * SQUARE_SIZE + START_Y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        for (GuiPiece piece : guiPieces) {
            g.drawImage(piece.getImage(), piece.getX(), piece.getY(), piece.getWidth(), piece.getHeight(), null);
        }

        if (isUserDraggingPiece()){
            MoveValidator moveVal = this.chessGame.getMoveValidator();
            int sourceRow = this.dragPiece.getPiece().getRow();
            int sourceColumn = this.dragPiece.getPiece().getColumn();

            //iterating through each row and col
            for (int col = Piece.COLUMN_A; col <= Piece.COLUMN_H; col++){
                for (int row = Piece.ROW_1; row <= Piece.ROW_8; row++){
                    // System.out.println(moveVal.isMoveValid(new Move(sourceRow, sourceColumn, row, col)));
                    // check if target location is valid
                    if (moveVal.isMoveValid(new Move(sourceRow, sourceColumn, row, col))){
                        int highlightX = convertColumnToX(col);
                        int highlightY = convertRowToY(row);
 
                        // draw a black drop shadow by drawing a black rectangle with an offset of 1 pixel
                        g.setColor(Color.BLACK);
                        g.drawRoundRect( highlightX + 5, highlightY + 5, SQUARE_SIZE - 8, SQUARE_SIZE - 8,10,10);
                        // draw the highlight
                        g.setColor(Color.GREEN);
                        g.drawRoundRect( highlightX + 4, highlightY + 4, SQUARE_SIZE - 8, SQUARE_SIZE - 8,10,10);
                    }
                }
            }
        }

        if(!isUserDraggingPiece() &&  this.lastMove != null){
            int highlightSourceX = convertColumnToX(this.lastMove.sourceCol);
            int highlightSourceY = convertRowToY(this.lastMove.sourceRow);
            int highlightTargetX = convertColumnToX(this.lastMove.targetCol);
            int highlightTargetY = convertRowToY(this.lastMove.targetRow);

            g.setColor(Color.YELLOW);
            g.fillRoundRect(highlightSourceX+4, highlightSourceY+4, SQUARE_SIZE-8, SQUARE_SIZE-8,10,10);
            g.fillRoundRect(highlightTargetX+4, highlightTargetY+4, SQUARE_SIZE-8, SQUARE_SIZE-8,10,10);
        }
    }

    public List<GuiPiece> getGuiPieces(){
        return guiPieces;
    }
}