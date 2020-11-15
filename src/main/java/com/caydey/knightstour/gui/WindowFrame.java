package knightstour.gui;

import javax.swing.*;

import java.awt.*;

import knightstour.model.*;
import knightstour.gui.board.*;
import knightstour.gui.toolbar.*;

public class WindowFrame extends JFrame {

  private BoardPanel panel;
  private ToolbarPanel toolbar;
  private ActionHandler actionHandler;

  private GridBagConstraints c;

  public WindowFrame() {
    super("Knights Tour");

    Board board = new Board();
    Knight knight = new Knight();
    TileState[][] tiles = board.getTiles();

    actionHandler = new ActionHandler(this, tiles, knight);

    this.setLayout(new GridBagLayout());
    c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    // icon
    ImageIcon icon = new ImageIcon(getClass().getResource("board/img/knight.png"));
    this.setIconImage(icon.getImage());

    // toolbar
    c.gridy = 0;
    this.toolbar = new ToolbarPanel();
    this.toolbar.setToolbarListener(new ToolbarListener() {
      public void toolbarAction(ToolbarActions action) {
        actionHandler.toolbarPressed(action);
      }
    });
    this.add(this.toolbar, c);

    // board
    c.gridy = 1;
    this.panel = new BoardPanel(tiles);
    this.panel.setTileListener(new TileListener() {
      public void tileAction(int x, int y) {
        actionHandler.tilePressed(x,y);
      }
    });
    this.add(this.panel, c);

    this.setSize(340,391);
    this.setMinimumSize(new Dimension(340,391));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
