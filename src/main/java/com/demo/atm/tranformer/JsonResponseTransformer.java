package com.demo.atm.tranformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.atm.entity.ATM;
import com.demo.atm.http.HttpRequestor;
import com.google.gson.Gson;

public class JsonResponseTransformer {

	private static final Logger log = LoggerFactory.getLogger(JsonResponseTransformer.class);

	public static ATM[] fromResponsetoArray(String URL) {
		String response = HttpRequestor.getResponse(URL);
		ATM[] atmArray = null;
		if (response != null && !response.isEmpty()) {
			String mainResponse = response.substring(5, response.length());
			try {
				atmArray = new Gson().fromJson(mainResponse, ATM[].class);
				return atmArray;

			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
		return null;

	}

}
