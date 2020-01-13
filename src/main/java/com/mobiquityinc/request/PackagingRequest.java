package com.mobiquityinc.request;

import java.util.List;

import com.mobiquityinc.domain.Package;

/**
 * This class contains request of packaging. Each request has a input text file name.
 * Other information of this class will be filled during handling into different handlers for
 * transferring information between handlers (inside of Chain of responsibility pattern)  
 *
 * @author Mahnaz
 */

public class PackagingRequest {

	/*
	 * Input file name of available items and package's weight limit 
	 */
	private String inputFileName;
	
	/*
	 * Extracted data from input file
	 */
	private List<String> inputData;
	
	/*
	 * Converted input file's lines into Package objects
	 */
	private List<Package> packages;
	
	/*
	 * The best item selections (index numbers of items) to insert into packages
	 */
	private List<String> result; 
	
	/**
	 * Returns input file's name
	 * @return String
	 */
	public String getInputFileName() {
		return inputFileName;
	}
	
	/**
	 * Sets input file's name
	 * @param inputFileName
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	/**
	 * Returns created packages
	 * @return List<{@link Package}>
	 */
	public List<Package> getPackages() {
		return packages;
	}
	
	/**
	 * Sets packages
	 * @param packages
	 */
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
	/**
	 * Returns process result
	 * @return List<String>
	 */
	public List<String> getResult() {
		return result;
	}
	
	/**
	 * Sets process result
	 * @param List<String>
	 */
	public void setResult(List<String> result) {
		this.result = result;
	}

	/**
	 * Returns input data
	 * @return List<String>
	 */
	public List<String> getInputData() {
		return inputData;
	}

	/**
	 * Sets input data
	 * @param List<String>
	 */
	public void setInputData(List<String> inputData) {
		this.inputData = inputData;
	}
	
}
