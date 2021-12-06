package view;

import controller.CacheSimulatorSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionView {
    private CacheSimulatorSystem cacheSimulatorSystem;
    private JPanel optionsPanel;

    private JLabel cacheSizeLabel;
    private JComboBox cacheSizeCombo;

    private JLabel blockSizeLabel;
    private JComboBox blockSizeCombo;

    private JLabel waySizeLabel;
    private JComboBox waySizeCombo;

    public OptionView(CacheSimulatorSystem cacheSimulatorSystem) {
        this.cacheSimulatorSystem = cacheSimulatorSystem;
        optionsPanel = new JPanel(new FlowLayout());
        optionsPanel.setPreferredSize(new Dimension(500, 100));

        cacheSizeLabel = new JLabel("Cache Size:");
        int[] int_cacheSize = {16, 32};
        String[] cacheSize = {"16", "32"};
        cacheSizeCombo = new JComboBox(cacheSize);
        cacheSizeCombo.setSelectedItem("16");
        cacheSizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cacheSimulatorSystem.setM_cache_size(int_cacheSize[cacheSizeCombo.getSelectedIndex()]);
                cacheSimulatorSystem.reset();
            }
        });
        optionsPanel.add(cacheSizeLabel);
        optionsPanel.add(cacheSizeCombo);

        blockSizeLabel = new JLabel("Block Size:");
        int[] int_blockSize = {1, 2, 4};
        String[] blockSize = {"1", "2", "4"};
        blockSizeCombo = new JComboBox(blockSize);
        blockSizeCombo.setSelectedItem("2");
        blockSizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cacheSimulatorSystem.setM_block_size(int_blockSize[blockSizeCombo.getSelectedIndex()]);
                cacheSimulatorSystem.reset();
            }
        });
        optionsPanel.add(blockSizeLabel);
        optionsPanel.add(blockSizeCombo);

        waySizeLabel = new JLabel("Way Size:");
        int[] int_waySize = {1, 2, 4, 8, 16, 32};
        String[] waySize = {"1", "2", "4", "8", "16", "32"};
        waySizeCombo = new JComboBox(waySize);
        waySizeCombo.setSelectedItem("2");
        waySizeCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cacheSimulatorSystem.setM_num_way(int_waySize[waySizeCombo.getSelectedIndex()]);
                cacheSimulatorSystem.reset();
            }
        });
        optionsPanel.add(waySizeLabel);
        optionsPanel.add(waySizeCombo);
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }
}
