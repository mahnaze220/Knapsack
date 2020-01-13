package com.mobiquityinc.domain;

import java.util.List;

/**
 * Each package contains some items and has a weight limit.
 *
 * @author Mahnaz
 */

public class Package {

	/*
	 * Weight limit of packages
	 */
	public static final Double WEIGHT_LIMIT = 100d;

	/*
	 * Allowed maximum number of limits
	 */
	public static final Integer ITEMS_COUNT_LIMIT = 15;

	/*
	 * Weight limit of the package
	 */
	private Double weightLimit;

	/*
	 * List of included items
	 */
	private List<Item> items;

	/**
	 * Creates an instance of a package by it's weight limit and included items
	 * @param maxWeight
	 * @param packages
	 */
	public Package(Double maxWeight, List<Item> packages) {
		super();
		this.weightLimit = maxWeight;
		this.items = packages;
	}

	/**
	 * Checks weight of the package is equal or less than the weight limit
	 * @return boolean
	 */
	public boolean hasValidWeight() {
		return getWeightLimit() <= WEIGHT_LIMIT;
	}

	/**
	 * Checks number of items of the package is equal or less than the maximum value
	 * @return boolean
	 */
	public boolean hasValidItems() {
		return getItems().size() <= ITEMS_COUNT_LIMIT;
	}

	/**
	 * Returns package's weight limit
	 * @return Integer
	 */
	public Double getWeightLimit() {
		return weightLimit;
	}

	/**
	 * Returns inserted items into the package 
	 * @return List<Item>
	 */
	public List<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "Package [weightLimit=" + weightLimit + ", items=" + items + "]";
	}
}
