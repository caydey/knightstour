package com.caydey.knightstour.model;

public class Board {
  private TileState[][] tiles;

  public Board() {
    this.tiles = new TileState[8][8];

    this.initializeBoard();
    this.placeKnight();
  }

  private void initializeBoard() {
    for (int j=0; j<8; j++) {
      for (int i=0; i<8; i++) {
        this.tiles[i][j] = TileState.EMPTY;
      }
    }
  }

  private void placeKnight() {
    this.tiles[0][0] = TileState.KNIGHT;
  }

  public TileState[][] getTiles() {
    return this.tiles;
  }
}
