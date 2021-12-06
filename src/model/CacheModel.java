package model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CacheModel {
    private int m_main_memory_size;
    private int m_cache_size;
    private int m_block_size;
    private int m_num_way;

    private int m_num_blocks;
    private int m_num_sets;
    private int m_digit_bias;
    private int m_digit_block;
    private int m_digit_index;
    private int m_digit_tag;

    private PropertyChangeSupport m_changes;
    private EntryModel[] m_entries;

    public CacheModel(int main_memory_size, int cache_size, int block_size, int num_way, PropertyChangeSupport changes) {
        m_main_memory_size = main_memory_size;
        m_cache_size = cache_size;
        m_block_size = block_size;
        m_num_way    = num_way;
        m_changes    = changes;

        m_num_blocks  = m_cache_size / m_block_size;
        m_num_sets    = m_num_blocks / m_num_way;
        m_digit_bias  = (int)(Math.log(m_block_size) / Math.log(2));
        m_digit_block = (int)(Math.log(m_main_memory_size / m_block_size) / Math.log(2));
        m_digit_index = (int)(Math.log(m_num_sets) / Math.log(2));
        m_digit_tag   = m_digit_block - m_digit_index;

        m_entries = new EntryModel[m_num_blocks];
        for (int i = 0; i < m_num_blocks; i++) {
            m_entries[i] = new EntryModel(i / m_num_way, i % 2 + 1, i % 2 + 1);
        }
    }

    public boolean searchCache(int address) {
        int index_flag = 0;
        for (int i = 0; i < m_digit_index; i++) {
            index_flag <<= 1;
            index_flag += 1;
        }
        index_flag <<= m_digit_bias;

        int tag_flag = 0;
        for (int i = 0; i < m_digit_tag; i++) {
            tag_flag <<= 1;
            tag_flag += 1;
        }
        tag_flag <<= m_digit_index + m_digit_bias;

        int index = (address & index_flag) >> m_digit_bias;
        int tag   = (address & tag_flag) >> m_digit_index + m_digit_bias;

        for (int i = 0; i < m_num_blocks; i++) {
            if (m_entries[i].getIndex() == index && m_entries[i].getTag() == tag && m_entries[i].getValid() == 1) {
                int max_time = 0;
                for (int j = 0; j < m_num_blocks; j++) {
                    if (m_entries[j].getIndex() == index) {
                        max_time = Math.max(m_entries[j].getLastTime(), max_time);
                    }
                }
                m_entries[i].setLastTime(max_time + 1);
                return true;
            }
        }
        return false;
    }

    public void loadData(int address) {
        int index_flag = 0;
        for (int i = 0; i < m_digit_index; i++) {
            index_flag <<= 1;
            index_flag += 1;
        }
        index_flag <<= m_digit_bias;

        int tag_flag = 0;
        for (int i = 0; i < m_digit_tag; i++) {
            tag_flag <<= 1;
            tag_flag += 1;
        }
        tag_flag <<= m_digit_index + m_digit_bias;

        int index = (address & index_flag) >> m_digit_bias;
        int tag   = (address & tag_flag) >> m_digit_index + m_digit_bias;

        int min_index = 0;
        int max_time = 0;
        for (int i = 0; i < m_num_blocks; i++) {
            if (m_entries[i].getIndex() == index) {
                max_time = Math.max(m_entries[i].getLastTime(), max_time);
                if (m_entries[i].getLastTime() < m_entries[min_index].getLastTime()) {
                    min_index = i;
                }
            }
        }

        m_entries[min_index].setValid(1, m_changes);
        m_entries[min_index].setTag(tag, m_changes);
        m_entries[min_index].setLastTime(max_time + 1);
    }

    public int getM_digit_index() {
        return m_digit_index;
    }

    public int getM_digit_tag() {
        return m_digit_tag;
    }

    public EntryModel[] getM_entries() {
        return m_entries;
    }
}
