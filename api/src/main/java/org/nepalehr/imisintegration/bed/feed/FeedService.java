package org.nepalehr.imisintegration.bed.feed;

import org.ict4h.atomfeed.server.service.Event;

public interface FeedService {

	/**
	 * Service to publish Events
	 * 
	 * @param event
	 */
	void publish(Event event);
}
