/**
 * 
 */
package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishSearchParameter;

/**
 * @author doogle-dev
 * 
 */
public class HashSpaceManager implements IHashSpaceManager {
	private PublishSearchParameter publishedFiles;
	private IMatcher matcher;
	private Logger logger;
	
	public HashSpaceManager() {
		publishedFiles = new PublishSearchParameter();
		matcher = new DefaultMatcher();
		logger = Logger.getLogger(HashSpaceManager.class);
	}

	/**
	 * Iterate through all of them see and identify the match
	 */
	public synchronized PublishSearchParameter search(IQuery query) {
		PublishSearchParameter searchResults = new PublishSearchParameter();

		publishedFiles.resetCounter();
		while (publishedFiles.getParamCount() < publishedFiles.getSize()) {
			byte[] digest = (byte[]) publishedFiles
					.getParamValue(EnumParamsType.FILEDIGEST);
			if (matcher.isMatches(query.getQueryDigest(), digest)) {
				searchResults.add(EnumParamsType.FILENAME,
						publishedFiles.getParamValue(EnumParamsType.FILENAME));
				searchResults
						.add(EnumParamsType.FILEDIGEST, publishedFiles
								.getParamValue(EnumParamsType.FILEDIGEST));
				searchResults.add(EnumParamsType.FILESIZE,
						publishedFiles.getParamValue(EnumParamsType.FILESIZE));
				searchResults.add(EnumParamsType.IPADDRESS,
						publishedFiles.getParamValue(EnumParamsType.IPADDRESS));
				searchResults.add(EnumParamsType.ABSTRACT,
						publishedFiles.getParamValue(EnumParamsType.ABSTRACT));
				searchResults.add(EnumParamsType.DELIMITER, null);
			}
			publishedFiles.setNextParam();
		}
		return searchResults;
	}

	/**
	 * Add all of them to published files
	 */
	public synchronized void handlePublishRequest(
			PublishRequestMessage pubRequest) {
		logger.debug("Adding all the published files into database");
		publishedFiles.add((PublishSearchParameter) pubRequest.getParameter());
	}

}
