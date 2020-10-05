package knightstour.gui.board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import knightstour.model.TileState;

public class TileButton extends JButton {
  private TileState state;
  private final Color baseColor;
  private final int x;
  private final int y;

  public TileButton(TileState state, int x, int y, boolean blackWhite) {
    this.baseColor = (blackWhite) ? Color.WHITE : Color.BLACK;
    this.x = x;
    this.y = y;

    this.setState(state);

    this.setBorder(null);
    this.setPreferredSize(new Dimension(42, 42)); // 40 + 2 padding
  }

  public void highlight() { // for showing knight moves
    this.setBackground(Color.CYAN);
  }
  public void unHighlight() {
    this.setState(this.state);
  }

  public void setState(TileState state) {
    this.state = state; // update state
    this.setIcon(null); // reset icon
    if (state == TileState.EMPTY) {
      this.setBackground(this.baseColor);
    } else {
      this.setBackground(Color.BLUE);
      if (state == TileState.KNIGHT) {
        // get image
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("img/knight.png"));
        Image img = imgIcon.getImage();
        // resize image
        Image resizedImage = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(resizedImage);
        this.setIcon(icon);
      }
    }
  }
  public int getXPos() { return this.x; }
  public int getYPos() { return this.y; }

}
