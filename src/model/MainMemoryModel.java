package model;

import java.util.ArrayList;
import java.util.Random;

public class MainMemoryModel {
    private int m_main_memory_size;
    private ArrayList<String> m_data;
    private ArrayList<String> m_inst;

    public MainMemoryModel(int main_memory_size) {
        m_main_memory_size = main_memory_size;
        m_data = new ArrayList<>();
        m_inst = new ArrayList<>();

        Random r = new Random();
        for (int i = 0; i < m_main_memory_size; i++) {
            m_data.add(Integer.toBinaryString(r.nextInt(256)));
        }

        m_inst.add("lod 080");
        m_inst.add("lod 081");
        m_inst.add("add");
        m_inst.add("add");

        m_inst.add("sub");
        m_inst.add("lod 152");
        m_inst.add("lod 153");
        m_inst.add("lod 154");

        m_inst.add("sub");
        m_inst.add("str 085");
        m_inst.add("str 086");
        m_inst.add("div");

        m_inst.add("mul");
        m_inst.add("str 107");
        m_inst.add("str 108");
        m_inst.add("str 109");

        m_inst.add("add");
        m_inst.add("lod 220");
        m_inst.add("lod 221");
        m_inst.add("mul");

        m_inst.add("div");
        m_inst.add("mul");
        m_inst.add("lod 012");
        m_inst.add("lod 013");

        m_inst.add("div");
        m_inst.add("lod 014");
        m_inst.add("str 015");
        m_inst.add("add");

        m_inst.add("bne 000");
        m_inst.add("lod 000");
        m_inst.add("lod 000");
        m_inst.add("lod 000");
    }

    public ArrayList<String> loadData(int address, int block_size) {
        ArrayList<String> data = new ArrayList<>();
        int block_address = address / block_size;
        for (int i = 0; i < block_size; ++i) {
            data.add(m_data.get(block_address * block_size + i));
        }
        return data;
    }

    public ArrayList<String> loadInst(int address, int block_size) {
        ArrayList<String> inst = new ArrayList<>();
        int block_address = address / block_size;
        for (int i = 0; i < block_size; ++i) {
            inst.add(m_inst.get(block_address * block_size + i));
        }
        return inst;
    }
}
