package com.formdev.flatlaf.demo;

import com.apixandru.csvui.main.DnDCloseButtonTabbedPane;

import javax.swing.*;

public class DraggableFrame extends JFrame {

    public DraggableFrame() {
        // need to fix this circular dependency stuff
        setContentPane(new TabsPanel2(this));
        pack();
    }

    public DnDCloseButtonTabbedPane getTabbedPane() {
        TabsPanel2 contentPane = getContentPane();
        return contentPane.getTabPlacementTabbedPane();
    }

    @Override
    public TabsPanel2 getContentPane() {
        return (TabsPanel2) super.getContentPane();
    }


}
