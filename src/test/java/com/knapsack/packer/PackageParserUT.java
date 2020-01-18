package com.knapsack.packer;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.knapsack.domain.Item;
import com.knapsack.exception.APIException;
import com.knapsack.packer.handler.PackageParser;

/**
 * This unit test contains test cases for PackageParser's scenarios.
 *  
 * @author Mahnaz
 */
public class PackageParserUT {

	@Test
	public void extractItem_successScenario() throws APIException {
		PackageParser packageParser = new PackageParser();
		List<Item> items = packageParser.extractItem(createInputString());
		Assertions.assertArrayEquals(createExpectedItems().toArray(), items.toArray());
	}

	@Test
	public void extractItem_invalidInputStringFormat_throwAPIException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			PackageParser packageParser = new PackageParser();
			packageParser.extractItem(createInvalidInputString());
		}, "Format of input string is not valid");
	}

	@Test
	public void extractMaxWeight_successScenario() throws APIException {
		String inputItem = "81 : (1,53.38,€45)";
		PackageParser packageParser = new PackageParser();
		Double weightLimit = packageParser.extractWeightLimit(inputItem);
		Assertions.assertEquals(new Double(81), weightLimit);
	}

	@Test
	public void extractMaxWeight_invalidInputStringFormat_throwAPIException() throws APIException {
		Assertions.assertThrows(APIException.class, () -> {
			String inputItem = "81 - (1,53.38,€45)";
			PackageParser packageParser = new PackageParser();
			packageParser.extractWeightLimit(inputItem);
		}, "Format of input string is not valid");
	}

	public List<Item> createExpectedItems() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1, new BigDecimal(53.38).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(45)));		
		items.add(new Item(2, new BigDecimal(88.62).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(98)));
		items.add(new Item(3, new BigDecimal(78.48).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(3)));
		items.add(new Item(4, new BigDecimal(72.30).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(76)));
		items.add(new Item(5, new BigDecimal(30.18).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(9)));
		items.add(new Item(6, new BigDecimal(46.34).round(new MathContext(4, RoundingMode.HALF_UP)) , new BigDecimal(48)));
		return items;
	}

	public String createInputString() {
		return "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
	}

	public String createInvalidInputString() {
		return "81 : (153.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
	}
}
