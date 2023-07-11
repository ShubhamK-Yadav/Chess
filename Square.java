public class Square {
  private Pieces piece;
  private int x, y;
  private boolean occupied;

  public Square(int coordX, int coordY, Pieces piece, boolean occupied){
    x = coordX;
    y = coordY;
    this.piece = piece;
    this.occupied = occupied;
  }

  public int[] getCoords(){
    return new int[] {x, y};
  }

  public Pieces getPiece(){
    return piece;
  }

  public boolean isOccupied(){
    return occupied;
  }
}
