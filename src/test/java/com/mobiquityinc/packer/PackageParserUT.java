package com.mobiquityinc.packer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.handler.PackageParser;

/**
 * This unit test contains test cases for PackageParser's scenarios.
 *  
 * @author Mahnaz
 */
public class PackageParserUT {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void extractItem_successScenario() throws APIException {
		PackageParser packageParser = new PackageParser();
		List<Item> items = packageParser.extractItem(createInputString());
		assertArrayEquals(createExpectedItems().toArray(), items.toArray());
	}
	
	@Test
	public void extractItem_invalidInputStringFormat_throwAPIException() throws APIException {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("Format of input string is not valid");
		
		PackageParser packageParser = new PackageParser();
		packageParser.extractItem(createInvalidInputString());
	}
	
	@Test
	public void extractMaxWeight_successScenario() throws APIException {
		String inputItem = "81 : (1,53.38,€45)";
		PackageParser packageParser = new PackageParser();
		Double weightLimit = packageParser.extractWeightLimit(inputItem);
		assertEquals(new Double(81), weightLimit);
	}
	
	@Test
	public void extractMaxWeight_invalidInputStringFormat_throwAPIException() throws APIException {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("Format of input string is not valid");
		
		String inputItem = "81 - (1,53.38,€45)";
		PackageParser packageParser = new PackageParser();
		packageParser.extractWeightLimit(inputItem);
	}
	
	public List<Item> createExpectedItems() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1, 53.38 , 45));
		items.add(new Item(2, 88.62 , 98));
		items.add(new Item(3, 78.48 , 3));
		items.add(new Item(4, 72.30 , 76));
		items.add(new Item(5, 30.18 , 9));
		items.add(new Item(6, 46.34 , 48));
		return items;
	}
	
	public String createInputString() {
		return "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
	}
	
	public String createInvalidInputString() {
		return "81 : (153.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
	}
}
