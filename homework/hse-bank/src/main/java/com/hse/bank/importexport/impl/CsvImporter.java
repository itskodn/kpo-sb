package com.hse.bank.importexport.impl;

import com.hse.bank.importexport.DataImporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvImporter extends DataImporter {
    @Override
    protected String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    @Override
    protected List<Object> parseData(String content) {
        System.out.println("Parsing CSV data...");
        return List.of();
    }

    @Override
    protected void saveData(List<Object> data) {
        System.out.println("Saving imported CSV data...");
    }
}