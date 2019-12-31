package org.nepalehr.imisintegration.service;

import org.nepalehr.imisintegration.pojo.OpenImisAccountInformation;

public interface ImisConnectService {

	OpenImisAccountInformation getAccountInformation(String nhisNumber);

	Boolean isInsuranceCardValid(String nhisNumber);

	String eligibilityRequest(String nhisNumber);
}
