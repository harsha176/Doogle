/**
 * 
 */
package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.ConfigurationManager;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.PublishSearchParameter;
import edu.ncsu.csc573.project.common.schema.DownloadFileParamType;
import edu.ncsu.csc573.project.common.schema.FileParamType;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;

/**
 * This class is a default implementation of HashSpaceManager.
 * 
 * It does'nt include any sophisticated data structures and algorithms.
 * 
 * @author doogle-dev
 * @see PublishSearchParameter
 * @see IMatcher
 * 
 */
public class HashSpaceManager implements IHashSpaceManager {
	/**
	 * This data structure is used to maintain all the published files to the
	 * server.
	 */
	private List<DownloadFileParamType> publishedFilesRepository;

	/**
	 * This is used to match files during search.
	 */
	private IMatcher matcher;
	/**
	 * logger instance used for logging events and errors
	 */
	private Logger logger;

	/**
	 * Initialize published file repository data structure and matcher
	 */
	public HashSpaceManager() {
		publishedFilesRepository = new ArrayList<DownloadFileParamType>();
		matcher = new DefaultMatcher();
		logger = Logger.getLogger(HashSpaceManager.class);
	}

	/**
	 * This method performs search and based on the query search results are
	 * returned.
	 * 
	 * @return search results in PublishSearchParameter data structure
	 */
	public synchronized List<MatchMetricFileParamType> search(IQuery query) {

		List<MatchMetricFileParamType> searchResults = new ArrayList<MatchMetricFileParamType>();

		// Iterate through publishedFilesRepository and uses matcher to see if a
		// query matches any of the file digest in publishedFilesRemository
		for (DownloadFileParamType dfpt : publishedFilesRepository) {
			// obtain digest of each file in repository
			byte[] digest = ByteOperationUtil.convertStringToBytes(dfpt
					.getFiledigest());
			// check if it matches the query if so add them to search results.
			if (matcher.isMatches(query.getQueryDigest(), digest)) {
				MatchMetricFileParamType mmfpt = new MatchMetricFileParamType();
				mmfpt.setAbstract(dfpt.getAbstract());
				mmfpt.setFiledigest(dfpt.getFiledigest());
				mmfpt.setFilename(dfpt.getFilename());
				mmfpt.setIpaddress(dfpt.getIpaddress());
				mmfpt.setFilesize(dfpt.getFilesize());
				double matchCoefficient = matcher.getMatchFactor() * 0.9
						+ ((dfpt.getDownloads()/ConfigurationManager.getInstance().getMaxDownloadLimit()) * 0.1);
				mmfpt.setMatchMetric(matchCoefficient);
				searchResults.add(mmfpt);
			}
			// go the next entry in the repository
		}
		logger.info("Number of search results obtained on search is "
				+ searchResults.size());
		return searchResults;
	}

	/**
	 * This method simply adds all the published files from server to published
	 * file repository
	 */
	public synchronized void handlePublishRequest(
			PublishRequestMessage pubRequest) {
		List<FileParamType> publishedFilesParam = pubRequest.getFileList();

		for (FileParamType fpt : publishedFilesParam) {
			DownloadFileParamType dftp = new DownloadFileParamType();
			dftp.setAbstract(fpt.getAbstract());
			dftp.setFiledigest(fpt.getFiledigest());
			dftp.setFilename(fpt.getFilename());
			dftp.setFilesize(fpt.getFilesize());
			dftp.setIpaddress(fpt.getIpaddress());
			dftp.setDownloads(0);
			publishedFilesRepository.add(dftp);
		}

		logger.info("Added " + publishedFilesParam.size()
				+ " published files into database");
		logger.info("Total size of repository is "
				+ publishedFilesRepository.size());
	}

	/**
	 * This method should remove the published files by the logged out user.
	 */
	public void removePublishedFiles(String fileName, String fileDigest) {
		for (int i = 0; i < publishedFilesRepository.size(); i++) {
                        DownloadFileParamType dfpt = publishedFilesRepository.get(i);
			if (dfpt.getFilename().equals(fileName) /*&& dfpt.getFiledigest().equals(fileDigest)*/) {
				publishedFilesRepository.remove(dfpt);
				logger.info("Removed file from the repository " + fileName);
				logger.info("Published files repository size is " + publishedFilesRepository.size());
			}
		}
	}

	public void updateDownloadCount(String fileDigest, String fileName) {
		for (DownloadFileParamType dfpt : publishedFilesRepository) {
			if (dfpt.getFilename().equals(fileName))
					//&& dfpt.getFiledigest().equals(fileDigest)) {
                        {
                            dfpt.setDownloads(dfpt.getDownloads() + 1);
				logger.info("Updated download count for file " + fileName
						+ " to " + dfpt.getDownloads());
			}
		}
	}
}
