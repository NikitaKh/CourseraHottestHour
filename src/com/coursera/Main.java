package com.coursera;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

class maxTemp{
    public CSVRecord hottestHourInFile(CSVParser parser) {
        CSVRecord largestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if (largestSoFar == null) {
                largestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                if (currentTemp > largestTemp) {
                    largestSoFar = currentRow;
                }
            }
        }
        return largestSoFar;
    }

    public CSVRecord hottestDayInManyFiles(){
        CSVRecord largestSoFar = null;
        DirectoryResource dir = new DirectoryResource();
        for(File f : dir.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord current = hottestHourInFile(fr.getCSVParser());
            if (largestSoFar == null){
                largestSoFar = current;
            } else {
                double currentTemp = Double.parseDouble(current.get("TemperatureF"));
                double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                if (currentTemp > largestTemp){
                    largestSoFar = current;
                }
            }
        }
        return largestSoFar;
    }
    public void testHottestInDay() {
        CSVRecord largest = hottestDayInManyFiles();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                " at " + largest.get("DateUTC"));
    }
}

public class Main {
    public static void main(String[] args) {
        // write your code here
        maxTemp temp = new maxTemp();
        temp.testHottestInDay();
    }
}