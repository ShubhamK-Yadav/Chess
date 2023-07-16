import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Represents the game board for a chess game.
 */
public class GameBoard extends JPanel {
    private static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600;
    private static final int BOARD_WIDTH = 512, BOARD_HEIGHT = 512;
    private static final int SQUARE_SIZE = 64;
    // private static int gameBlocks = BOARD_HEIGHT * BOARD_WIDTH / SQUARE_SIZE;
    private static ArrayList <ArrayList<Square>> chessboard= new ArrayList<ArrayList<Square>>();
    
    /**
     * Constructs a new GameBoard instance.
     */
    public GameBoard(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();

        //creates the arraylists of squares in chessboard
        instantiateSquares();

        //creates chess pieces and add them to the relevant squares in the chessboard
        populateInitialChessBoard();
        movePiece(findSquare(Pawn.class, 2, 'W').getPiece(), 2, 'W', 3, 2);
        
        // for (ArrayList<Square> squares : chessboard){
        //     for(Square sq: squares){
        //         if(sq.getPiece() != null){
        //             System.out.println(sq.getPiece().getClass());
        //         } else{
        //             System.out.println("null");
        //         }
        //         System.out.println(sq);
        //     }
        // }
    }
    
    /**
     * Loads an image from the specified URL.
     * @param url the URL of the image to load
     * @return the loaded BufferedImage instance
     */
    public static BufferedImage loadImage(String url){
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
     * @param g the Graphics object to paint on
     */

    public void paint(Graphics g){
        //Load all the piece images.
        Color lightBrown = new Color(241,194,192);
        Color burntUmber = new Color(154,77,43);

        // render the chess board
        for (int i = 0; i < BOARD_WIDTH/SQUARE_SIZE; i++) {
            //Creating a grid for the chess board
            g.drawLine(i * SQUARE_SIZE + 50, 50, i * SQUARE_SIZE + 50, 512);
            g.drawLine(50, i * SQUARE_SIZE+ 50, 512, i * SQUARE_SIZE + 50);
            // System.out.println("I value is " + i);
            int x =  i * SQUARE_SIZE + 50;
            
            for(int j = 0; j < BOARD_HEIGHT/SQUARE_SIZE; j++){
                // System.out.println("J value is: " + j);
                int y = j * SQUARE_SIZE + 50;
                g.setColor(burntUmber);
                
                // check if the tile is a even tile, it is then chaange colour to lightBrown.
                if ((i+j) % 2 == 0){  
                    g.setColor(lightBrown);    
                }
                g.fillRect(i * SQUARE_SIZE + 50, j * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);
                if (chessboard.get(j).get(i).getPiece() != null){
                    g.drawImage(chessboard.get(j).get(i).getPiece().getImage(), x, y,64,64, null);
                }
            }
        }
    }
    
    /**
     * Finds and returns the Square object at the specified coordinates (x, y).
     * @param x the x-coordinate of the Square
     * @param y the y-coordinate of the Square
     * @return the Square object at the specified coordinates, or null if not found
     */
    public static Square findSquare(int x, int y){
        for (ArrayList<Square> squares : chessboard){
            for(Square sq: squares){
                if(sq.getCoords()[0] == x && sq.getCoords()[1] == y){
                    return sq;
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns the Square object containing a specific piece type, id, and color.
     * @param type    the class representing the type of the piece
     * @param id      the id of the piece
     * @param colour  the color of the piece
     * @return the Square object containing the specified piece, or null if not found
     */
    public static Square findSquare(Class<?> type, int id, char colour){
        for (ArrayList<Square> squares : chessboard){
            for(Square sq: squares){
                if(sq.getPiece() != null && sq.getPiece().getClass().equals(type) && sq.getPiece().getId() == id && 
                sq.getPiece().getColour() == colour){
                    return sq;
                }
            }
        }
        return null;
    }

    /**
     * Moves a piece to the specified position (x, y) on the chessboard.
     * @param piece  the piece to move
     * @param id     the id of the piece
     * @param colour the color of the piece
     * @param x      the x-coordinate to move the piece to
     * @param y      the y-coordinate to move the piece to
     */
    public static void movePiece(Pieces piece, int id, char colour, int x, int y){
        Square squareWithPiece = findSquare(piece.getClass(), id, colour);
        chessboard.get(squareWithPiece.getCoords()[1]).get(squareWithPiece.getCoords()[0]).setPiece(null);
        chessboard.get(y).get(x).setPiece(piece);
    }

    /**
     * Instantiates the arraylists of squares in the chessboard.
     */
    public static void instantiateSquares(){
        for (int i = 0; i < 8; i++){
            chessboard.add(new ArrayList<Square>());
        }
    }

    /**
     * Populates the chessboard with pieces in the correct positions.
     */
    public static void populateInitialChessBoard(){
        //Loading all the images using the loadImage method.
        Image darkRook = loadImage("img/darkRook.png");
        Image whiteRook = loadImage("img/whiteRook.png");
        Image darkBishop = loadImage("img/darkBishop.png");
        Image whiteBishop = loadImage("img/whiteBishop.png");
        Image darkKing = loadImage("img/darkKing.png");
        Image whiteKing = loadImage("img/whiteKing.png");
        Image darkQueen = loadImage("img/darkQueen.png");
        Image whiteQueen = loadImage("img/whiteQueen.png");
        Image darkKnight = loadImage("img/darkKnight.png");
        Image whiteKnight = loadImage("img/whiteKnight.png");
        Image darkPawn = loadImage("img/darkPawn.png");
        Image whitePawn = loadImage("img/whitePawn.png");

        for (int i = 0; i < chessboard.size(); i++) {
            for (int j = 0; j < chessboard.size(); j++) {
                Pieces piece = null;
                boolean isOccupied = false;
                
                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        piece = new Rook(i == 0 ? 'B' : 'W', j, false, i == 0 ? darkRook : whiteRook);
                        isOccupied = true;
                    } else if (j == 1 || j == 6) {
                        piece = new Knight(i == 0 ? 'B' : 'W', j, false, i == 0 ? darkKnight : whiteKnight);
                        isOccupied = true;
                    } else if (j == 2 || j == 5) {
                        piece = new Bishop(i == 0 ? 'B' : 'W', j, false, i == 0 ? darkBishop : whiteBishop);
                        isOccupied = true;
                    } else if (j == 3) {
                        piece = new Queen(i == 0 ? 'B' : 'W', j, false, i == 0 ? darkQueen : whiteQueen);
                        isOccupied = true;
                    } else if (j == 4) {
                        piece = new King(i == 0 ? 'B' : 'W', j, false, i == 0 ? darkKing : whiteKing);
                        isOccupied = true;
                    }
                } else if (i == 1 || i == 6) {
                    piece = new Pawn(i == 1 ? 'B' : 'W', j, false, i == 1 ? darkPawn : whitePawn);
                    isOccupied = true;
                }
                
                chessboard.get(i).add(new Square(j, i, piece, isOccupied));
            }
        }
        
    }
}