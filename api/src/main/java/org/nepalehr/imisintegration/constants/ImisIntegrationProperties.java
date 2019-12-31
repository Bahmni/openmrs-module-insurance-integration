package org.nepalehr.imisintegration.constants;

import org.bahmni.module.bahmnicore.properties.BahmniCoreProperties;
import org.springframework.stereotype.Component;

@Component
public class ImisIntegrationProperties {

	private static final String IMIS_CONNECT_URI = "imisconnect.uri";
	private static final String IMIS_CONNECT_USER = "imisconnect.user";
	private static final String IMIS_CONNECT_PASSWORD = "imisconnect.password";
	private static final String IMIS_CONNECT_CONNECTION_TIMEOUT = "imisconnect.connectionTimeoutInMilliseconds";
	private static final String IMIS_CONNECT_REPLY_TIMEOUT = "imisconnect.replyTimeoutInMilliseconds";

	public int getConnectionTimeoutInMilliseconds() {
		return Integer.parseInt(BahmniCoreProperties.getProperty(IMIS_CONNECT_CONNECTION_TIMEOUT));
	}

	public int getReplyTimeoutInMilliseconds() {
		return Integer.parseInt(BahmniCoreProperties.getProperty(IMIS_CONNECT_REPLY_TIMEOUT));
	}

	public String getImisConnectUser() {
		return BahmniCoreProperties.getProperty(IMIS_CONNECT_USER);
	}

	public String getImisConnectPassword() {
		return BahmniCoreProperties.getProperty(IMIS_CONNECT_PASSWORD);
	}

	public String getImisConnectUri() {
		return BahmniCoreProperties.getProperty(IMIS_CONNECT_URI);
	}

}
