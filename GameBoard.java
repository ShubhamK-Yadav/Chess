import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameBoard extends JPanel {
    private static final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600;
    private static final int BOARD_WIDTH = 512, BOARD_HEIGHT = 512;
    private static final int SQUARE_SIZE = 64;
    // private static int gameBlocks = BOARD_HEIGHT * BOARD_WIDTH / SQUARE_SIZE;
    private static ArrayList <ArrayList<Pieces>> chessboard= new ArrayList<ArrayList<Pieces>>();
    
    private static ArrayList <Pieces> whitePieces = new ArrayList <Pieces>();
    private static ArrayList <Pieces> blackPieces = new ArrayList <Pieces>();

    private static ArrayList <Pieces> whitePawns = new ArrayList <Pieces>();
    private static ArrayList <Pieces> blackPawns = new ArrayList <Pieces>();

    public GameBoard(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
        populateInitialChessBoard();
    }

    public static BufferedImage loadImage(String url){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch (IOException e) {
        System.out.println("Could not load " + url);
        }
        return img;
    }

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
                
                // check if the tile is a even tile.
                if ((i+j) % 2 == 0){  
                    g.setColor(lightBrown);    
                }
                g.fillRect(i * SQUARE_SIZE + 50, j * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);
                if (chessboard.get(j).get(i) != null){
                    g.drawImage(chessboard.get(j).get(i).getImage(), x, y,64,64, null);
                    System.out.println(getPosition(Pawn.class, 5, 'W')[0] + ", " + getPosition(Pawn.class, 5, 'W')[1]);
                }
            }
        }
    }

    public static int[] getPosition(Class<?> type, int id, char colour){
        for (int i = 0; i < chessboard.size(); i++){
            for (int j = 0; j < chessboard.get(i).size(); j++){
                if (chessboard.get(j).get(i) != null){
                    Pieces piece = chessboard.get(j).get(i);
                    if (piece.getClass().equals(type) && piece.getId() == id && piece.getColour() == colour){
                        return new int[] {j,i};
                    }
                }
            }
        }
        return null;
    }

    public static void movePiece(Pieces piece, int id, char colour, int x, int y){
        // if there is a piece or a empty space
        int[] pos = getPosition(piece.getClass(), id, colour);
        Pieces thePiece = chessboard.get(pos[1]).get(pos[0]);
        // chessboard.replaceAll()
    }

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

        //Create and add black pieces
        blackPieces.add(new Rook('B', 1, false, darkRook));
        blackPieces.add(new Knight('B', 1, false, darkKnight));
        blackPieces.add(new Bishop('B', 1, false, darkBishop));
        blackPieces.add(new King('B', 1, false, darkKing));
        blackPieces.add(new Queen('B', 1, false, darkQueen));
        blackPieces.add(new Bishop('B', 2, false, darkBishop));
        blackPieces.add(new Knight('B', 2, false, darkKnight));
        blackPieces.add(new Rook('B', 2, false, darkRook));

        chessboard.add(blackPieces);

        for (int i = 1; i < 9; i++) {
            blackPawns.add(new Pawn('B', i, false, darkPawn));
        }
        chessboard.add(blackPawns);

        // Create and add the empty rows (rows 6 to 3)
        for (int i = 0; i < 4; i++) {
            ArrayList<Pieces> emptyRow = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                emptyRow.add(null);
            }
            chessboard.add(emptyRow);
        }
        
        // Create and add white pieces
        for (int i = 1; i < 9; i++) {
            whitePawns.add(new Pawn('W', i, false, whitePawn));
        }

        chessboard.add(whitePawns);

        whitePieces.add(new Rook('W', 1, false, whiteRook));
        whitePieces.add(new Knight('W', 1, false,whiteKnight));
        whitePieces.add(new Bishop('W', 1, false, whiteBishop));
        whitePieces.add(new King('W', 1, false, whiteKing));
        whitePieces.add(new Queen('W', 1, false, whiteQueen));
        whitePieces.add(new Bishop('W', 2, false, whiteBishop));
        whitePieces.add(new Knight('W', 2, false, whiteKnight));
        whitePieces.add(new Rook('W', 2, false, whiteRook));

        chessboard.add(whitePieces);

    }
}