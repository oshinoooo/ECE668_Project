package model;

import java.beans.PropertyChangeSupport;

public class StatusModel {
    private PropertyChangeSupport m_changes;
    private String m_ifHit;
    private int m_total_count;
    private int m_miss_count;
    private double m_miss_rate;

    public StatusModel(PropertyChangeSupport changes) {
        m_changes = changes;
        m_ifHit = "Miss/Hit";
        m_total_count = 0;
        m_miss_count = 0;
        m_miss_rate = 0;
    }

    public void reset() {
        m_ifHit = "Miss/Hit";
        m_total_count = 0;
        m_miss_count = 0;
        m_miss_rate = 0;
    }

    public String getM_ifHit() {
        return m_ifHit;
    }

    public int getM_total_count() {
        return m_total_count;
    }

    public int getM_miss_count() {
        return m_miss_count;
    }

    public double getM_miss_rate() {
        return m_miss_rate;
    }

    public void setM_ifHit(String m_ifHit) {
        String old = this.m_ifHit;
        this.m_ifHit = m_ifHit;
        m_changes.firePropertyChange("m_ifHit", old, m_ifHit);
    }

    public void setM_total_count(int m_total_count) {
        int old = this.m_total_count;
        this.m_total_count = m_total_count;
        m_changes.firePropertyChange("m_total_count", old, m_total_count);
    }

    public void setM_miss_count(int m_miss_count) {
        int old = this.m_miss_count;
        this.m_miss_count = m_miss_count;
        m_changes.firePropertyChange("m_miss_count", old, m_miss_count);
    }

    public void setM_miss_rate(double m_miss_rate) {
        double old = this.m_miss_rate;
        this.m_miss_rate = m_miss_rate;
        m_changes.firePropertyChange("m_miss_rate", old, m_miss_rate);
    }
}
