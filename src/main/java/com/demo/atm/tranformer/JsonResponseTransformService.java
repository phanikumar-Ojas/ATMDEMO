package com.demo.atm.tranformer;

import com.demo.atm.entity.ATM;

public interface JsonResponseTransformService {
	
	public  ATM[] fromResponsetoArray(String mainData) ;	

}
