package com.knapsack.packer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.knapsack.exception.APIException;
import com.knapsack.packer.handler.PackageReader;
import com.knapsack.request.PackagingRequest;

/**
 * This unit test contains test cases for PackageReader's scenarios.
 *  
 * @author Mahnaz
 */
public class PackageReaderUT {

	@Test
	public void handleRequest_successScenario() throws APIException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test-packages.txt").getFile());
		String inputFileName = file.getAbsolutePath();
		List<String> expectedInputData = createExpectedInputData();
		PackageReader readerHandler = new PackageReader();
		PackagingRequest request = new PackagingRequest();
		request.setInputFileName(inputFileName);
		readerHandler.handleRequest(request); 
		Assertions.assertArrayEquals(expectedInputData.toArray(), request.getInputData().toArray());
	}

	@Test
	public void handleRequest_invalidFileFormat_throwException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("invalid-packages.txt").getFile());
			String inputFileName = file.getAbsolutePath();
			PackageReader readerHandler = new PackageReader();
			PackagingRequest request = new PackagingRequest();
			request.setInputFileName(inputFileName);
			readerHandler.handleRequest(request);
		}, "Input file's format is not valid");
	}

	public List<String> createExpectedInputData() {
		List<String> items = new ArrayList<>();
		items.add("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
		items.add("8 : (1,15.3,€34)");
		items.add("75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) "
				+ "(7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
		items.add("56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) "
				+ "(7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
		return items;
	}
}
