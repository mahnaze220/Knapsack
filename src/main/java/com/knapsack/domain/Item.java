package com.knapsack.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * An item (thing) can be added into a package. Each item has index number, weight and cost.
 *
 * @author Mahnaz
 */

public class Item {

	public static final BigDecimal MAX_COST = new BigDecimal(100);
	public static final BigDecimal MAX_WEIGHT = new BigDecimal(100);

	/*
	 * Item's index number
	 */
	private Integer index;

	/*
	 * Item's weight
	 */
	private BigDecimal weight;

	/*
	 * Item's cost
	 */
	private BigDecimal cost;

	/**
	 * Creates an item instance by it's index number, weight and cost
	 * @param index
	 * @param weight
	 * @param cost
	 */
	public Item(Integer index, BigDecimal weight, BigDecimal cost) {
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
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Returns cost of item
	 * @return BigDecimal
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * Checks cost of the item is equal or less than the cost limit
	 * @return boolean
	 */
	public boolean isValid() {
		return getCost().compareTo(MAX_COST) < 0 && getWeight().compareTo(MAX_WEIGHT) < 0;
	}

	/**
	 * Converts double value of weight to integer
	 * @return
	 */
	public Integer getIntegerValueOfWeight() {
		return weight.multiply(new BigDecimal(100)).intValue();
	}

	@Override
	public String toString() {
		return "Package [index=" + index + ", weight=" + weight + ", cost=" + cost + "]";
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		return new HashCodeBuilder(getIndex() %2 == 0 ? getIndex() + 1 : getIndex(), PRIME).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		return new EqualsBuilder()
				.append(getIndex(), other.getIndex())
				.append(getWeight(), other.getWeight())
				.append(getCost(), other.getCost())
				.isEquals();
	}
}
