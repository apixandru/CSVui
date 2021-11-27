/*
 * Copyright 2020 FormDev Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.formdev.flatlaf.demo;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.formdev.flatlaf.FlatClientProperties.*;

class TabsPanel2 extends JPanel {

    private JToolBar tabPlacementToolBar;
    private JToggleButton topPlacementButton;
    private JToggleButton bottomPlacementButton;
    private JToggleButton borderButton;
    private JTabbedPane tabPlacementTabbedPane;
    private JSeparator separator2;
    private JLabel scrollButtonsPolicyLabel;
    private JToolBar scrollButtonsPolicyToolBar;
    private JCheckBox showTabSeparatorsCheckBox;

    TabsPanel2() {
        initComponents();

        initClosableTabs(tabPlacementTabbedPane);

        customComponentsChanged();
    }

    private void addTab(JTabbedPane tabbedPane, String title, String text) {
        tabbedPane.addTab(title, createTab(text));
    }

    private JComponent createTab(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel tab = new JPanel(new BorderLayout());
        tab.add(label, BorderLayout.CENTER);
        return tab;
    }

    private void tabPlacementChanged() {
        int tabPlacement = JTabbedPane.TOP;
        if (bottomPlacementButton.isSelected()) {
            tabPlacement = JTabbedPane.BOTTOM;
        }

        tabPlacementTabbedPane.setTabPlacement(tabPlacement);
    }

    private void scrollChanged() {
        tabPlacementTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        int extraTabCount = 7;
        int tabCount = tabPlacementTabbedPane.getTabCount();
        for (int i = tabCount + 1; i <= tabCount + extraTabCount; i++) {
            addTab(tabPlacementTabbedPane, "Tab " + i, "tab content " + i);
        }
    }

    private void borderChanged() {
        Boolean hasFullBorder = borderButton.isSelected() ? true : null;
        tabPlacementTabbedPane.putClientProperty(TABBED_PANE_HAS_FULL_BORDER, hasFullBorder);
    }

    private void initClosableTabs(JTabbedPane tabbedPane) {
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSABLE, true);
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close");
        tabbedPane.putClientProperty(TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
                    AWTEvent e = EventQueue.getCurrentEvent();
                    int modifiers = (e instanceof MouseEvent) ? ((MouseEvent) e).getModifiers() : 0;
                    JOptionPane.showMessageDialog(this, "Closed tab '" + tabPane.getTitleAt(tabIndex) + "'."
                                    + "\n\n(modifiers: " + MouseEvent.getMouseModifiersText(modifiers) + ")",
                            "Tab Closed", JOptionPane.PLAIN_MESSAGE);
                });

        addDefaultTabsNoContent(tabbedPane, 3);
        tabbedPane.setEnabledAt(2, false);
    }

    private void customComponentsChanged() {
        JToolBar trailing = new JToolBar();
        trailing.setFloatable(false);
        trailing.setBorder(null);
        trailing.add(new JButton(new FlatSVGIcon("com/formdev/flatlaf/demo/icons/add.svg")));
        tabPlacementTabbedPane.putClientProperty(TABBED_PANE_TRAILING_COMPONENT, trailing);
    }

    private void addDefaultTabsNoContent(JTabbedPane tabbedPane, int count) {
        tabbedPane.addTab("Tab 1", createTab("tab content 1"));
        tabbedPane.addTab("Second Tab", createTab("tab content 2"));
        if (count >= 3) {
            JComponent tab2 = createTab("tab content 2");
            tab2.setBorder(new LineBorder(Color.magenta));
            tabbedPane.addTab("3rd Tab", tab2);
        }

        for (int i = 4; i <= count; i++) {
            tabbedPane.addTab("Tab " + i, null);
        }
    }

    private void closeButtonStyleChanged() {
        UIManager.put("TabbedPane.closeArc", 999);
        UIManager.put("TabbedPane.closeCrossFilledSize", 5.5f);
        UIManager.put("TabbedPane.closeIcon", new FlatTabbedPaneCloseIcon());
        tabPlacementTabbedPane.updateUI();
        UIManager.put("TabbedPane.closeArc", null);
        UIManager.put("TabbedPane.closeCrossFilledSize", null);
        UIManager.put("TabbedPane.closeIcon", null);
    }

    private void tabsPopupPolicyChanged() {
        putTabbedPanesClientProperty(TABBED_PANE_TABS_POPUP_POLICY, null);
    }

    private void scrollButtonsPlacementChanged() {
        putTabbedPanesClientProperty(TABBED_PANE_SCROLL_BUTTONS_PLACEMENT, TABBED_PANE_PLACEMENT_TRAILING);
    }

    private void showTabSeparatorsChanged() {
        Boolean showTabSeparators = showTabSeparatorsCheckBox.isSelected() ? true : null;
        putTabbedPanesClientProperty(TABBED_PANE_SHOW_TAB_SEPARATORS, showTabSeparators);
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
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JLabel tabPlacementLabel = new JLabel();
        tabPlacementToolBar = new JToolBar();
        topPlacementButton = new JToggleButton();
        bottomPlacementButton = new JToggleButton();
        borderButton = new JToggleButton();
        tabPlacementTabbedPane = new JTabbedPane();
        separator2 = new JSeparator();
        JPanel panel4 = new JPanel();
        scrollButtonsPolicyLabel = new JLabel();
        scrollButtonsPolicyToolBar = new JToolBar();
        showTabSeparatorsCheckBox = new JCheckBox();

        //======== this ========
        setName("this");
        setLayout(new MigLayout(
                "insets dialog,hidemode 3",
                // columns
                "[grow,fill]para" +
                        "[fill]para" +
                        "[fill]",
                // rows
                "[grow,fill]para" +
                        "[]" +
                        "[]"));

        //======== panel1 ========
        {
            panel1.setName("panel1");
            panel1.setLayout(new MigLayout(
                    "insets 0,hidemode 3",
                    // columns
                    "[grow,fill]",
                    // rows
                    "[]" +
                            "[fill]para" +
                            "[]0" +
                            "[]" +
                            "[]para" +
                            "[]" +
                            "[]para" +
                            "[]" +
                            "[]"));

            //---- tabPlacementLabel ----
            tabPlacementLabel.setText("Tab placement");
            tabPlacementLabel.setFont(tabPlacementLabel.getFont().deriveFont(tabPlacementLabel.getFont().getSize() + 4f));
            tabPlacementLabel.setName("tabPlacementLabel");
            panel1.add(tabPlacementLabel, "cell 0 0");

            //======== tabPlacementToolBar ========
            {
                tabPlacementToolBar.setFloatable(false);
                tabPlacementToolBar.setBorder(BorderFactory.createEmptyBorder());
                tabPlacementToolBar.setName("tabPlacementToolBar");

                //---- topPlacementButton ----
                topPlacementButton.setText("top");
                topPlacementButton.setSelected(true);
                topPlacementButton.setFont(topPlacementButton.getFont().deriveFont(topPlacementButton.getFont().getSize() - 2f));
                topPlacementButton.setName("topPlacementButton");
                topPlacementButton.addActionListener(e -> tabPlacementChanged());
                tabPlacementToolBar.add(topPlacementButton);

                //---- bottomPlacementButton ----
                bottomPlacementButton.setText("bottom");
                bottomPlacementButton.setFont(bottomPlacementButton.getFont().deriveFont(bottomPlacementButton.getFont().getSize() - 2f));
                bottomPlacementButton.setName("bottomPlacementButton");
                bottomPlacementButton.addActionListener(e -> tabPlacementChanged());
                tabPlacementToolBar.add(bottomPlacementButton);

                tabPlacementToolBar.addSeparator();

                scrollChanged();

                //---- borderButton ----
                borderButton.setText("border");
                borderButton.setFont(borderButton.getFont().deriveFont(borderButton.getFont().getSize() - 2f));
                borderButton.setName("borderButton");
                borderButton.addActionListener(e -> borderChanged());
                tabPlacementToolBar.add(borderButton);
            }
            panel1.add(tabPlacementToolBar, "cell 0 0,alignx right,growx 0");

            //======== tabPlacementTabbedPane ========
            {
                tabPlacementTabbedPane.setName("tabPlacementTabbedPane");
            }
            panel1.add(tabPlacementTabbedPane, "cell 0 1,width 300:300,height 100:100");

            closeButtonStyleChanged();
        }
        add(panel1, "cell 0 0");

        //---- separator2 ----
        separator2.setName("separator2");
        add(separator2, "cell 0 1 3 1");

        //======== panel4 ========
        {
            panel4.setName("panel4");
            panel4.setLayout(new MigLayout(
                    "insets 0,hidemode 3",
                    // columns
                    "[]" +
                            "[fill]para" +
                            "[fill]" +
                            "[fill]para",
                    // rows
                    "[]" +
                            "[center]"));

            //---- scrollButtonsPolicyLabel ----
            scrollButtonsPolicyLabel.setText("Scroll buttons policy:");
            scrollButtonsPolicyLabel.setName("scrollButtonsPolicyLabel");
            panel4.add(scrollButtonsPolicyLabel, "cell 0 0");

            //======== scrollButtonsPolicyToolBar ========
            {
                scrollButtonsPolicyToolBar.setFloatable(false);
                scrollButtonsPolicyToolBar.setBorder(BorderFactory.createEmptyBorder());
                scrollButtonsPolicyToolBar.setName("scrollButtonsPolicyToolBar");
            }
            panel4.add(scrollButtonsPolicyToolBar, "cell 1 0");

            //---- scrollButtonsPlacementLabel ----
            showTabSeparatorsCheckBox.setText("Show tab separators");
            showTabSeparatorsCheckBox.addActionListener(e -> showTabSeparatorsChanged());

            panel4.add(showTabSeparatorsCheckBox, "cell 2 0");

            scrollButtonsPlacementChanged();

            tabsPopupPolicyChanged();
        }
        add(panel4, "cell 0 2 3 1");

        //---- tabPlacementButtonGroup ----
        ButtonGroup tabPlacementButtonGroup = new ButtonGroup();
        tabPlacementButtonGroup.add(topPlacementButton);
        tabPlacementButtonGroup.add(bottomPlacementButton);

        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
