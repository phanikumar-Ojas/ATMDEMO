package com.demo.atm.tranformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.atm.entity.ATM;
import com.google.gson.Gson;

@Component
public class JsonResponseTransformer implements JsonResponseTransformService {

	private static final Logger log = LoggerFactory.getLogger(JsonResponseTransformer.class);

	public ATM[] fromResponsetoArray(String mainResponse) {
		ATM[] atmArray = null;
		if (mainResponse != null && !mainResponse.isEmpty()) {
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
