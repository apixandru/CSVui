package com.apixandru.csvui.main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;
import java.util.Arrays;

public final class DndUtils {

    private DndUtils() {
    }

    public static <T> T getTransferData(DropTargetDropEvent event, DataFlavor flavor) {
        return getTransferData(event.getTransferable(), flavor);
    }

    public static <T> T getTransferData(DropTargetDragEvent event, DataFlavor flavor) {
        return getTransferData(event.getTransferable(), flavor);
    }


    public static <T> T getTransferData(DragSourceDragEvent event, DataFlavor flavor) {
        return getTransferData(event.getDragSourceContext(), flavor);
    }

    public static <T> T getTransferData(DragSourceDropEvent event, DataFlavor flavor) {
        return getTransferData(event.getDragSourceContext(), flavor);
    }

    public static <T> T getTransferData(DragSourceContext dragSourceContext, DataFlavor flavor) {
        return getTransferData(dragSourceContext.getTransferable(), flavor);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getTransferData(Transferable transferable, DataFlavor flavor) {
        if (!transferable.isDataFlavorSupported(flavor)) {
            return null;
        }
        try {
            return (T) transferable.getTransferData(flavor);
        } catch (UnsupportedFlavorException | IOException e) {
            DataFlavor[] availableFlavors = transferable.getTransferDataFlavors();
            throw new IllegalArgumentException("Cannot get " + flavor + " transferable from " + Arrays.toString(availableFlavors));
        }
    }

}
