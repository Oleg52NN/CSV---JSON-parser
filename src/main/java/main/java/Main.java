package main.java;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        //System.out.println(list.toString());
        writeString(listToJson(list));
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        CsvToBean<Employee> csv = new CsvToBean<Employee>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            List<Employee> list = csv.parse(setColumMapping(columnMapping), csvReader);
            for (Object object : list) {
                Employee employee = (Employee) object;
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String listToJson(List<Employee> employeeList) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(employeeList, listType);
        //System.out.println(json);
        return json;
    }

    public static void writeString(String json) {
        try (FileWriter fileWriter = new FileWriter(new File("data.json"))) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ColumnPositionMappingStrategy<Employee> setColumMapping(String[] columnMapping) {
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);
        return strategy;
    }
}


