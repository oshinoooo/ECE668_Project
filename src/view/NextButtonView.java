package view;

import controller.CacheSimulatorSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextButtonView {
    CacheSimulatorSystem cacheSimulatorSystem;
    private JPanel nextPanel;
    private Font m_font;
    private JButton nextButton;
    private JTextArea currentPC;

    public NextButtonView(CacheSimulatorSystem cacheSimulatorSystem) {
        this.cacheSimulatorSystem = cacheSimulatorSystem;
        nextPanel = new JPanel(new FlowLayout());
        m_font = new Font("Menu.font", Font.PLAIN, 15);
        nextButton = new JButton("Execute next instruction");
        nextButton.setFont(m_font);
        currentPC = new JTextArea("Next PC: 0");
        currentPC.setFont(m_font);

        nextPanel.add(currentPC);
        nextPanel.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cacheSimulatorSystem.run();
            }
        });
    }

    public void update() {
        currentPC.setText("Next PC: " + cacheSimulatorSystem.getM_PC());
    }

    public void reset() {
        currentPC.setText("Next PC: 0");
    }

    public JPanel getNextPanel() {
        return nextPanel;
    }
}
