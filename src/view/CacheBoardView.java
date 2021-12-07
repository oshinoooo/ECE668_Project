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

    private Font m_font;

    private JPanel m_cache_board;
    private JTable m_table;

    private JPanel m_info;

    // left
    private JPanel m_left_info;
    private JTextArea m_label;
    private JTextArea m_ifHit;

    // right
    private JPanel m_right_info;
    private JTextArea m_miss_count;
    private JTextArea m_total;
    private JTextArea m_miss_rate;

    Vector<String> m_column;
    Vector<Vector<String>> m_row;

    public CacheBoardView(String boardName, CacheSimulatorSystem cacheSimulatorSystem) {
        m_boardName = boardName;
        m_cacheSimulatorSystem = cacheSimulatorSystem;

        m_font = new Font("Menu.font", Font.PLAIN, 15);

        m_cache_board = new JPanel(new BorderLayout());

        m_column = new Vector<>();
        m_column.add("Index");
        m_column.add("Way");
        m_column.add("Tag");
        m_column.add("Valid");

        m_row = new Vector<>();

        initialize();

        m_table = new JTable(m_row, m_column);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        m_table.setDefaultRenderer(Object.class, cr);
        m_table.getTableHeader().setDefaultRenderer(cr);
        m_table.setFont(m_font);

        // info
        m_info = new JPanel(new FlowLayout());

        // left
        m_left_info = new JPanel(new BorderLayout());
        m_label = new JTextArea(boardName);
        m_ifHit = new JTextArea("Miss/Hit");
        m_label.setFont(m_font);
        m_ifHit.setFont(m_font);
        m_left_info.add(m_label, BorderLayout.NORTH);
        m_left_info.add(m_ifHit, BorderLayout.SOUTH);

        // right
        m_right_info = new JPanel(new BorderLayout());
        m_miss_count = new JTextArea("Miss Count: 0");
        m_miss_count.setFont(m_font);
        m_total      = new JTextArea("Total          : 0");
        m_total.setFont(m_font);
        m_miss_rate  = new JTextArea("Miss Rate   : 0.000");
        m_miss_rate.setFont(m_font);
        m_right_info.add(m_miss_count, BorderLayout.NORTH);
        m_right_info.add(m_total, BorderLayout.CENTER);
        m_right_info.add(m_miss_rate, BorderLayout.SOUTH);

        m_info.add(m_left_info);
        m_info.add(m_right_info);

        m_cache_board.add(m_table.getTableHeader(), BorderLayout.NORTH);
        m_cache_board.add(m_table, BorderLayout.CENTER);
        m_cache_board.add(m_info, BorderLayout.SOUTH);
    }

    public void update() {
        StatusModel tmp_status;
        EntryModel[] entries;
        if (m_boardName.equals("Data Cache")) {
            tmp_status = m_cacheSimulatorSystem.getM_data_status();
            entries = m_cacheSimulatorSystem.getM_data_cache().getM_entries();
        }
        else {
            tmp_status = m_cacheSimulatorSystem.getM_inst_status();
            entries = m_cacheSimulatorSystem.getM_inst_cache().getM_entries();
        }

        // hit
        m_ifHit.setText(tmp_status.getM_ifHit());

        // miss count
        m_miss_count.setText("Miss Count: " + tmp_status.getM_miss_count());

        // total
        m_total.setText("Total          : " + tmp_status.getM_total_count());

        // miss rate
        String miss_rate_str = Double.toString(tmp_status.getM_miss_rate());
        int length = miss_rate_str.length();
        if (5 < length) {
            miss_rate_str = miss_rate_str.substring(0, 5);
        }
        else if (length < 5) {
            for (int i = 0; i < 5 - length; i++) {
                miss_rate_str += "0";
            }
        }
        m_miss_rate.setText("Miss Rate   : " + miss_rate_str);

        // table
        int cache_size = m_cacheSimulatorSystem.getM_cache_size();
        int block_size = m_cacheSimulatorSystem.getM_block_size();
        int num_blocks = cache_size / block_size;

        for (int i = 0; i < num_blocks; i++) {
            // tag
            String tag = Integer.toBinaryString(entries[i].getTag());
            int digit_tag = m_cacheSimulatorSystem.getM_data_cache().getM_digit_tag();
            if (tag.length() < digit_tag) {
                String prefix = "";
                for (int j = 0; j < digit_tag - tag.length(); j++) {
                    prefix += "0";
                }
                tag = prefix + tag;
            }
            m_row.get(i).set(2, tag);

            // valid
            m_row.get(i).set(3, Integer.toBinaryString(entries[i].getValid()));
        }

        m_cache_board.updateUI();
    }

    public void reset() {
        m_row.clear();

        initialize();

        m_ifHit.setText("Miss/Hit");
        m_miss_count.setText("Miss Count: 0");
        m_total.setText("Total          : 0");
        m_miss_rate.setText("Miss Rate   : 0.000");

        m_cache_board.updateUI();
    }

    private void initialize() {
        int cache_size = m_cacheSimulatorSystem.getM_cache_size();
        int block_size = m_cacheSimulatorSystem.getM_block_size();
        int num_way = m_cacheSimulatorSystem.getM_num_way();
        int num_blocks = cache_size / block_size;

        for (int i = 0; i < num_blocks; i++) {
            Vector<String> entry = new Vector<>();

            // index
            String index = "";
            if (cache_size != block_size * num_way) {
                String prefix = "";
                index = Integer.toBinaryString(i / num_way);
                int digit_index = m_cacheSimulatorSystem.getM_data_cache().getM_digit_index();
                if (index.length() < digit_index) {
                    for (int j = 0; j < digit_index - index.length(); j++) {
                        prefix += "0";
                    }
                }
                index = prefix + index;
            }
            entry.add(index);

            // way
            entry.add(Integer.toString(i % num_way + 1));

            // tag
            String tag = "";
            for (int j = 0; j < m_cacheSimulatorSystem.getM_data_cache().getM_digit_tag(); j++) {
                tag += "0";
            }
            entry.add(tag);

            // valid
            entry.add("0");

            m_row.add(entry);
        }
    }

    public JPanel getM_cache_board() {
        return m_cache_board;
    }
}
