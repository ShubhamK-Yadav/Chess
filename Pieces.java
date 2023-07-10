import java.awt.Image;

public abstract class Pieces {
  private char colour;
  private boolean killed;
  private Image piece;
  private int id;
  private int[] possiblePos;
  
  public Pieces(char colour, int id, boolean killed, Image piece){
    this.colour = colour;
    this.id = id;
    this.killed = killed;
    this.piece = piece;
  }

  // array containing arrays with possible solution.
  public abstract int[] findPossibleMoves();

  public char getColour(){
    return colour;
  }

  public int getId(){
    return id;
  }

  public Image getImage(){
    return piece;
  }

  public void setImage(Image img){
    piece = img;
  }

  // public int getCurrentPosX(){
  //   return arrayPosI;
  // }

  // public int getCurrentPosY(){
  //   return arrayPosJ;
  // }

  public char colour(){
    return colour;
  }

  public boolean killed(){
    return killed;
  }

  public int[] getPossibleMoves(){
    return possiblePos;
  }
}
