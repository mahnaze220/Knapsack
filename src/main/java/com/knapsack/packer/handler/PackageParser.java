package com.mobiquityinc.packer.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.request.PackagingRequest;

/**
 * This class is one of the handlers in the defined chain of responsibility pattern.
 * Responsibility of this class is getting input data (packaging requests) from PackageReader handler 
 * then parsing them to create Item and Package objects.
 *
 * @author Mahnaz
 */
public class PackageParser extends PackageHandler {

	public static final String DOT = ":";

	/*
	 * Regular expression used to extract list of items from input data
	 */
	public static final String ITEMS_REGEX = "\\((.*?)\\)";

	/*
	 * Regular expression used to extract each item from input data
	 */
	public static final String ITEM_REGEX = "(-?\\d+(\\.\\d+)?)";

	/**
	 * Pars the input data by using of two regular expressions to create Item and Package objects
	 */
	@Override
	public boolean handleRequest(PackagingRequest request) throws APIException {

		List<Package> packageReuests = new ArrayList<>();
		for(String item: request.getInputData()) {

			//extracts weight limit of package
			Double maxWeight = extractWeightLimit(item);

			//extracts list of available items
			List<Item> items = extractItem(item);

			//creates Package object by it's weight limit and available items
			Package packageReuest = new Package(maxWeight, items);
			packageReuests.add(packageReuest);
		}

		//adds created package into packaging request to process by next handler
		request.setPackages(packageReuests);

		//call next handler
		return processNext(request);
	}

	/**
	 * Extracts list of available items from input data 
	 * @param inputString
	 * @return {@link Item}
	 * @throws APIException
	 */
	public List<Item> extractItem(String inputString) throws APIException {
		List<Item> availableItems = new ArrayList<>();	

		//list of items are placed after weight limit of requested package in the input string
		inputString = inputString.substring(inputString.indexOf(DOT) + 1 , inputString.length());

		Pattern pattern = Pattern.compile(ITEMS_REGEX);
		Matcher matcher = pattern.matcher(inputString);

		try {
			//extract list of items by the pattern
			while(matcher.find()) {
				String result = matcher.group();
				Pattern pat = Pattern.compile(ITEM_REGEX);
				Matcher match = pat.matcher(result);
				String[] items = new String[3];
				int i = 0;

				//extract each item by the pattern
				while(match.find()) {
					items[i] = match.group();
					i++;				 
				}

				//create item object by extracted data
				Item p = new Item(Integer.valueOf(items[0]), Double.valueOf(items[1]), 
						Integer.valueOf(items[2]));
				availableItems.add(p);
			}
			if(availableItems.isEmpty()) {
				throw new APIException("Format of input string is not valid");
			}
		}
		catch(Exception e) {
			throw new APIException("Format of input string is not valid", e);
		}
		return availableItems;
	}

	/**
	 * Extract weight limit of requested package
	 * @param inputString
	 * @return Integer
	 * @throws APIException
	 */
	public Double extractWeightLimit(String inputString) throws APIException {

		Double result = null;
		//first parameter of input string is weight limit of package 
		try {
			result = Double.valueOf(inputString.substring(0, inputString.indexOf(DOT)).trim());
			if(result == null) {
				throw new APIException("Format of input string is not valid");
			}
		}catch(Exception e) {
			throw new APIException("Format of input string is not valid");
		}
		return result;
	}
}
