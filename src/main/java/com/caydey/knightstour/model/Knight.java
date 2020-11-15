package com.caydey.knightstour.model;

import java.util.ArrayList;

public class Knight {
  private ArrayList<Coord> trail;
  private int trailLen;
  private Coord position;
  private int undoCount;
   // x,y increments that a knight can make in any direction
  private final int[][] moveInc = {{-1,2},{1,2},{-1,-2},{1,-2},{-2,1},{-2,-1},{2,1},{2,-1}};

  public Knight() {
    this.position = new Coord(0,0);

    this.trail = new ArrayList<Coord>();
    this.trail.add(this.position);
    this.trailLen = 1;
    this.undoCount = 0;
  }

  public void move(int x, int y) {
    this.position = new Coord(x,y);
    this.trail.add(this.position);
    this.trailLen++;
  }

  public void undo() {
    this.position = getLastPosition();
    this.trailLen--;
    this.trail.remove(this.trailLen);

    this.undoCount++;
    if (this.trailLen == 1) { // reset undo count if at start
      this.undoCount = 0;
    }
  }

  public void reset() {
    this.position.x = 0;
    this.position.y = 0;

    this.undoCount = 0;

    this.trail.clear();
    this.trail.add(this.position);
    this.trailLen = 1;
  }

  public ArrayList<Coord> getMoves(TileState[][] tileState) {
    ArrayList<Coord> moves = new ArrayList<Coord>();

    // moveInc defined at class start
    for (int[] inc : this.moveInc) {
      Coord newMove = new Coord(this.position.x + inc[0], this.position.y + inc[1]); // possible move

      if (0 <= newMove.x && newMove.x < 8  &&  0 <= newMove.y && newMove.y < 8) { // in bounds
        if (tileState[newMove.x][newMove.y] == TileState.EMPTY) { // not trail
          moves.add(newMove);
        }
      }
    }
    return moves;
  }

  public Coord getLastPosition() {
    if (this.trailLen > 1) {
      return new Coord(this.trail.get(this.trailLen-2));
    }
    return null;  // no last pos
  }

  public Coord getPosition() { return this.position; }
  public int getUndoCount() { return this.undoCount; }
  public int getTrailLen() { return this.trailLen; }
}
