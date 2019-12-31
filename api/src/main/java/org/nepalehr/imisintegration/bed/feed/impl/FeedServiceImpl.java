package org.nepalehr.imisintegration.bed.feed.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsQueueJdbcImpl;
import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.server.service.EventServiceImpl;
import org.ict4h.atomfeed.transaction.AFTransactionWorkWithoutResult;
import org.nepalehr.imisintegration.bed.feed.FeedService;
import org.openmrs.api.context.Context;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class FeedServiceImpl implements FeedService {

	private static Logger logger = Logger.getLogger(FeedServiceImpl.class);

	private AtomFeedSpringTransactionManager atomFeedTransactionManager;

	private EventService eventService;
	private final Object eventServiceMonitor = new Object();
	private final Object txManagerMonitor = new Object();

	private EventService getEventService() {
		if (eventService == null) {
			synchronized (eventServiceMonitor) {
				this.eventService = this.eventService == null
						? new EventServiceImpl(new AllEventRecordsQueueJdbcImpl(this.atomFeedTransactionManager))
						: this.eventService;
			}
		}
		return this.eventService;
	}

	private AtomFeedSpringTransactionManager getAtomFeedTxManager() {
		if (this.atomFeedTransactionManager == null) {
			synchronized (txManagerMonitor) {
				this.atomFeedTransactionManager = this.atomFeedTransactionManager == null
						? new AtomFeedSpringTransactionManager(getSpringPlatformTransactionManager())
						: this.atomFeedTransactionManager;
			}
		}
		return this.atomFeedTransactionManager;
	}

	@Override
	public void publish(Event event) {
		getAtomFeedTxManager().executeWithTransaction(new AFTransactionWorkWithoutResult() {
			@Override
			protected void doInTransaction() {
				getEventService().notify(event);
			}

			@Override
			public PropagationDefinition getTxPropagationDefinition() {
				return PropagationDefinition.PROPAGATION_REQUIRED;
			}
		});
	}

	private PlatformTransactionManager getSpringPlatformTransactionManager() {
		List<PlatformTransactionManager> platformTransactionManagers = Context
				.getRegisteredComponents(PlatformTransactionManager.class);
		return platformTransactionManagers.get(0);
	}
}
