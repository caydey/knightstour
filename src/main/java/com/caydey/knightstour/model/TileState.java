package knightstour.model;

public enum TileState {
  EMPTY,
  KNIGHT,
  VISITED;

  public String toString() { // for debugging
    if (this == TileState.EMPTY) {
      return ".";
    } else if (this == TileState.KNIGHT) {
      return "K";
    } else {
      return "x";
    }
  }
}
