package org.nepalehr.imisintegration.bed.scheduler.service.impl;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.ict4h.atomfeed.server.service.Event;
import org.joda.time.DateTime;
import org.nepalehr.imisintegration.bed.feed.FeedService;
import org.nepalehr.imisintegration.bed.scheduler.service.IpdBedSalesQuotationService;
import org.nepalehr.imisintegration.bed.service.BedService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpdBedSalesQuotationServiceImpl extends BaseOpenmrsService implements IpdBedSalesQuotationService {

	private static Logger logger = Logger.getLogger(IpdBedSalesQuotationServiceImpl.class);

	private static final String TEMPLATE = "/openmrs/ws/rest/v1/bedPatientAssignment/%s?v=custom:(uuid,startDatetime,endDatetime,bed,patient,encounter:(uuid,encounterDatetime,encounterType:(uuid,name),visit:(uuid,startDatetime,visitType)))";
	public static final String CATEGORY = "encounter";
	public static final String TITLE = "Bed-Assignment";

	private BedService bedService;

	private FeedService feedService;

	@Autowired
	public IpdBedSalesQuotationServiceImpl(BedService bedService, FeedService feedService) {
		this.bedService = bedService;
		this.feedService = feedService;
	}

	public List<Event> generateSalesQuotationForDailyBedCharge() {
		List<String> uuids = getActiveBedAssignment();
		for (String uuid : uuids) {
			logger.error("\n uuid->>" + uuid);
		}
		List<Event> events = uuids.stream().map(uuid -> createEvent(uuid)).collect(Collectors.toList());
		return events;
	}

	/**
	 * This method gets all active bed assignments
	 * 
	 * @return
	 */
	public List<String> getActiveBedAssignment() {
		return bedService.getActiveBedAssignmentUuids();
	}

	/**
	 * Creates event with a bed assignment uuid
	 * 
	 * @param bedAssignmentUuid
	 * @return
	 */
	public Event createEvent(String bedAssignmentUuid) {
		String contents = String.format(TEMPLATE, bedAssignmentUuid);
		Event event = new Event(UUID.randomUUID().toString(), TITLE, DateTime.now(), (URI) null, contents, CATEGORY);
		return event;
	}

	@Override
	public void publishEvent() {
		List<Event> events = generateSalesQuotationForDailyBedCharge();
		events.forEach(event -> feedService.publish(event));
	}

}
