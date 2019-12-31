package org.nepalehr.imisintegration.bed.atomfeed;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.ict4h.atomfeed.server.repository.AllEventRecordsQueue;
import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsQueueJdbcImpl;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.server.service.EventServiceImpl;
import org.openmrs.Concept;
import org.openmrs.ConceptAttribute;
import org.openmrs.ConceptAttributeType;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;
import org.openmrs.module.bedmanagement.entity.BedType;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.transaction.PlatformTransactionManager;

public class BedManagementAdvice implements AfterReturningAdvice {
	private static Logger logger = Logger.getLogger(BedManagementAdvice.class);

	private final String BED_TYPE_SAVE = "saveBedType";
	private final String BED_TYPE_DELETE = "deleteBedType";
	private final String SALEABLE_CONECPT_ATTRIBUTE = "saleable";
	private final String BED_CONCEPT_CLASS = "Misc";
	private final String BED_CONCEPT_DATATYPE = "N/A";
	private AtomFeedSpringTransactionManager atomFeedSpringTransactionManager;
	private EventService eventService;

	private ConceptService conceptService;

	public BedManagementAdvice() {
		atomFeedSpringTransactionManager = createTransactionManager();
		this.eventService = createService(atomFeedSpringTransactionManager);
		this.conceptService = Context.getService(ConceptService.class);
	}

	public BedManagementAdvice(AtomFeedSpringTransactionManager atomFeedSpringTransactionManager,
			EventService eventService) {
		this.atomFeedSpringTransactionManager = atomFeedSpringTransactionManager;
		this.eventService = eventService;
	}

	private AtomFeedSpringTransactionManager createTransactionManager() {
		PlatformTransactionManager platformTransactionManager = getSpringPlatformTransactionManager();
		return new AtomFeedSpringTransactionManager(platformTransactionManager);
	}

	private EventServiceImpl createService(AtomFeedSpringTransactionManager atomFeedSpringTransactionManager) {
		AllEventRecordsQueue allEventRecordsQueue = new AllEventRecordsQueueJdbcImpl(atomFeedSpringTransactionManager);
		return new EventServiceImpl(allEventRecordsQueue);
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object bedService)
			throws Throwable {

		// Generate Bed Setup Events if Bed Type save/update
		if (!BED_TYPE_SAVE.equals(method.getName()) && !BED_TYPE_DELETE.equals(method.getName())) {
			return;
		}

		BedType bedType = returnValue == null ? (BedType) arguments[0] : (BedType) returnValue;

		if (BED_TYPE_DELETE.equals(method.getName())) {
			deleteConcept(bedType);
			return;
		}

		Concept bedTypeConcept = mapConcept(bedType);
		conceptService.saveConcept(bedTypeConcept);

	}

	private Concept getConcept(BedType bedType) {
		Concept concept = conceptService.getConceptByUuid(bedType.getUuid());
		return concept;
	}

	private void deleteConcept(BedType bedType) {
		logger.error("\n\n Inside deleteConcept \n\n\n");
		Concept concept = getConcept(bedType);

		conceptService.purgeConcept(concept);
	}

	private Concept mapConcept(BedType bedType) {
		Concept concept = getConcept(bedType);

		if (concept == null) {
			concept = new Concept();
			concept.setUuid(bedType.getUuid());

			mapConceptMetaData(concept, bedType);
		}

		setConceptName(concept, bedType);

		return concept;
	}

	private Concept setConceptName(Concept concept, BedType bedType) {
		Locale locale = Context.getLocale();
		ConceptName existingName = concept.getFullySpecifiedName(locale);
		if (existingName != null) {
			concept.removeName(existingName);
		}

		ConceptName conceptFullName = new ConceptName();
		conceptFullName.setName(bedType.getName());
		conceptFullName.setLocale(locale);
		conceptFullName.setConceptNameType(ConceptNameType.FULLY_SPECIFIED);
		concept.setFullySpecifiedName(conceptFullName);

		ConceptName existingShortName = concept.getShortNameInLocale(locale);
		if (existingShortName != null) {
			concept.removeName(existingShortName);
		}

		ConceptName conceptShortName = new ConceptName();
		conceptShortName.setName(bedType.getDescription());
		conceptShortName.setLocale(locale);
		conceptShortName.setConceptNameType(ConceptNameType.SHORT);
		concept.setShortName(conceptShortName);

		return concept;
	}

	private Concept mapConceptMetaData(Concept concept, BedType bedType) {
		ConceptDescription description = new ConceptDescription(bedType.getDisplayName(), Context.getLocale());
		concept.addDescription(description);

		concept.setConceptClass(conceptService.getConceptClassByName(BED_CONCEPT_CLASS));
		concept.setDatatype(conceptService.getConceptDatatypeByName(BED_CONCEPT_DATATYPE));

		// Setting up Saleable = true
		ConceptAttributeType conceptAttributeType = conceptService
				.getConceptAttributeTypeByName(SALEABLE_CONECPT_ATTRIBUTE);

		ConceptAttribute conceptAttribute = new ConceptAttribute();
		conceptAttribute.setAttributeType(conceptAttributeType);
		conceptAttribute.setValue(Boolean.TRUE);
		concept.addAttribute(conceptAttribute);

		return concept;
	}

	private PlatformTransactionManager getSpringPlatformTransactionManager() {
		List<PlatformTransactionManager> platformTransactionManagers = Context
				.getRegisteredComponents(PlatformTransactionManager.class);
		return platformTransactionManagers.get(0);
	}

}
