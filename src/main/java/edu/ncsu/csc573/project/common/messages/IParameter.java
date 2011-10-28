package edu.ncsu.csc573.project.common.messages;

/**
 * This interface abstracts the notion of parameter. It is assumed that each
 * parameter has a field and value.
 * 
 * @author doogle-dev
 * 
 */
public interface IParameter {
	/**
	 * This method adds param value entry in Parameters object.
	 * @param param
	 * @param value
	 */
	void add(EnumParamsType param, Object value);
}
