package com.knapsack.packer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.knapsack.domain.Item;
import com.knapsack.domain.Package;
import com.knapsack.exception.APIException;
import com.knapsack.packer.handler.PackageProcessor;
import com.knapsack.request.PackagingRequest;

/**
 * This unit test contains test cases for PackageProcessor's scenarios.
 *  
 * @author Mahnaz
 */

public class PackageProcessorUT { 

	@Test
	public void processPackage_successScenario1() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(53.38) , new BigDecimal(45)));
		packages.add(new Item(2, new BigDecimal(88.62) , new BigDecimal(98)));
		packages.add(new Item(3, new BigDecimal(78.48) , new BigDecimal(3)));
		packages.add(new Item(4, new BigDecimal(72.30) , new BigDecimal(76)));
		packages.add(new Item(5, new BigDecimal(30.18) , new BigDecimal(9)));
		packages.add(new Item(6, new BigDecimal(46.34) , new BigDecimal(48)));
		int weightLimit = 81;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("4", result);
	}

	@Test
	public void processPackage_successScenario2() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(15.3) , new BigDecimal(34)));
		int weightLimit = 8;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("-", result);
	}

	@Test
	public void processPackage_successScenario3() throws APIException {		
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(85.31), new BigDecimal(29)));
		packages.add(new Item(2, new BigDecimal(14.55), new BigDecimal(74)));
		packages.add(new Item(3, new BigDecimal(3.98), new BigDecimal(16)));
		packages.add(new Item(4, new BigDecimal(26.24), new BigDecimal(55)));
		packages.add(new Item(5, new BigDecimal(63.69), new BigDecimal(52)));
		packages.add(new Item(6, new BigDecimal(76.25), new BigDecimal(75)));
		packages.add(new Item(7, new BigDecimal(60.02), new BigDecimal(74)));
		packages.add(new Item(8, new BigDecimal(93.18), new BigDecimal(35)));
		packages.add(new Item(9, new BigDecimal(89.95), new BigDecimal(78)));
		int weightLimit = 75;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("7,2", result);
	}

	@Test
	public void processPackage_successScenario4() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(90.72), new BigDecimal(13)));
		packages.add(new Item(2, new BigDecimal(33.80), new BigDecimal(40)));
		packages.add(new Item(3, new BigDecimal(43.15), new BigDecimal(10)));
		packages.add(new Item(4, new BigDecimal(37.97), new BigDecimal(16)));
		packages.add(new Item(5, new BigDecimal(46.81), new BigDecimal(36)));
		packages.add(new Item(6, new BigDecimal(48.77), new BigDecimal(79)));
		packages.add(new Item(7, new BigDecimal(81.80), new BigDecimal(45)));
		packages.add(new Item(8, new BigDecimal(19.36), new BigDecimal(79)));
		packages.add(new Item(9, new BigDecimal(6.76), new BigDecimal(64)));
		int weightLimit = 56;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("8,9", result);
	}

	@Test
	public void processPackage_totalWeightEqualWeightLimit_successScenario() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(50.0), new BigDecimal(13)));
		packages.add(new Item(2, new BigDecimal(6.0), new BigDecimal(45)));
		int weightLimit = 56;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("2,1", result);
	}

	@Test
	public void processPackage_invalidItemCost_throwAPIException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			List<Item> packages = new ArrayList<>();
			packages.add(new Item(1, new BigDecimal(15.0), new BigDecimal(10)));
			packages.add(new Item(2, new BigDecimal(6.0), new BigDecimal(130)));
			int weightLimit = 56;

			PackageProcessor packageProcessorHandler = new PackageProcessor();
			String result = packageProcessorHandler.processPackage(packages, weightLimit);
			Assertions.assertEquals("1", result);
		}, "Muximum weight and cost of item should be up to 100.0");
	}

	@Test
	public void processPackage_twoSubsetSameCostDifferentWeight_successful() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(15.0), new BigDecimal(45)));
		packages.add(new Item(2, new BigDecimal(6.0), new BigDecimal(45)));
		int weightLimit = 16;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		Assertions.assertEquals("2", result);
	}

	@Test
	public void handleRequest_successful() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, new BigDecimal(50.0), new BigDecimal(13)));
		packages.add(new Item(2, new BigDecimal(6.0), new BigDecimal(45)));
		Double weightLimit = 56d;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		PackagingRequest request = new PackagingRequest();
		Package totalPackage = new Package(weightLimit, packages);
		List<Package> totalPackages = new ArrayList<>();
		totalPackages.add(totalPackage);
		request.setPackages(totalPackages);
		packageProcessorHandler.handleRequest(request);
		List<String> expectedResult = new ArrayList();
		expectedResult.add("2,1");
		Assertions.assertArrayEquals(expectedResult.toArray(), request.getResult().toArray());
	}

	@Test
	public void handleRequest_invalidWeightLimit_throwAPIException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			List<Item> packages = new ArrayList<>();
			packages.add(new Item(1, new BigDecimal(90.72), new BigDecimal(13)));
			packages.add(new Item(2, new BigDecimal(33.80), new BigDecimal(40)));
			Double weightLimit = 110d;

			PackageProcessor packageProcessorHandler = new PackageProcessor();
			PackagingRequest request = new PackagingRequest();
			Package totalPackage = new Package(weightLimit, packages);
			List<Package> totalPackages = new ArrayList<>();
			totalPackages.add(totalPackage);
			request.setPackages(totalPackages);
			packageProcessorHandler.handleRequest(request);
		}, "Max weight that a package can take is <= 100.0");
	}

	@Test
	public void handleRequest_invalidNumberOfItems_throwAPIException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			List<Item> packages = new ArrayList<>();
			packages.add(new Item(1, new BigDecimal(90.72), new BigDecimal(13)));
			packages.add(new Item(2, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(3, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(4, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(5, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(6, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(7, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(8, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(9, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(10, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(11, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(12, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(13, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(14, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(15, new BigDecimal(33.80), new BigDecimal(40)));
			packages.add(new Item(16, new BigDecimal(33.80), new BigDecimal(40)));

			Double weightLimit = 100d;

			PackageProcessor packageProcessorHandler = new PackageProcessor();
			PackagingRequest request = new PackagingRequest();
			Package totalPackage = new Package(weightLimit, packages);
			List<Package> totalPackages = new ArrayList<>();
			totalPackages.add(totalPackage);
			request.setPackages(totalPackages);
			packageProcessorHandler.handleRequest(request);
		}, "Maximum number of items to choose should be up to 15");
	}
}
