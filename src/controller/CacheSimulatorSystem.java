package controller;

import model.CacheModel;
import model.MainMemoryModel;
import model.StatusModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
        int tmp = m_PC++;

        m_data_status.setM_total_count(m_data_status.getM_total_count() + 1);
        if (!m_data_cache.searchCache(tmp)) {
            m_data_status.setM_ifHit("Miss");
            m_data_status.setM_miss_count(m_data_status.getM_miss_count() + 1);
            m_data_cache.loadData(tmp);
        }
        else {
            m_data_status.setM_ifHit("Hit");
        }
        double tmp_miss_rate = (double)m_data_status.getM_miss_count() / (double)m_data_status.getM_total_count();
        m_data_status.setM_miss_rate(tmp_miss_rate);
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

    public CacheModel getM_dataCache() {
        return m_data_cache;
    }

    public CacheModel getM_instCache() {
        return m_inst_cache;
    }

    public StatusModel getM_data_status() {
        return m_data_status;
    }

    public StatusModel getM_inst_status() {
        return m_inst_status;
    }
}
