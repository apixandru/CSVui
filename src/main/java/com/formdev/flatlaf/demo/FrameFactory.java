package com.formdev.flatlaf.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameFactory {

    private static int numFrames = 0;

    public static DraggableFrame createMainFrame() {
        return createFrame(null);
    }

    public static DraggableFrame createFrame(Point location) {
        DraggableFrame frame = newFrame();
        frame.pack();
        if (location == null) {
            frame.setLocationRelativeTo(null);
        } else {
            frame.setLocation(location);
        }
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dispose(frame);
            }
        });
        frame.setVisible(true);
        return frame;
    }

    public static void dispose(JFrame frame) {
        if (frame != null) {
            frame.dispose();
            numFrames--;
        }
    }

    private static DraggableFrame newFrame() {
        numFrames++;
        return new DraggableFrame();
    }

    public static int getCurrentNumFrames() {
        return numFrames;
    }

}
