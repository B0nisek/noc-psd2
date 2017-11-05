package com.capco.noc.psd2.service.client;

import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * TODO - implement this service to fulfill the workshop task
 * */
@Service
public class ErsteRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErsteRestClient.class);

    //TODO - fill erste endpoint base URL
    private static final String ERSTE_BASE_URL = "";
    private static final String JSON_EL_DATA = "data";
    private static final String JSON_EL_RESULT = "result";
    private static final String JSON_EL_CODE = "code";

    private final RestTemplate restTemplate;
    private final JsonParser jsonParser;

    @Autowired
    ErsteRestClient(){
        this.restTemplate = new RestTemplate();
        this.jsonParser = new JsonParser();
    }

    //TODO - implement client methods

    //TODO - implement parser helpers
}
