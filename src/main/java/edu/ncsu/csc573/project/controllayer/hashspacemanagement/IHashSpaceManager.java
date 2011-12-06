package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import java.util.List;

import edu.ncsu.csc573.project.common.messages.MatchMetricSearchParameter;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishSearchParameter;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;

/**
 * This acts as an interface for control layer to communicate with
 * HashSpaceManager.
 * 
 * @author doogle-dev
 * 
 */
public interface IHashSpaceManager {
	/**
	 * This method retrieves the search results for the given query.
	 * 
	 * @param query
	 * @return
	 */
	public List<MatchMetricFileParamType> search(IQuery query);

	/**
	 * This method is called every time a publish request message is received from
	 * the server.
	 * 
	 * @param pubRequest
	 */
	public void handlePublishRequest(PublishRequestMessage pubRequest);
	
	/**
	 * This method removes the published files for the user from repository
	 * 
	 * @param username
	 */
	public void removePublishedFiles(String filename, String fileDigest);
	
	/**
	 * 
	 * @param fileDigest
	 * @param fileName
	 */
	public void updateDownloadCount(String fileDigest, String fileName);
}
