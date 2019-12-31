package org.nepalehr.imisintegration.bed.scheduler.tasks;

import org.apache.log4j.Logger;
import org.nepalehr.imisintegration.bed.scheduler.service.IpdBedSalesQuotationService;
import org.openmrs.api.context.Context;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class IpdBedSaleQuotationTask extends AbstractTask {

	// @Autowired
	// IpdBedSalesQuotationService service;

	private static Logger logger = Logger.getLogger(IpdBedSaleQuotationTask.class);

	@Override
	public void execute() {
		logger.error("\n\n\n\n Inside execute. \n\n");
		// service.publishEvent();
		Context.getService(IpdBedSalesQuotationService.class).publishEvent();
	}
}
