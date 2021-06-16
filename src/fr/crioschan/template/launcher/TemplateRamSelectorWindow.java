package fr.crioschan.template.launcher;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class TemplateRamSelectorWindow extends AbstractOptionFrame implements ActionListener {
    private JLabel ramLabel;
    private JComboBox ramBox;
    private JButton ok = new JButton("OK");

    public TemplateRamSelectorWindow(RamSelector selector) {
        super(selector);
        this.setTitle(LanguageManager.lang(new String[]{"options"}));
        this.setResizable(false);
        this.setSize(275, 100);
        this.setLocationRelativeTo((Component)null);
        this.setLayout((LayoutManager)null);
        this.setUndecorated(true);
        this.setBackground(Color.white);
        this.ramLabel = new JLabel(LanguageManager.lang(new String[]{"ram"}) + " : ");
        this.ramLabel.setBounds(15, 20, 45, 25);
        this.add(this.ramLabel);
        this.ramBox = new JComboBox(RamSelector.RAM_ARRAY);
        this.ramBox.setBounds(65, 20, 195, 25);
        this.add(this.ramBox);

        ok.setBounds(0, 75, 275, 25);
        ok.addActionListener(this);
        this.add(ok);

    }

    public int getSelectedIndex() {
        return this.ramBox.getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        this.ramBox.setSelectedIndex(index);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getSelector().save();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
