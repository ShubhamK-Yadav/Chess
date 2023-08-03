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
    /**
     * Constructs a new GameBoard instance.
     */
    public ChessGui() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);

        this.chessGame = new ChessGame();
        for (Piece piece : chessGame.getPieces()){
            createAndAddPieceGui(piece);
        }
        PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.chessGame.getPieces(), this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        JButton btnChangeState = new JButton("End turn");
        btnChangeState.addActionListener(new ChangeGameStateButtonActionListener(this));
        btnChangeState.setBounds(0,0, 100, 30);
        this.add(btnChangeState);

        // label to display game state
        String labelText = this.getGameStateAsText();
        this.lblGameState = new JLabel(labelText);
        lblGameState.setBounds(0, 30, 80, 30);
        lblGameState.setForeground(Color.WHITE);
        this.add(lblGameState);
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
            this.chessGame.movePiece(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(),
                    targetRow, targetColumn);
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

    /**
     * Overrides the paint method to render the chess board and pieces.
     * 
     * @param g the Graphics object to paint on
     */
    public void paint(Graphics g) {
        // Load all the piece images.
        Color lightBrown = new Color(241, 194, 192);
        Color burntUmber = new Color(154, 77, 43);
        g.setColor(new Color (41, 44, 44));
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // render the chess board
        for (int i = 0; i < BOARD_WIDTH / SQUARE_SIZE; i++) {
            // Creating a grid for the chess board
            // g.drawLine(i * SQUARE_SIZE + START_X, 50, i * SQUARE_SIZE + START_Y, 512);
            // g.drawLine(50, i * SQUARE_SIZE + START_X, BOARD_HEIGHT, i * SQUARE_SIZE + START_Y);
            // System.out.println("I value is " + i);

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
            g.drawImage(piece.getImage(), piece.getX(), piece.getY(), 64, 64, null);
        }
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
}