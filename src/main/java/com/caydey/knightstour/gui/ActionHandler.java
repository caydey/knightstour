package com.caydey.knightstour.gui;

import com.caydey.knightstour.model.*;
import com.caydey.knightstour.gui.board.*;
import com.caydey.knightstour.gui.toolbar.*;

import javax.swing.JOptionPane;

public class ActionHandler {
  private TileState[][] tiles;
  private Knight knight;
  private boolean showMoves;
  private WindowFrame frame;

  public ActionHandler(WindowFrame frame, TileState[][] tiles, Knight knight) {
    this.frame = frame;
    this.tiles = tiles;
    this.knight = knight;
    this.showMoves = false;
  }

  public void toolbarPressed(ToolbarActions action) {
    TileButton[][] tileButtons = BoardPanel.getTileButtons(); // gui buttons
    if (action == ToolbarActions.SHOW_MOVES) { // SHOW MOVES
      this.showMoves = !this.showMoves; // toggle
      ToolbarPanel.setShowMoves(this.showMoves); // Lable changes from "Show Moves" to "Hide Moves"

      // show/hide moves
      if (this.showMoves) {
        this.showKnightMoves(tileButtons);
      } else {
        this.hideKnightMoves(tileButtons);
      }
    } else if (action == ToolbarActions.UNDO) { // UNDO
      this.undo(tileButtons);
    } else { // RESTART
      if (!this.knight.getPosition().equals(new Coord(0,0))) { // not at start
        // confirmation
        String[] restartOpt = {"Restart", "Cancel"};
        int restartConfirm = JOptionPane.showOptionDialog(this.frame, "Are you sure you want to restart?", "Knights Tour",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, restartOpt, restartOpt[0]);
        if (restartConfirm == 0) {  // Restart
          this.resetTiles(tileButtons);
        }
      }
    }
  }

  private void undo(TileButton[][] tileButtons) {
    Coord now = this.knight.getPosition();
    Coord last = this.knight.getLastPosition();
    if (!now.equals(new Coord(0,0))) { // not at start
      this.hideKnightMoves(tileButtons);

      this.tiles[now.x][now.y] = TileState.EMPTY; // remove knight from current pos
      this.tiles[last.x][last.y] = TileState.KNIGHT; // (re)place knight to last pos
      this.knight.undo();

      // GUI
      tileButtons[now.x][now.y].setState(TileState.EMPTY);
      tileButtons[last.x][last.y].setState(TileState.KNIGHT);

      if (this.showMoves) { this.showKnightMoves(tileButtons); }

      // "Completed x/64" label
      ToolbarPanel.setCompleted(knight.getTrailLen());
    }
  }

  private void resetTiles(TileButton[][] tileButtons) {
    // reset all tiles
    for (int j=0; j<8; j++) {
      for (int i=0; i<8; i++) {
        this.tiles[i][j] = TileState.EMPTY;
        tileButtons[i][j].setState(TileState.EMPTY); // GUI
      }
    }
    // (re)place knight at 0,0
    tiles[0][0] = TileState.KNIGHT;
    tileButtons[0][0].setState(TileState.KNIGHT); // GUI
    this.knight.reset();

    // show possible moves (GUI)
    if (this.showMoves) { showKnightMoves(tileButtons); }

    // "Completed x/64" label
    ToolbarPanel.setCompleted(this.knight.getTrailLen());
  }

  private void gameWon() {
    String[] opt = {"Play Again", "Exit"};
    int option = JOptionPane.showOptionDialog(frame, "You have completed the Knights Tour\n\nUndos: "+this.knight.getUndoCount(), "Knights Tour",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, opt[0]);

    if (option == 0) { // Play Again
      resetTiles(BoardPanel.getTileButtons()); // restart
    } else { // Exit
      this.frame.dispose(); // close window
    }
  }
  private void gameLost() {
    String[] opt = {"Try Again", "Undo", "Exit"};
    int option = JOptionPane.showOptionDialog(frame, "No more valid moves left.\n\nCompleted: "+this.knight.getTrailLen()+"/64\nUndos: "+this.knight.getUndoCount(), "Knights Tour",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, opt[0]);

    if (option == 0) { // Try Again
      this.resetTiles(BoardPanel.getTileButtons()); // restart
    } else if (option == 1) { // Undo
      this.undo(BoardPanel.getTileButtons());
    } else { // Exit
      this.frame.dispose(); // close window
    }
  }

  public void tilePressed(int x, int y) {
    TileButton[][] tileButtons = BoardPanel.getTileButtons(); // gui buttons
    if (this.tiles[x][y] == TileState.EMPTY && this.knight.getMoves(this.tiles).contains(new Coord(x,y))) { // tile is empty and is possible move for knight (empty check for optimization)
      int lastX = this.knight.getPosition().x;
      int lastY = this.knight.getPosition().y;

      hideKnightMoves(tileButtons); // hide prev pos moves

      // move knight
      this.tiles[lastX][lastY] = TileState.VISITED;
      this.tiles[x][y] = TileState.KNIGHT;
      this.knight.move(x,y);

      // move knight GUI
      tileButtons[lastX][lastY].setState(TileState.VISITED);
      tileButtons[x][y].setState(TileState.KNIGHT);

      // show possible moves (GUI)
      if (this.showMoves) { this.showKnightMoves(tileButtons); }

      // "Completed x/64" label
      ToolbarPanel.setCompleted(this.knight.getTrailLen());

      // check win/lose
      if (this.knight.getMoves(tiles).size() == 0) { // no more moves
        if (this.knight.getTrailLen() == 64) { // all tiles visited
          this.gameWon();
        } else {
          this.gameLost();
        }
      }
    }
  }

  private void showKnightMoves(TileButton[][] tileButtons) {
    for (Coord move : this.knight.getMoves(this.tiles)) {
      tileButtons[move.x][move.y].highlight();
    }
  }
  private void hideKnightMoves(TileButton[][] tileButtons) {
    for (Coord move : this.knight.getMoves(this.tiles)) {
      tileButtons[move.x][move.y].unHighlight();
    }
  }
}
