package com.example.batch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

public class JsonReader implements StepExecutionListener, ItemReader<JsonNode> {

    private String fileName;

    private BufferedReader reader;

    private ObjectMapper objectMapper;

    public JsonReader(String file) {
        objectMapper = new ObjectMapper();
        if (file.matches("^file:(.*)"))
            file = file.substring(file.indexOf("data/"));
        this.fileName = file;
    }

    private void initReader() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public JsonNode read() throws Exception {
        if (reader == null) {
            initReader();
        }
        String line = reader.readLine();
        if (line != null)
            return objectMapper.readTree(line);
        else
            return null;
    }
}
