import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    private static final int WINDOW_WIDTH = 700, WINDOW_HEIGHT = 700;
    private static final int BOARD_WIDTH = 600, BOARD_HEIGHT = 600;
    private static final int SQUARE_SIZE = 75;
    // private static int gameBlocks = BOARD_HEIGHT * BOARD_WIDTH / SQUARE_SIZE;

    public GameBoard(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.requestFocus();
    }

    public void paint(Graphics g){
 
        // render the chess board
        for (int i = 0; i < BOARD_WIDTH/SQUARE_SIZE; i++) {
            g.drawLine(i * SQUARE_SIZE + 50, 50, i * SQUARE_SIZE+ 50, 650);
            g.drawLine(50, i*SQUARE_SIZE+ 50, 650, i*SQUARE_SIZE+ 50);
            for(int j = 0; j < BOARD_HEIGHT/SQUARE_SIZE; j++){
                g.setColor(Color.green);
                if ((i+j) % 2 == 0){  
                    g.setColor(Color.white);    
                }
                g.fillRect(i * SQUARE_SIZE + 50, j * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
}