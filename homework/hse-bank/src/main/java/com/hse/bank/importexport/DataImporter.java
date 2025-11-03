package com.hse.bank.importexport;

import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Category;
import com.hse.bank.domain.Operation;

import java.util.List;

public abstract class DataImporter {
    public final void importData(String filePath) {
        String content = readFile(filePath);
        List<Object> data = parseData(content);
        validateData(data);
        saveData(data);
    }

    protected abstract String readFile(String filePath);
    protected abstract List<Object> parseData(String content);

    protected void validateData(List<Object> data) {
        for (Object item : data) {
            if (!(item instanceof BankAccount) &&
                    !(item instanceof Category) &&
                    !(item instanceof Operation)) {
                throw new IllegalArgumentException("Invalid data type in import file");
            }
        }
    }

    protected abstract void saveData(List<Object> data);
}