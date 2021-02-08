package com.demo.atm.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.demo.atm.controller.ATMController;
import com.demo.atm.entity.ATM;
import com.demo.atm.entity.Address;
import com.demo.atm.http.HttpRequestor;
import com.demo.atm.tranformer.JsonResponseTransformService;

class ATMControllerTest {

	@InjectMocks
	ATMController atmController;

	@Mock
	JsonResponseTransformService jsonResponseTransformService;

	String URL = "https://www.ing.nl/api/locator/atms/";
	String response = ")]}',[{\n" + "\"address\": {\n" + "\"housenumber\": \"test\",\n"
			+ "			\"city\": \"HYDERABAD\",\n" + "		\"geoLocation\": null\n" + "		},\n"
			+ "		\"distance\": 10,\n" + "		\"openingHours\": null,\n"
			+ "		\"functionality\": \"testing1\",\n" + "		\"type\": \"test1\"\n" + "	},\n" + "	{\n"
			+ "		\"address\": {\n" + "			\"housenumber\": \"test\",\n" + "			\"city\": \"PUNE\",\n"
			+ "			\"geoLocation\": null\n" + "		},\n" + "		\"distance\": 11,\n"
			+ "		\"openingHours\": null,\n" + "		\"functionality\": \"testing2\",\n"
			+ "		\"type\": \"test2\"\n" + "	},\n" + "	{\n" + "		\"address\": {\n"
			+ "			\"housenumber\": \"test\",\n" + "			\"city\": \"BANGLORE\",\n"
			+ "			\"geoLocation\": null\n" + "		},\n" + "		\"distance\": 12,\n"
			+ "		\"openingHours\": null,\n" + "		\"functionality\": \"testing3\",\n"
			+ "		\"type\": \"test3\"\n" + "	}\n" + "]";
	Map<ATM, String> atmCacheMap = new HashMap<>();

	String city1 = "HYDERABAD";
	String city2 = "PUNE";
	String city3 = "BANGLORE";

	@BeforeEach
	public void beforeEach() {

		MockitoAnnotations.initMocks(this);
		HttpRequestor httpRequestor = mock(HttpRequestor.class);

		atmController = new ATMController(URL, jsonResponseTransformService, httpRequestor);

		ATM atm1 = new ATM();
		atm1.setDistance(10);
		atm1.setFunctionality("testing1");
		atm1.setType("test1");

		atm1.setAddress(getSeedAddressData(city1));

		ATM atm2 = new ATM();
		atm2.setDistance(11);
		atm2.setFunctionality("testing2");
		atm2.setType("test2");
//		System.out.println(response);

		atm2.setAddress(getSeedAddressData(city2));

		ATM atm3 = new ATM();
		atm3.setDistance(12);
		atm3.setFunctionality("testing3");
		atm3.setType("test3");

		atm3.setAddress(getSeedAddressData(city3));
		atmCacheMap.put(atm1, city1);
		atmCacheMap.put(atm2, city2);
		atmCacheMap.put(atm3, city3);
		ATM[] array = new ATM[] { atm1, atm2, atm3 };
		when(httpRequestor.getResponse(URL)).thenReturn(response);
		try {
//			doReturn(array).when(jsonResponseTransformer).fromResponsetoArray(URL);
			when(jsonResponseTransformService.fromResponsetoArray(response.substring(5, response.length())))
					.thenReturn(array);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public Address getSeedAddressData(String cityName) {
		Address address = new Address();
		address.setCity(cityName);
		address.setHousenumber("test");
		return address;

	}

	@Test
	public void test_getAllATMS_happyPath() {

		List<ATM> atmSet = (List<ATM>) atmController.getAllATMS().getBody();
		assertNotNull(atmSet);

		assertEquals(atmCacheMap.keySet().size(), atmSet.size());

	}
	
	

}
