package com.checkdroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestSequence {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	private static final String SUMMARY_FILE_NAME = "resources/SummaryPassFail.csv";	//TODO: edit this
	private static final String SUMMARY_FILE_HEADER = "testSequence,passOrFail,noOfTests";
	
	private static final String BAR_CHART_DATA_FILE = "resources/BarChart.csv";	//TODO: edit this
	private static final String BAR_CHART_FILE_HEADER = "testSequence,passPercentage";
	
	private static final String OTHER_CHART_DATA_FILE = "resources/SummaryPassFail.csv";	//TODO: edit this
	private static final String OTHER_CSV_FILE_HEADER = "testSequence,passOrFail,noOfTests";
	int testSequenceName;
	int nTestsPassed;
	int nTestsFailed;
	int nTestsError;
	int nTests;
	ArrayList<TestCase> testCases;
	
	public TestSequence(String fileName){
		// Read from XML report
		try {
			ParseXML p1 = new ParseXML(fileName);			//TODO: edit this
			this.testCases = p1.getTestcases();
			int[] nPassFailError = p1.getNoOfPassFailErrorTests();
			this.nTestsPassed = nPassFailError[0];
			this.nTestsFailed = nPassFailError[1];
			this.nTestsError = nPassFailError[2];
			this.nTests = nPassFailError[3];
		} catch (Exception e1) {
			System.out.println("Error while reading the XML report !!!");
			e1.printStackTrace();
		}
		
		// Read last Test Sequence from Summary file (CSV)
		BufferedReader fileReader = null;
		String lastTestSequenceName = "";
		try{
			File summaryFile = new File(SUMMARY_FILE_NAME);
			if(!summaryFile.exists()) {
			    summaryFile.createNewFile();
			    // Write the SUMMARY_FILE_HEADER in the csv
			    BufferedWriter fileWriter = new BufferedWriter (new FileWriter (summaryFile));
			    fileWriter.write(SUMMARY_FILE_HEADER+NEW_LINE_SEPARATOR);
			    fileWriter.close();
			} 
			String line = "";
			//Create the file reader
			fileReader = new BufferedReader(new FileReader(SUMMARY_FILE_NAME));
			//Read the CSV file header to skip it
			fileReader.readLine();
			//Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				//Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					//Create a new student object and fill his  data
					lastTestSequenceName = tokens[0];
				}
			}
		}
		catch(Exception e){
			System.out.println("Error in constructor of TestSequence !!!");
			e.printStackTrace();
		}
		finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}

		if (lastTestSequenceName == ""){
			this.testSequenceName = 1;
		}
		else{
			this.testSequenceName = Integer.parseInt(lastTestSequenceName)+1;
		}
	}
	
	public void writeTestSequenceDatatoCSVs(){
		File barChartDataFile = new File(BAR_CHART_DATA_FILE);
		File otherChartDataFile = new File(OTHER_CHART_DATA_FILE);
		BufferedWriter fileWriter;
		try{
			// Writing in BarChart CSV file
			if(!barChartDataFile.exists()) {
				// Create CSV file
				barChartDataFile.createNewFile();
			    // Write the BAR_CHART_FILE_HEADER in the csv
			    fileWriter = new BufferedWriter (new FileWriter (barChartDataFile));
			    fileWriter.write(BAR_CHART_FILE_HEADER+NEW_LINE_SEPARATOR);
			}
			else{
				fileWriter = new BufferedWriter (new FileWriter (barChartDataFile, true));
			}
			String passPercentage = String.format("%.1f", (this.nTestsPassed*100.0)/this.nTests);
			fileWriter.write(Integer.toString(this.testSequenceName) + COMMA_DELIMITER + passPercentage + NEW_LINE_SEPARATOR);
			fileWriter.close();
			
			// Writing in OtherChartData CSV file which is the same as SUMMARY_FILE
			if(!otherChartDataFile.exists()) {
				// Create CSV file
				otherChartDataFile.createNewFile();
			    // Write the  in the csv
			    fileWriter = new BufferedWriter (new FileWriter (otherChartDataFile));
			    fileWriter.write(OTHER_CSV_FILE_HEADER+NEW_LINE_SEPARATOR);
			}
			else{
				fileWriter = new BufferedWriter (new FileWriter (otherChartDataFile, true));
			}
			fileWriter.write(Integer.toString(this.testSequenceName) + COMMA_DELIMITER + "Pass" + COMMA_DELIMITER + this.nTestsPassed + NEW_LINE_SEPARATOR);
			fileWriter.write(Integer.toString(this.testSequenceName) + COMMA_DELIMITER + "Fail" + COMMA_DELIMITER + this.nTestsFailed + NEW_LINE_SEPARATOR);
			fileWriter.close();
		}
		catch(Exception e){
			System.out.println("Error in writeTestSequenceDatatoCSVs !!!");
		}
	}
}
