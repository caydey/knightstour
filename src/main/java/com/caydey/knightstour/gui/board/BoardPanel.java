package com.caydey.knightstour.gui.board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.caydey.knightstour.model.TileState;

public class BoardPanel extends JPanel {
  private static TileButton[][] tileButtons;
  private GridBagConstraints c;

  // Listener
  private TileListener tileListener;
  public void setTileListener(TileListener tileListener) {
    this.tileListener = tileListener;
  }

  public BoardPanel(TileState[][] tiles) {
    this.tileButtons = new TileButton[8][8];

    this.setLayout(new GridBagLayout());
    c = new GridBagConstraints();

    this.initializePanel(tiles);
  }

  private void initializePanel(TileState[][] tiles) {
    boolean blackWhite = true;
    for (int j=0; j<8; j++) {
      c.gridy = j;
      for (int i=0; i<8; i++) {
        c.gridx = i;
        tileButtons[i][j] = new TileButton(tiles[i][j], i,j, blackWhite);
        tileButtons[i][j].addActionListener(new ActionListener() { // couldve used (this) but i dont like to add the implements thing at top
          public void actionPerformed(ActionEvent evt) {
            TileButton evtTile = (TileButton) evt.getSource(); // object of button pressed
            tileListener.tileAction(evtTile.getXPos(), evtTile.getYPos()); // x & y coords from button object
          }
        });
        this.add(tileButtons[i][j], c);
        blackWhite = !blackWhite;
      }
      blackWhite = !blackWhite;
    }
  }

  public static TileButton[][] getTileButtons() {
    return tileButtons;
  }
}
