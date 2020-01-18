package com.knapsack.packer;

import com.knapsack.exception.APIException;
import com.knapsack.packer.handler.PackageHandler;
import com.knapsack.packer.handler.PackageParser;
import com.knapsack.packer.handler.PackageProcessor;
import com.knapsack.packer.handler.PackageReader;
import com.knapsack.request.PackagingRequest;

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
