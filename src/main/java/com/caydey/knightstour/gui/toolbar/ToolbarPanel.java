package com.caydey.knightstour.gui.toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToolbarPanel extends JPanel {
  private ToolbarButton undoBtn;
  private ToolbarButton restartBtn;
  private static ToolbarButton showMovesBtn; // static so the text can be changed by other classes without having to pass around initialised instance
  private static JLabel completedLbl;
  private JLabel fillLbl;

  private GridBagConstraints c;

  // Listener
  private ToolbarListener toolbarListener;
  public void setToolbarListener(ToolbarListener toolbarListener) {
    this.toolbarListener = toolbarListener;
  }

  public ToolbarPanel() {
    this.setLayout(new GridBagLayout());
    c = new GridBagConstraints();

    this.setBackground(new Color(235,235,255)); // light gray

    c.gridx = 0;
    undoBtn = new ToolbarButton("Undo");
    undoBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        toolbarListener.toolbarAction(ToolbarActions.UNDO);
      }
    });
    this.add(undoBtn, c);

    c.gridx = 1;
    restartBtn = new ToolbarButton("Restart");
    restartBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        toolbarListener.toolbarAction(ToolbarActions.RESTART);
      }
    });
    this.add(restartBtn, c);

    c.gridx = 2;
    showMovesBtn = new ToolbarButton("Show Moves");
    showMovesBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        toolbarListener.toolbarAction(ToolbarActions.SHOW_MOVES);
      }
    });
    this.add(showMovesBtn, c);

    c.gridx = 3;
    c.insets = new Insets(2,2,2,2);
    completedLbl = new JLabel();
    this.setCompleted(1);
    completedLbl.setBackground(Color.RED);
    this.add(completedLbl, c);

    // force buttons to left
    c.gridx = 4;
    c.weightx = 1;
    fillLbl = new JLabel();
    this.add(fillLbl, c);
  }

  public static void setShowMoves(boolean showMoves) {
    String newBtnText = (showMoves) ? "Hide Moves" : "Show Moves";
    showMovesBtn.setText(newBtnText);
  }
  public static void setCompleted(int completed) {
    String newLblText = String.format("Completed % 2d/64", completed);
    completedLbl.setText(newLblText);
  }
}
