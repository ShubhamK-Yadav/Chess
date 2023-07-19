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
    private final int START_X = 100, START_Y = 100;
    private static final int BOARD_WIDTH = 512, BOARD_HEIGHT = 512;
    private static final int SQUARE_SIZE = 64;
    private List<Piece> pieces = new ArrayList<Piece>();

    private int gameState = GAME_STATE_WHITE;
    static final int GAME_STATE_WHITE = 0;
    static final int GAME_STATE_BLACK = 1;
    private JLabel lblGameState; 

    /**
     * Constructs a new GameBoard instance.
     */
    public ChessGui() {
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
        this.setLayout(null);

        populateInitialChessBoard();
        PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.pieces, this);
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

    private String getGameStateAsText() {
        return (this.gameState == GAME_STATE_BLACK ? "black" : "white");
    }

    public void changeGameState() {
        switch (this.gameState){
            case GAME_STATE_BLACK:
                this.gameState = GAME_STATE_WHITE;
                break;
            case GAME_STATE_WHITE:
                this.gameState = GAME_STATE_BLACK;
                break;
            default:
                throw new IllegalStateException("unknown game state: " + this.gameState);
        }
        System.out.println(this.gameState);
        this.lblGameState.setText(this.getGameStateAsText());
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
     * @return current game state
     */
    public int getGameState() {
        return this.gameState;
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

        for (Piece piece : pieces) {
            g.drawImage(piece.getImage(), piece.getX(), piece.getY(), 64, 64, null);
        }
    }

    /**
     * Populates the chessboard with pieces in the correct positions.
     */
    public void populateInitialChessBoard() {
        // white pieces
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, START_X + SQUARE_SIZE * 0, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, START_X + SQUARE_SIZE * 1, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, START_X + SQUARE_SIZE * 2, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, START_X + SQUARE_SIZE * 3, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, START_X + SQUARE_SIZE * 4, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, START_X + SQUARE_SIZE * 5, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, START_X + SQUARE_SIZE * 6, START_Y + SQUARE_SIZE * 7);
        createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, START_X + SQUARE_SIZE * 7, START_Y + SQUARE_SIZE * 7);

        // White pawns
        for (int i = 0; i < 8; i++) {
            createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, START_X + SQUARE_SIZE * i, START_Y + SQUARE_SIZE * 6);
        }

        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, START_X + SQUARE_SIZE * 0, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, START_X + SQUARE_SIZE * 1, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, START_X + SQUARE_SIZE * 2, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, START_X + SQUARE_SIZE * 3, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, START_X + SQUARE_SIZE * 4, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, START_X + SQUARE_SIZE * 5, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, START_X + SQUARE_SIZE * 6, START_Y + SQUARE_SIZE * 0);
        createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, START_X + SQUARE_SIZE * 7, START_Y + SQUARE_SIZE * 0);

        // Black pawns
        for (int i = 0; i < 8; i++) {
            createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, START_X + SQUARE_SIZE * i, START_Y + SQUARE_SIZE * 1);
        }
    }

    private void createAndAddPiece(int colour, int type, int x, int y) {
        Image img = this.getImageForPiece(colour, type);
        Piece piece = new Piece(img, x, y, colour, type);
        this.pieces.add(piece);
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