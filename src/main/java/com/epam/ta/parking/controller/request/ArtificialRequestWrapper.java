package com.epam.ta.parking.controller.request;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>ArtificialRequestWrapper</code> instance stores user request and session data
 */
public class ArtificialRequestWrapper {

	/**
	 * <code>paramMap</code> map stores request parameters
	 */
	private Map<String, Object> paramMap = new HashMap<>();

	/**
	 * <code>attrMap</code> map stores response attributes
	 */
	private Map<String, Object> attrMap = new HashMap<>();

	/**
	 * <code>paramMap</code> map stores session attributes
	 */
	private Map<String, Object> sessionAttrMap = new HashMap<>();

	public Object getParameter(ParamList paramName) {
		if (paramMap.containsKey(paramName.get())) {
			return paramMap.get(paramName.get());
		}
		return null;
	}

	public void setParameter(ParamList command, Object paramValue) {
		paramMap.put(command.get(), paramValue);
	}

	public Object getAttr(AttrList attrName) {
		if (attrMap.containsKey(attrName.get())) {
			return attrMap.get(attrName.get());
		}
		return null;
	}

	public void setAttr(AttrList attrName, Object attrValue) {
		attrMap.put(attrName.get(), attrValue);
	}

	public void setSessionAttr(SessionAttrList attrName, Object attrValue) {
		sessionAttrMap.put(attrName.get(), attrValue);
	}

	public Object getSessionAttr(SessionAttrList attrName) {
		if (sessionAttrMap.containsKey(attrName.get())) {
			return sessionAttrMap.get(attrName.get());
		}
		return null;
	}

	public void clearRequestStorage() {
		paramMap = new HashMap<>();
		attrMap = new HashMap<>();
	}

}
