package controller;

import model.CacheModel;
import model.MainMemoryModel;
import model.StatusModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CacheSimulatorSystem {
    private int m_PC;

    private int m_main_memory_size;
    private int m_block_size;
    private int m_cache_size;
    private int m_num_way;

    private PropertyChangeSupport m_changes;

    private StatusModel m_data_status;
    private StatusModel m_inst_status;

    private CacheModel m_data_cache;
    private CacheModel m_inst_cache;
    private MainMemoryModel m_main_memory;

    public CacheSimulatorSystem() {
        m_PC = 0;

        m_main_memory_size = 256;
        m_cache_size = 16;
        m_block_size = 2;
        m_num_way = 2;

        m_changes = new PropertyChangeSupport(this);

        m_data_status = new StatusModel(m_changes);
        m_inst_status = new StatusModel(m_changes);

        m_data_cache = new CacheModel(m_main_memory_size, m_cache_size, m_block_size, m_num_way, m_changes);
        m_inst_cache = new CacheModel(m_main_memory_size, m_cache_size, m_block_size, m_num_way, m_changes);
        m_main_memory = new MainMemoryModel(m_main_memory_size);
    }

    public void reset() {
        m_PC = 0;

        m_inst_status.reset();
        m_data_status.reset();

        CacheModel oldDataCache = m_data_cache;
        m_data_cache = new CacheModel(m_main_memory_size, m_cache_size, m_block_size, m_num_way, m_changes);

        CacheModel oldInstCache = m_inst_cache;
        m_inst_cache = new CacheModel(m_main_memory_size, m_cache_size, m_block_size, m_num_way, m_changes);

        m_changes.firePropertyChange("DataCache", oldDataCache, m_data_cache);
        m_changes.firePropertyChange("InstCache", oldInstCache, m_inst_cache);
    }

    public void run() {
        m_inst_status.setM_total_count(m_inst_status.getM_total_count() + 1);
        int currentPC = m_PC++;
        ArrayList<String> inst_data = m_inst_cache.searchCache(currentPC);

        if (!inst_data.isEmpty()) {
            m_inst_status.setM_ifHit("Hit");
        }
        else {
            m_inst_status.setM_ifHit("Miss");
            m_inst_status.setM_miss_count(m_inst_status.getM_miss_count() + 1);
            inst_data = m_main_memory.loadInst(currentPC, m_block_size);
            m_inst_cache.loadData(currentPC, inst_data);
        }
        double inst_miss_rate = (double)m_inst_status.getM_miss_count() / (double)m_inst_status.getM_total_count();
        m_inst_status.setM_miss_rate(inst_miss_rate);

        String current_instruction_type = inst_data.get(currentPC % m_block_size).substring(0, 3);
        if (current_instruction_type.equals("lod") || current_instruction_type.equals("str")) {
            m_data_status.setM_total_count(m_data_status.getM_total_count() + 1);
            int address = strToInt(inst_data.get(currentPC % m_block_size).substring(4, 7));
            ArrayList<String> data_data = m_data_cache.searchCache(address);

            if (!data_data.isEmpty()) {
                m_data_status.setM_ifHit("Hit");
            }
            else {
                m_data_status.setM_ifHit("Miss");
                m_data_status.setM_miss_count(m_data_status.getM_miss_count() + 1);
                data_data = m_main_memory.loadData(address, m_block_size);
                m_data_cache.loadData(address, data_data);
            }
            double data_miss_rate = (double)m_data_status.getM_miss_count() / (double)m_data_status.getM_total_count();
            m_data_status.setM_miss_rate(data_miss_rate);
        }
        else if (current_instruction_type.equals("bne")) {
            m_PC = strToInt(inst_data.get(currentPC % m_block_size).substring(4, 7));
        }
    }

    private int strToInt(String str) {
        int out = 0;
        for (int i = 0; i < str.length(); i++) {
            out *= 10;
            out += str.charAt(i) - '0';
        }
        return out;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        m_changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        m_changes.removePropertyChangeListener(listener);
    }

    public void setM_block_size(int m_block_size) {
        this.m_block_size = m_block_size;
    }

    public void setM_cache_size(int m_cache_size) {
        this.m_cache_size = m_cache_size;
    }

    public void setM_num_way(int m_num_way) {
        this.m_num_way = m_num_way;
    }

    public int getM_PC() {
        return m_PC;
    }

    public int getM_main_memory_size() {
        return m_main_memory_size;
    }

    public int getM_block_size() {
        return m_block_size;
    }

    public int getM_cache_size() {
        return m_cache_size;
    }

    public int getM_num_way() {
        return m_num_way;
    }

    public PropertyChangeSupport getM_changes() {
        return m_changes;
    }

    public StatusModel getM_data_status() {
        return m_data_status;
    }

    public StatusModel getM_inst_status() {
        return m_inst_status;
    }

    public CacheModel getM_data_cache() {
        return m_data_cache;
    }

    public CacheModel getM_inst_cache() {
        return m_inst_cache;
    }

    public MainMemoryModel getM_main_memory() {
        return m_main_memory;
    }
}
