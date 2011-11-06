/**
 * 
 */
package edu.ncsu.csc573.project.common.messages;

/**
 * This an enumeration of all the fields request parameters can take.
 * @author doogle-dev
 *
 */
public enum EnumParamsType {
	USERNAME,
	EMAIL_ID,
	PASSWORD,
	DESIGNATION,
	SEARCHKEY,
	IPADDRESS,
        FILENAME,
        FILESIZE,
        ABSTRACT,
        FILEDIGEST,
        DELIMITER,
	CUSTOM,
        STATUSCODE,
        MESSAGE                 // Used for publish requests where field value is different. 
				// It can be used for other fields as well.
}
