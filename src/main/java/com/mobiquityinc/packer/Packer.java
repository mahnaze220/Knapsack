package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.handler.PackageHandler;
import com.mobiquityinc.packer.handler.PackageParser;
import com.mobiquityinc.packer.handler.PackageProcessor;
import com.mobiquityinc.packer.handler.PackageReader;
import com.mobiquityinc.request.PackagingRequest;

public class Packer {

	private Packer() {
	}
 
	public static String pack(String filePath) throws APIException { 
		
		//creates chain of responsibility pattern by three handlers (reader, parser and processor)
		PackageHandler packageHandler = new PackageReader();
		packageHandler.linkWith(new PackageParser())
		.linkWith(new PackageProcessor()); 
		
		PackagingRequest request = new PackagingRequest();
		
		//set input file (consist of available items and package's weight limit) into the request
		request.setInputFileName(filePath);
		packageHandler.handleRequest(request);
		
		//get the best items selection (index number of items separated by ',' ) to fill the package 
		return request.getResult().toString();
	}
}
