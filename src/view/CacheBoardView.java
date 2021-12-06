package view;

import controller.CacheSimulatorSystem;
import model.CacheModel;
import model.EntryModel;
import model.StatusModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class CacheBoardView {
    private String m_boardName;
    private CacheSimulatorSystem m_cacheSimulatorSystem;

    private JPanel m_cache_board;
    private JTable m_table;

    private JPanel m_info;
    private JTextArea m_label;

    private JPanel m_status;
    private JTextArea m_ifHit;
    private JTextArea m_miss_count;
    private JTextArea m_miss_rate;

    Vector<String> Column;
    Vector<Vector<String>> Row;

    public CacheBoardView(String boardName, CacheSimulatorSystem cacheSimulatorSystem) {
        m_boardName = boardName;
        m_cacheSimulatorSystem = cacheSimulatorSystem;

        m_cache_board = new JPanel(new BorderLayout());

        Column = new Vector<>();
        Column.add("Index");
        Column.add("Way");
        Column.add("Tag");
        Column.add("Valid");

        int cache_size = m_cacheSimulatorSystem.getM_cache_size();
        int block_size = m_cacheSimulatorSystem.getM_block_size();
        int num_blocks = cache_size / block_size;
        Row = new Vector<>();

        for (int i = 0; i < num_blocks; i++) {
            Vector<String> tmp = new Vector<>();
            // index
            String tmp1 = Integer.toBinaryString(i / block_size);
            String tmp2 = "";
            int digit_index = m_cacheSimulatorSystem.getM_dataCache().getM_digit_index();
            if (tmp1.length() < digit_index) {
                for (int j = 0; j < digit_index - tmp1.length(); j++) {
                    tmp2 += "0";
                }
            }
            tmp.add(tmp2 + tmp1);

            // way
            tmp.add(Integer.toString(i % block_size + 1));

            // tag
            String prefix = "";
            for (int j = 0; j < m_cacheSimulatorSystem.getM_dataCache().getM_digit_tag(); j++) {
                prefix += "0";
            }
            tmp.add(prefix);

            // valid
            tmp.add("0");

            Row.add(tmp);
        }
        m_table = new JTable(Row, Column);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        m_table.setDefaultRenderer(Object.class, cr);
        m_table.getTableHeader().setDefaultRenderer(cr);

        m_info = new JPanel(new FlowLayout());
        m_label = new JTextArea(boardName);

        m_status = new JPanel(new BorderLayout());
        m_ifHit = new JTextArea("Miss/Hit");
        m_miss_count = new JTextArea("Miss Count: 0");
        m_miss_rate = new JTextArea("Miss Rate: 0");
        m_status.add(m_ifHit, BorderLayout.NORTH);
        m_status.add(m_miss_count, BorderLayout.CENTER);
        m_status.add(m_miss_rate, BorderLayout.SOUTH);

        m_info.add(m_label);
        m_info.add(m_status);

        m_cache_board.add(m_table.getTableHeader(), BorderLayout.NORTH);
        m_cache_board.add(m_table, BorderLayout.CENTER);
        m_cache_board.add(m_info, BorderLayout.SOUTH);
    }

    public void update() {
        StatusModel tmp_status;
        EntryModel[] entries;
        if (m_boardName == "Data Cache") {
            tmp_status = m_cacheSimulatorSystem.getM_data_status();
            entries = m_cacheSimulatorSystem.getM_dataCache().getM_entries();
        }
        else{
            tmp_status = m_cacheSimulatorSystem.getM_inst_status();
            entries = m_cacheSimulatorSystem.getM_instCache().getM_entries();
        }

        // hit
        m_ifHit.setText(tmp_status.getM_ifHit());

        // miss count
        m_miss_count.setText("Miss Count: " + tmp_status.getM_miss_count());

        // miss rate
        m_miss_rate.setText("Miss Rate: " + tmp_status.getM_miss_rate());

        // table
        int cache_size = m_cacheSimulatorSystem.getM_cache_size();
        int block_size = m_cacheSimulatorSystem.getM_block_size();
        int num_blocks = cache_size / block_size;

        for (int i = 0; i < num_blocks; i++) {
            // tag
            String tmp = Integer.toBinaryString(entries[i].getTag());
            int digit_tag = m_cacheSimulatorSystem.getM_dataCache().getM_digit_tag();
            if (tmp.length() < digit_tag) {
                String tmp2 = "";
                for (int j = 0; j < digit_tag - tmp.length(); j++) {
                    tmp2 += "0";
                }
                Row.get(i).set(2, tmp2 + tmp);
            }
            else {
                Row.get(i).set(2, tmp);
            }

            // valid
            Row.get(i).set(3, Integer.toBinaryString(entries[i].getValid()));
        }

        m_cache_board.updateUI();
    }

    public void reset() {
        m_ifHit.setText("Miss/Hit");
        m_miss_count.setText("Miss Count: 0");
        m_miss_rate.setText("Miss Rate: 0");

        int cache_size = m_cacheSimulatorSystem.getM_cache_size();
        int block_size = m_cacheSimulatorSystem.getM_block_size();
        int num_blocks = cache_size / block_size;

        Row.clear();

        for (int i = 0; i < num_blocks; i++) {
            Vector<String> tmp = new Vector<>();
            // index
            String tmp1 = Integer.toBinaryString(i / block_size);
            String tmp2 = "";
            int digit_index = m_cacheSimulatorSystem.getM_dataCache().getM_digit_index();
            if (tmp1.length() < digit_index) {
                for (int j = 0; j < digit_index - tmp1.length(); j++) {
                    tmp2 += "0";
                }
            }
            tmp.add(tmp2 + tmp1);

            // way
            tmp.add(Integer.toString(i % block_size + 1));

            // tag
            String prefix = "";
            for (int j = 0; j < m_cacheSimulatorSystem.getM_dataCache().getM_digit_tag(); j++) {
                prefix += "0";
            }
            tmp.add(prefix);

            // valid
            tmp.add("0");

            Row.add(tmp);
        }

        m_cache_board.updateUI();
    }

    public JPanel getM_cache_board() {
        return m_cache_board;
    }
}
