package com.formdev.flatlaf.demo;

import com.apixandru.csvui.main.DnDCloseButtonTabbedPane;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class DraggableFrame extends JFrame {

    public DraggableFrame() {
        // need to fix this circular dependency stuff
        setContentPane(new TabsPanel2(this));
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
