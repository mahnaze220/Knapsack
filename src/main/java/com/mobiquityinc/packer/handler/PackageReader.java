package com.mobiquityinc.packer.handler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.request.PackagingRequest;

/**
 * This class is one of the handlers in the defined chain of responsibility pattern.
 * This class reads the input file and converts each line into a String.
 *
 * @author Mahnaz
 */

public class PackageReader extends PackageHandler {

	@Override
	public boolean handleRequest(PackagingRequest request) throws APIException {

		final List<String> inputData = new ArrayList<>();

		//read the input file line by line and add to a list
		try (Stream<String> stream = Files.lines(Paths.get(request.getInputFileName()))) {
			stream.forEach(i -> inputData.add(i));
		} catch (Exception e) {
			throw new APIException("Input file's format is not valid");
		}		
		
		//add extracted string into the packaging request to process by next handler 
		request.setInputData(inputData);
		
		//call next handler
		return processNext(request);
	}
}
