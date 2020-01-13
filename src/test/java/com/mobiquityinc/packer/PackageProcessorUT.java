package com.mobiquityinc.packer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.handler.PackageProcessor;
import com.mobiquityinc.request.PackagingRequest;

/**
 * This unit test contains test cases for PackageProcessor's scenarios.
 *  
 * @author Mahnaz
 */

public class PackageProcessorUT { 

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void processPackage_successScenario1() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 53.38 , 45));
		packages.add(new Item(2, 88.62 , 98));
		packages.add(new Item(3, 78.48 , 3));
		packages.add(new Item(4, 72.30 , 76));
		packages.add(new Item(5, 30.18 , 9));
		packages.add(new Item(6, 46.34 , 48));
		int weightLimit = 81;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("4", result);
	}

	@Test
	public void processPackage_successScenario2() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 15.3 , 34));
		int weightLimit = 8;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("-", result);
	}

	@Test
	public void processPackage_successScenario3() throws APIException {		
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 85.31, 29));
		packages.add(new Item(2, 14.55, 74));
		packages.add(new Item(3, 3.98, 16));
		packages.add(new Item(4, 26.24, 55));
		packages.add(new Item(5, 63.69, 52));
		packages.add(new Item(6, 76.25, 75));
		packages.add(new Item(7, 60.02, 74));
		packages.add(new Item(8, 93.18, 35));
		packages.add(new Item(9, 89.95, 78));
		int weightLimit = 75;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("7,2", result);
	}

	@Test
	public void processPackage_successScenario4() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 90.72, 13));
		packages.add(new Item(2, 33.80, 40));
		packages.add(new Item(3, 43.15, 10));
		packages.add(new Item(4, 37.97, 16));
		packages.add(new Item(5, 46.81, 36));
		packages.add(new Item(6, 48.77, 79));
		packages.add(new Item(7, 81.80, 45));
		packages.add(new Item(8, 19.36, 79));
		packages.add(new Item(9, 6.76, 64));
		int weightLimit = 56;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("8,9", result);
	}

	@Test
	public void processPackage_totalWeightEqualWeightLimit_successScenario() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 50.0, 13));
		packages.add(new Item(2, 6.0, 45));
		int weightLimit = 56;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("2,1", result);
	}

	@Test
	public void processPackage_invalidItemCost_throwAPIException() throws APIException {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("Muximum weight and cost of item should be up to 100.0");
		
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 15.0, 10));
		packages.add(new Item(2, 6.0, 130));
		int weightLimit = 56;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("1", result);
	}

	@Test
	public void processPackage_twoSubsetSameCostDifferentWeight_successful() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 15.0, 45));
		packages.add(new Item(2, 6.0, 45));
		int weightLimit = 16;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		String result = packageProcessorHandler.processPackage(packages, weightLimit);
		assertEquals("2", result);
	}

	@Test
	public void handleRequest_successful() throws APIException {
		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 50.0, 13));
		packages.add(new Item(2, 6.0, 45));
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
		assertArrayEquals(expectedResult.toArray(), request.getResult().toArray());
	}

	@Test
	public void handleRequest_invalidWeightLimit_throwAPIException() throws APIException {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("Max weight that a package can take is <= 100.0");

		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 90.72, 13));
		packages.add(new Item(2, 33.80, 40));
		Double weightLimit = 110d;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		PackagingRequest request = new PackagingRequest();
		Package totalPackage = new Package(weightLimit, packages);
		List<Package> totalPackages = new ArrayList<>();
		totalPackages.add(totalPackage);
		request.setPackages(totalPackages);
		packageProcessorHandler.handleRequest(request);
	}

	@Test
	public void handleRequest_invalidNumberOfItems_throwAPIException() throws APIException {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("Maximum number of items to choose should be up to 15");

		List<Item> packages = new ArrayList<>();
		packages.add(new Item(1, 90.72, 13));
		packages.add(new Item(2, 33.80, 40));
		packages.add(new Item(3, 33.80, 40));
		packages.add(new Item(4, 33.80, 40));
		packages.add(new Item(5, 33.80, 40));
		packages.add(new Item(6, 33.80, 40));
		packages.add(new Item(7, 33.80, 40));
		packages.add(new Item(8, 33.80, 40));
		packages.add(new Item(9, 33.80, 40));
		packages.add(new Item(10, 33.80, 40));
		packages.add(new Item(11, 33.80, 40));
		packages.add(new Item(12, 33.80, 40));
		packages.add(new Item(13, 33.80, 40));
		packages.add(new Item(14, 33.80, 40));
		packages.add(new Item(15, 33.80, 40));
		packages.add(new Item(16, 33.80, 40));

		Double weightLimit = 100d;

		PackageProcessor packageProcessorHandler = new PackageProcessor();
		PackagingRequest request = new PackagingRequest();
		Package totalPackage = new Package(weightLimit, packages);
		List<Package> totalPackages = new ArrayList<>();
		totalPackages.add(totalPackage);
		request.setPackages(totalPackages);
		packageProcessorHandler.handleRequest(request);
	}
}
