package org.nepalehr.imisintegration.bed.scheduler.service;

public interface IpdBedSalesQuotationService {

	/**
	 * This service publishes sale quotation events for all active beds.
	 */
	void publishEvent();
}
