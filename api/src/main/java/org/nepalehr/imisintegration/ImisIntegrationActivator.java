package org.nepalehr.imisintegration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.module.bahmnicore.properties.BahmniCoreProperties;
import org.openmrs.module.ModuleActivator;

public class ImisIntegrationActivator implements ModuleActivator {

	protected Log log = LogFactory.getLog(getClass());

	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Refreshing Imisintegration Module");
	}

	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		log.info("Imisintegration Module refreshed");
	}

	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Starting Imisintegration Module");
	}

	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		log.info("Imisintegration Module started");
		BahmniCoreProperties.load();
	}

	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		log.info("Stopping Imisintegration Module");
	}

	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("Imisintegration Module stopped");
	}

}
