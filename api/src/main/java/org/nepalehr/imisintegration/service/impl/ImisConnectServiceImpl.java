package org.nepalehr.imisintegration.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.nepalehr.imisintegration.constants.ImisIntegrationProperties;
import org.nepalehr.imisintegration.pojo.OpenImisAccountInformation;
import org.nepalehr.imisintegration.service.ImisConnectService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImisConnectServiceImpl implements ImisConnectService {

	Logger LOGGER = Logger.getLogger(ImisConnectServiceImpl.class);

	private ImisIntegrationProperties properties;

	public void setImisIntegrationProperties(ImisIntegrationProperties properties) {
		this.properties = properties;
	}

	RestTemplate restTemplate = new RestTemplate();

	@Override
	public OpenImisAccountInformation getAccountInformation(String nhisNumber) {
		// TODO make call to imis connect using httpclient
		OpenImisAccountInformation openImisAccountInformation = new OpenImisAccountInformation();
		openImisAccountInformation.setCardType("Normal");
		openImisAccountInformation.setFirstName("Paul");
		openImisAccountInformation.setLastName("Pogba");
		openImisAccountInformation.setNhisNumber(nhisNumber);
		openImisAccountInformation.setRemainingAmount(54009.23);
		try {
			openImisAccountInformation.setValidFrom(new SimpleDateFormat("yyyy/MM/dd").parse("2019/01/01"));
			openImisAccountInformation.setValidTill(new SimpleDateFormat("yyyy/MM/dd").parse("2019/12/31"));
		} catch (ParseException e) {
			LOGGER.error(e);
			e.printStackTrace();
		}
		return openImisAccountInformation;
	}

	@Override
	public String eligibilityRequest(String nhisNumber) {
		String plainCreds = properties.getImisConnectUser() + ":" + properties.getImisConnectPassword();
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String url = properties.getImisConnectUri() + "/check/eligibility/" + nhisNumber;
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String response = responseEntity.getBody();
		LOGGER.error(response);
		return response;
	}

	@Override
	public Boolean isInsuranceCardValid(String nhisNumber) {
		// TODO Auto-generated method stub
		return null;
	}
}
