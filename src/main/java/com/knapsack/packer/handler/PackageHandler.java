package com.mobiquityinc.packer.handler;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.request.PackagingRequest;

/**
 * This abstract class implements "Chain of responsibility" design pattern.
 * It has three handlers consists of a handler for reading the input file, a handler for parsing the input data
 * and a handler for processing the items to insert them into a package.
 *
 * @author Mahnaz
 */

public abstract class PackageHandler {

	private PackageHandler next;

	/**
	 * Builds chains of package handler objects.
	 * @param next
	 * @return {@link PackageHandler}
	 */
	public PackageHandler linkWith(PackageHandler next) {
		this.next = next;
		return next;
	}

	/**
	 * Each chain object implements this method with concrete process.
	 * @param {@link PackagingRequest}
	 * @return boolean
	 * @throws APIException
	 */
	public abstract boolean handleRequest(PackagingRequest request) throws APIException;

	/**
	 * Runs handleRequest on the next object in the chain or ends traversing when the
	 * last object in the chain is met.
	 * @param {@link PackagingRequest}
	 * @return boolean
	 * @throws APIException
	 */
	protected boolean processNext(PackagingRequest request) throws APIException {
		if (next == null) {
			return true;
		}
		return next.handleRequest(request);
	}

}
