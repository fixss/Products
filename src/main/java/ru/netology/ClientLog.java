package ru.netology;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    private List<String[]> logs = new ArrayList<>();

    public void log(int productNum, int amount) {
        this.logs.add((Integer.toString(productNum) + "," + Integer.toString(amount)).split(","));
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true), ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {
            String[] wr = "productNum ,amount".split(",");
            writer.writeNext(wr);
            for (String[] l : logs) {
                writer.writeNext(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

