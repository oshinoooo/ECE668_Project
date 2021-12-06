package model;

import java.util.ArrayList;
import java.util.Random;

public class MainMemoryModel {
    private int m_main_memory_size;
    private int m_block_size;
    private ArrayList<Integer> m_data;
    private ArrayList<String> m_instruction;

    public MainMemoryModel(int main_memory_size, int block_size) {
        m_main_memory_size = main_memory_size;
        m_block_size = block_size;
        m_data = new ArrayList<>();
        m_instruction = new ArrayList<>();

        Random r = new Random();
        for (int i = 0; i < m_main_memory_size; i++) {
            m_data.add(r.nextInt(256));
        }

        m_instruction.add("l 1234");
        m_instruction.add("l 1234");
        m_instruction.add("l 1234");
        m_instruction.add("l 1234");
        m_instruction.add("l 1234");

        m_instruction.add("s 1234");
        m_instruction.add("s 1234");
        m_instruction.add("s 1234");
        m_instruction.add("s 1234");
        m_instruction.add("s 1234");
        m_instruction.add("beq 1234 5");
    }

    public ArrayList<Integer> loadData(int address) {
        ArrayList<Integer> data = new ArrayList<>();
        int block_address = address / m_block_size;

        for (int i = 0; i < m_block_size; ++i) {
            data.add(m_data.get(block_address * m_block_size + i));
        }

        return data;
    }

    public void showData() {
        for (int i = 0; i < m_data.size(); ++i)
        {
            if (i != 0 && i % 4 == 0)
                System.out.printf(" ");

            if (i != 0 && i % 16 == 0)
                System.out.println("");

            if (i % 4 == 0)
                System.out.printf("0x");
            System.out.printf("%02x", m_data.get(i));
        }
        System.out.println("");
    }
}
