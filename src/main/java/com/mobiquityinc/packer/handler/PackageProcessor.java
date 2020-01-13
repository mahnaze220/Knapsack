package com.mobiquityinc.packer.handler;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.request.PackagingRequest;

/**
 * This class is one of the handlers in the defined chain of responsibility pattern.
 * This class gets list of available items and also weight limit of a package to put items
 * into the package so that the total weight is less than or equal to the package limit 
 * and the total cost is as large as possible. It uses the dynamic programming algorithm to find best selections.  
 *
 * @author Mahnaz
 */

public class PackageProcessor extends PackageHandler {

	public static final Double WEIGHT_LIMIT = 100d; 
	public static final Integer ITEMS_COUNT_LIMIT = 15;
	public static final String COMMA = ",";

	/**
	 * Processes the package request by available items and package's weight limit
	 */
	@Override
	public boolean handleRequest(PackagingRequest request) throws APIException {  
		List<String> result = new ArrayList<>(); 
		for(Package pkg: request.getPackages()) {

			//validate package request
			validateRequest(pkg);

			//process request
			result.add(processPackage(pkg.getItems(), pkg.getWeightLimit()));
		}

		//set result into package request
		request.setResult(result);

		//go to next handler
		return processNext(request);
	}

	/**
	 * Processes each package request to fill the package so that the total weight 
	 * is less than or equal to the package limit and the total cost is as large as possible.
	 * @param {@link List<Item>}
	 * @param weightLimit
	 * @return String
	 * @throws APIException 
	 */
	public String processPackage(List<Item> items, double weightLimit) throws APIException {

		List<Item> bestSelectionList;
		String bestItems = "";

		//validate each item to check if its weight and cost are less than defined limitation. 
		for(Item item: items) {
			if(!item.isValid()) {
				throw new APIException("Muximum weight and cost of item should be up to " + WEIGHT_LIMIT);
			}
		}

		//find the best selection of items to put into the package
		bestSelectionList = findBestItemSelections(items, weightLimit);

		if(bestSelectionList != null) {
			//create response as a string of selected items' index numbers separated by ','
			for(int i = 0; i < bestSelectionList.size(); i++) {
				bestItems = new StringBuilder(bestItems)
						.append(bestSelectionList.get(i).getIndex())
						.append(COMMA)
						.toString();
			}

			if(!bestItems.isEmpty() && bestItems.lastIndexOf(COMMA) == bestItems.length() -1) {
				bestItems = bestItems.substring(0, bestItems.lastIndexOf(COMMA)).trim();
			}
		}
		return !bestItems.equals("") ? bestItems : "-";
	}


	/**
	 * Process all possible options (item selections) to find the maximum cost 
	 * and fill the package based on defined the criteria by the dynamic programming algorithm.
	 * @param items
	 * @param weightLimit
	 * @return
	 */
	public List<Item> findBestItemSelections(List<Item> items, Double weightLimit) {

		// convert weight limit value and also double value of weight for each item 
		// to an integer to use in the dynamic algorithm.
		int capacity = (int) (weightLimit * 100);

		int numberOfItems = items.size();

		// create a matrix to put the max cost at each n-th item
		double[][] costMatrix = new double[numberOfItems + 1][capacity + 1];

		// initiate first row to zero
		for (int i = 0; i <= capacity; i++) {
			costMatrix[0][i] = 0;
		}

		// iterate on items to fill matrix
		for (int i = 1; i <= numberOfItems; i++) {

			// iterate on each capacity
			for (int j = 0; j <= capacity; j++) {

				// get integer value of weight
				if (items.get(i - 1).getIntegerValueOfWeight() > j)
					costMatrix[i][j] = costMatrix[i-1][j];
				else
					costMatrix[i][j] = Math.max(costMatrix[i-1][j], 
							costMatrix[i-1][(j - items.get(i-1).getIntegerValueOfWeight())] + items.get(i-1).getCost());
			}
		}

		double result = costMatrix[numberOfItems][capacity];
		int w = capacity;
		List<Item> selectedItems = new ArrayList<>();

		for (int i = numberOfItems; i > 0  &&  result > 0; i--) {
			if (result != costMatrix[i-1][w]) {
				selectedItems.add(items.get(i-1));

				// remove items value and weight
				result -= items.get(i-1).getCost();
				w -= items.get(i-1).getIntegerValueOfWeight();
			}
		}
		return selectedItems;
	}	

	/**
	 * Checks weight and number of available items according defined limits 
	 * @param {@link Package}
	 * @throws APIException
	 */
	public void validateRequest(Package pkg) throws APIException {
		if(!pkg.hasValidWeight()) {
			throw new APIException("Max weight that a package can take is <= " + WEIGHT_LIMIT);					
		}
		if(!pkg.hasValidItems()) {
			throw new APIException("Maximum number of items to choose should be up to " + ITEMS_COUNT_LIMIT);
		}
	}
}
