package model;

import java.beans.PropertyChangeSupport;

public class EntryModel {
    private int index;
    private int way;
    private int tag;
    private int valid;
    private int lastTime;

    public EntryModel(int index, int way, int lastTime) {
        this.index = index;
        this.way = way;
        tag = 0;
        valid = 0;
        this.lastTime = lastTime;
    }

    public void setTag(int tag, PropertyChangeSupport changes) {
        int oldTag = this.tag;
        this.tag = tag;
        changes.firePropertyChange("tag", oldTag, tag);
    }

    public void setValid(int valid, PropertyChangeSupport changes) {
        int oldValid = this.valid;
        this.valid = valid;
        changes.firePropertyChange("valid", oldValid, valid);
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getIndex() {
        return index;
    }

    public int getWay() {
        return way;
    }

    public int getTag() {
        return tag;
    }

    public int getValid() {
        return valid;
    }

    public int getLastTime() {
        return lastTime;
    }
}