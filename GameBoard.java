import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private static final int WINDOW_WIDTH = 700, WINDOW_HEIGHT = 700;
    private static final int BOARD_WIDTH = 600, BOARD_HEIGHT = 600;
    private static final int SQUARE_SIZE = 75;
    // private static int gameBlocks = BOARD_HEIGHT * BOARD_WIDTH / SQUARE_SIZE;

    private char[][] chessboard = {
        {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}
    };

    public GameBoard(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
    }

    public void paint(Graphics g){
        // render the chess board
        for (int i = 0; i < BOARD_WIDTH/SQUARE_SIZE; i++) {
            g.drawLine(i * SQUARE_SIZE + 50, 50, i * SQUARE_SIZE + 50, 650);
            g.drawLine(50, i * SQUARE_SIZE+ 50, 650, i * SQUARE_SIZE + 50);
            for(int j = 0; j < BOARD_HEIGHT/SQUARE_SIZE; j++){
                int x =  i * SQUARE_SIZE + (50 + SQUARE_SIZE/4);
                int y = j * SQUARE_SIZE + (50 + SQUARE_SIZE/4);
                g.setColor(Color.GRAY);
                
                // check if the tile is a even tile.
                if ((i+j) % 2 == 0){  
                    g.setColor(Color.LIGHT_GRAY);    
                }
                g.fillRect(i * SQUARE_SIZE + 50, j * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);

                char chessPiece = chessboard[j][i];
                if (chessPiece != ' '){
                    // Image chessPieceImage = getChessPieceImage(chessPiece);
                    // g.drawImage(chessPieceImage, i * SQUARE_SIZE + 50, j * SQUARE_SIZE + 50, null);
                    int sideLength = SQUARE_SIZE/3;
                    int xPoint = i * SQUARE_SIZE + (50 + SQUARE_SIZE/2);
                    int yPoint = j * SQUARE_SIZE + (50 + SQUARE_SIZE/2);
                    int[] xPoints = { xPoint, xPoint + sideLength, xPoint, xPoint - sideLength };
                    int[] yPoints = { yPoint - sideLength, yPoint, yPoint + sideLength, yPoint };
                    
                    switch (chessPiece){ 
                        case ('p'):
                            g.setColor(Color.BLUE);
                            g.fillOval(x, y, SQUARE_SIZE/2, SQUARE_SIZE/2);
                            break;
                        case ('P'):
                            g.setColor(Color.white);
                            g.fillOval(x, y, SQUARE_SIZE/2, SQUARE_SIZE/2);
                            break;
                        case ('r'):
                            g.setColor(Color.BLUE);
                            g.fillRect(x, y, SQUARE_SIZE/2, SQUARE_SIZE/2);
                            break;
                        case ('R'):
                            g.setColor(Color.white);
                            g.fillRect(x, y, SQUARE_SIZE/2, SQUARE_SIZE/2);
                            break;
                        case ('n'):
                            g.setColor(Color.BLUE);
                            g.fillPolygon(xPoints, yPoints, 4);
                            break;
                        case ('N'):
                            g.setColor(Color.white);
                            g.fillPolygon(xPoints, yPoints, 4);
                            break;  
                        case ('b'):
                            g.setColor(Color.BLUE);
                            int[] xTrig = { x, xPoint + sideLength, xPoint };
                            int[] yTrig = { yPoint - sideLength, y, yPoint + sideLength }; 
                            g.fillPolygon(xTrig, yTrig, 3);
                            break;
                        case ('B'):
                            g.setColor(Color.white);
                            int[] xTriag = { x, xPoint + sideLength, xPoint };
                            int[] yTriag = { yPoint - sideLength, y, yPoint + sideLength }; 
                            g.fillPolygon(xTriag, yTriag, 3);
                            break;
                        case ('K'):    
                            g.setColor(Color.white);
                            g.setFont(new Font("Arial", Font.PLAIN, 48));
                            g.drawString("K", xPoint-15, yPoint+15);
                            break;
                        case ('k'):    
                            g.setColor(Color.BLUE);
                            g.setFont(new Font("Arial", Font.PLAIN, 48));
                            g.drawString("K", xPoint-15, yPoint+15);
                            break;
                        case ('Q'):    
                            g.setColor(Color.white);
                            g.setFont(new Font("Arial", Font.PLAIN, 48));
                            g.drawString("Q", xPoint-15, yPoint+15);
                            break;
                        case ('q'):    
                            g.setColor(Color.BLUE);
                            g.setFont(new Font("Arial", Font.PLAIN, 48));
                            g.drawString("Q", xPoint-15, yPoint+15);
                            break;    
                    }
                }
            }
        }
    }
    
    // public Image getChessPieceImage(char piece){
    //     if (piece == 'p'){

    //     }
        
    //     return null;
    // }
}