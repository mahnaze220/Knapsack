package com.mobiquityinc.domain;

/**
 * An item (thing) can be added into a package. Each item has index number, weight and cost.
 *
 * @author Mahnaz
 */

public class Item {

	public static final Integer MAX_COST = 100;
	public static final Double MAX_WEIGHT = 100d;

	/*
	 * Item's index number
	 */
	private Integer index;

	/*
	 * Item's weight
	 */
	private Double weight;

	/*
	 * Item's cost
	 */
	private Integer cost;

	/**
	 * Creates an item instance by it's index number, weight and cost
	 * @param index
	 * @param weight
	 * @param cost
	 */
	public Item(Integer index, Double weight, Integer cost) {
		super();
		this.index = index;
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * Returns index number of item
	 * @return Integer
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Returns weight of item
	 * @return Double
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Returns cost of item
	 * @return BigDecimal
	 */
	public Integer getCost() {
		return cost;
	}

	/**
	 * Checks cost of the item is equal or less than the cost limit
	 * @return boolean
	 */
	public boolean isValid() {
		return getCost() <= MAX_COST && getWeight() <= MAX_WEIGHT;
	}
	
	/**
	 * Converts double value of weight to integer
	 * @return
	 */
	public Integer getIntegerValueOfWeight() {
		return (int)(weight * 100);
	}

	@Override
	public String toString() {
		return "Package [index=" + index + ", weight=" + weight + ", cost=" + cost + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (cost.compareTo(other.cost) < 0)
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

}
