package com.apixandru.csvui.main;

import com.apixandru.csvui.main.DnDCloseButtonTabbedPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.formdev.flatlaf.FlatClientProperties.*;

public class TabsPanel2 extends JPanel {

    private final JFrame frame;

    int numTabs = 0;

    private DnDCloseButtonTabbedPane tabPlacementTabbedPane;

    public TabsPanel2(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(640, 480));
        initComponents();
        tabPlacementTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        initClosableTabs(tabPlacementTabbedPane);

        addNewTabButton();
    }

    public void newTab() {
        numTabs++;
        addTab(tabPlacementTabbedPane, "New tab " + numTabs, "Placeholder " + numTabs);
    }

    private void addTab(JTabbedPane tabbedPane, String title, String text) {
        SwingUtilities.invokeLater(() -> {
            tabbedPane.addTab(title, "New tab 1".equals(title) ? new DataComponentsPanel() : createTab(text));
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        });
    }

    private JComponent createTab(String text) {
        if (text == null) {
            text = "Tab #" + numTabs;
        }
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel tab = new JPanel(new BorderLayout());
        tab.add(label, BorderLayout.CENTER);
        return tab;
    }

    private void initClosableTabs(DnDCloseButtonTabbedPane tabbedPane) {
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSABLE, true);
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close");
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    tabbedPane.removeTabAt(tabIndex);
                    if (tabbedPane.disposeMaybe()) {
                        newTab();
                    }
                    repaint();
                });
    }

    private void addNewTabButton() {
        JToolBar trailing = new JToolBar();
        trailing.setFloatable(false);
        trailing.setBorder(null);
        JButton button = new JButton(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/add.svg"));
        button.addActionListener(e -> newTab());
        trailing.add(button);
        tabPlacementTabbedPane.putClientProperty(TABBED_PANE_TRAILING_COMPONENT, trailing);
    }

    private void setTabCloseIcons() {
        UIManager.put("TabbedPane.closeArc", 999);
        UIManager.put("TabbedPane.closeCrossFilledSize", 5.5f);
        UIManager.put("TabbedPane.closeIcon", new FlatTabbedPaneCloseIcon());
        tabPlacementTabbedPane.updateUI();
        UIManager.put("TabbedPane.closeArc", null);
        UIManager.put("TabbedPane.closeCrossFilledSize", null);
        UIManager.put("TabbedPane.closeIcon", null);
    }

    private void putTabbedPanesClientProperty(String key, Object value) {
        updateTabbedPanesRecur(this, tabbedPane -> tabbedPane.putClientProperty(key, value));
    }

    private void updateTabbedPanesRecur(Container container, Consumer<JTabbedPane> action) {
        for (Component c : container.getComponents()) {
            if (c instanceof JTabbedPane) {
                JTabbedPane tabPane = (JTabbedPane) c;
                action.accept(tabPane);
            }

            if (c instanceof Container) {
                updateTabbedPanesRecur((Container) c, action);
            }
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        tabPlacementTabbedPane = new DnDCloseButtonTabbedPane(frame);

        tabPlacementTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabPlacementTabbedPane, BorderLayout.CENTER);

        setTabCloseIcons();
        putTabbedPanesClientProperty(TABBED_PANE_SHOW_TAB_SEPARATORS, true);
        putTabbedPanesClientProperty(TABBED_PANE_SCROLL_BUTTONS_PLACEMENT, TABBED_PANE_PLACEMENT_TRAILING);
        putTabbedPanesClientProperty(TABBED_PANE_TABS_POPUP_POLICY, null);
    }

    public DnDCloseButtonTabbedPane getTabPlacementTabbedPane() {
        return tabPlacementTabbedPane;
    }

}
