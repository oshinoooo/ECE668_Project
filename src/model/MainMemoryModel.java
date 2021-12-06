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

        m_inst.add("lod 000");
        m_inst.add("lod 001");
        m_inst.add("lod 002");
        m_inst.add("lod 003");
        m_inst.add("lod 004");
        m_inst.add("str 005");
        m_inst.add("str 006");
        m_inst.add("str 007");
        m_inst.add("str 008");
        m_inst.add("str 009");
        m_inst.add("bne 000");
        m_inst.add("lod 010");
        m_inst.add("lod 011");
        m_inst.add("lod 012");
        m_inst.add("lod 013");
        m_inst.add("lod 014");
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
