package com.caydey.knightstour.model;

public class Coord {
  public int x;
  public int y;

  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public Coord(Coord coord) {
    this.x = coord.x;
    this.y = coord.y;
  }

  @Override
  public String toString() { // debugging purposes
    return String.format("[%s, %s]", this.x, this.y);
  }

  @Override
  public boolean equals(Object object) {  // so ArrayList can used contains() function (ButtonHandler.java buttonPressed method)
    if (object != null && object instanceof Coord) { // object is Coord type
      Coord coord = (Coord) object; // typecase object to Coord type
      if (this.x == coord.x && this.y == coord.y) { // Equals Coord
        return true;
      }
    }
    return false;
  }
}
