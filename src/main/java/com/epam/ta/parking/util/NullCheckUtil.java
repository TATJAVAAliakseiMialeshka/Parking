package com.epam.ta.parking.util;

/**
 * <code>NullCheckUtil</code> util class 
 * Makes some null checks
 *
 */
public class NullCheckUtil {

	public static boolean notNullCheck(String... params){
		for(String param: params){
			if(null == param || param.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean notNullCheck(Integer... params){
		for(Integer param: params){
			if(null == param){
				return false;
			}
		}
		return true;
	}

}
