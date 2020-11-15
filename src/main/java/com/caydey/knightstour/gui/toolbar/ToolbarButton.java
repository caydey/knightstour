package com.caydey.knightstour.gui.toolbar;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Insets;

public class ToolbarButton extends JButton {
  public ToolbarButton(String text) {
    super(text);
    this.setMargin(new Insets(2,2,2,2));
    this.setBackground(Color.WHITE);
  }
}
