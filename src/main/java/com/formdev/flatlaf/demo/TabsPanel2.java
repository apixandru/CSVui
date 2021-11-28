package com.formdev.flatlaf.demo;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.formdev.flatlaf.FlatClientProperties.*;

class TabsPanel2 extends JPanel {

    int numTabs = 0;

    private JTabbedPane tabPlacementTabbedPane;

    TabsPanel2() {
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.red);
        initComponents();
        tabPlacementTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        initClosableTabs(tabPlacementTabbedPane);

        addNewTabButton();
    }

    private void addTab(JTabbedPane tabbedPane, String title, String text) {
        tabbedPane.addTab(title, createTab(text));
    }

    private JComponent createTab(String text) {
        numTabs++;
        if (text == null) {
            text = "Tab #" + numTabs;
        }
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel tab = new JPanel(new BorderLayout());
        tab.add(label, BorderLayout.CENTER);
        return tab;
    }

    private void initClosableTabs(JTabbedPane tabbedPane) {
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSABLE, true);
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close");
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> tabbedPane.removeTabAt(tabIndex));
    }

    private void addNewTabButton() {
        JToolBar trailing = new JToolBar();
        trailing.setFloatable(false);
        trailing.setBorder(null);
        JButton button = new JButton(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/add.svg"));
        button.addActionListener(e -> {
            int tabCount = tabPlacementTabbedPane.getTabCount();
            int uniqueId = numTabs + 1;
            addTab(tabPlacementTabbedPane, "Tab " + uniqueId, "tab content " + uniqueId);
            tabPlacementTabbedPane.setSelectedIndex(tabCount);
        });
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
        tabPlacementTabbedPane = new JTabbedPane();

        tabPlacementTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabPlacementTabbedPane, BorderLayout.CENTER);

        setTabCloseIcons();
        putTabbedPanesClientProperty(TABBED_PANE_SHOW_TAB_SEPARATORS, true);
        putTabbedPanesClientProperty(TABBED_PANE_SCROLL_BUTTONS_PLACEMENT, TABBED_PANE_PLACEMENT_TRAILING);
        putTabbedPanesClientProperty(TABBED_PANE_TABS_POPUP_POLICY, null);
    }

}
