package view;

import controller.CacheSimulatorSystem;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CacheSimulatorView implements PropertyChangeListener {
    private CacheSimulatorSystem m_cacheSimulatorSystem;

    private JFrame m_gui;
    private OptionView m_optionView;
    private CacheBoardView m_instView;
    private CacheBoardView m_dataView;
    private NextButtonView m_nextButtonView;

    public CacheSimulatorView(CacheSimulatorSystem cacheSimulatorSystem) {
        m_cacheSimulatorSystem = cacheSimulatorSystem;
        JFrame.setDefaultLookAndFeelDecorated(true);

        m_gui = new JFrame("Cache Simulator");
        m_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_gui.setSize(new Dimension(650, 800));
        m_gui.setResizable(true);

        m_optionView = new OptionView(m_cacheSimulatorSystem);
        m_instView = new CacheBoardView("Inst Cache", m_cacheSimulatorSystem);
        m_dataView = new CacheBoardView("Data Cache", m_cacheSimulatorSystem);
        m_nextButtonView = new NextButtonView(m_cacheSimulatorSystem);

        m_gui.add(m_optionView.getOptionsPanel(), BorderLayout.NORTH);
        m_gui.add(m_instView.getM_cache_board(), BorderLayout.WEST);
        m_gui.add(m_dataView.getM_cache_board(), BorderLayout.EAST);
        m_gui.add(m_nextButtonView.getNextPanel(), BorderLayout.SOUTH);

        m_gui.setVisible(true);
    }

    public void update() {
        m_instView.update();
        m_dataView.update();
        m_nextButtonView.update();
    }

    public void reset() {
        m_instView.reset();
        m_dataView.reset();
        m_nextButtonView.reset();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "DataCache" || evt.getPropertyName() == "InstCache") {
            reset();
        }
        else {
            update();
        }
    }
}
