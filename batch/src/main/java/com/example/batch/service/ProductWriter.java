package com.example.batch.service;

import com.example.batch.model.Product;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.sql.*;

import java.util.List;

public class ProductWriter implements ItemWriter<Product>, StepExecutionListener {
    static final String USER = "sa";
    static final String PASS = "";
    static final String DB_URL = "jdbc:mysql://localhost:3306/satest";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    Connection conn;

    public ProductWriter() throws Exception {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void write(List<? extends Product> list) throws Exception {
        for (Product p : list) {
            System.out.println(p);
            Statement stmt = conn.createStatement();
            StringBuilder categoryStr = new StringBuilder();
            for (String category : p.getCategory()) {
                categoryStr.append(category + ",");
            }
            if (categoryStr.length() == 0)
                continue;
            categoryStr.deleteCharAt(categoryStr.length() - 1);
            StringBuilder imageURLHighResStr = new StringBuilder();
            for (String imageURLHighRes : p.getImageURLHighRes()) {
                imageURLHighResStr.append(imageURLHighRes + ",");
            }
            if (imageURLHighResStr.length() == 0)
                continue;
            imageURLHighResStr.deleteCharAt(imageURLHighResStr.length() - 1);

            try {
                stmt.executeUpdate(
                        "INSERT IGNORE INTO amazon (main_cat, title, asin, category, imageURLHighRes) VALUES "
                                +
                                String.format("('%s', '%s', '%s', '%s', '%s');", p.getAsin().replaceAll("\'", "\\\\'"),
                                        p.getTitle().replaceAll("\'", "\\\\'"), p.getAsin().replaceAll("\'", "\\\\'"),
                                        categoryStr.toString().replaceAll("\'", "\\\\'"),
                                        imageURLHighResStr.toString().replaceAll("\'", "\\\\'")));
            } catch (Exception e) {
            }

        }
        System.out.println("chunk written");
    }
}
