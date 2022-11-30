/*
 * Copyright © 11/30/2022, Pexers (https://github.com/Pexers)
 */
 
package view;

import controller.Layer;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private final Layer layerToDraw;

    public Panel(Layer layerToDraw) {
        this.layerToDraw = layerToDraw;
        buildPanel();
    }

    private JFrame buildPanel() {
        JFrame frame = new JFrame("view.Panel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        return frame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        layerToDraw.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Space.SQUARE_SIZE * Space.PANEL_SIZE, Space.SQUARE_SIZE * Space.PANEL_SIZE);
    }
}
