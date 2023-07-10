import java.awt.Image;

public class Knight extends Pieces {
  public Knight(char colour, int id, boolean killed, Image piece){
    super(colour, id, killed, piece);
  }

  @Override
  public int[] findPossibleMoves() {
    throw new UnsupportedOperationException("Unimplemented method 'findPossibleMoves'");
  }
}
