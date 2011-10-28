package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import java.util.List;
/**
 * This acts as an interface for control layer to communicate with HashSpaceManager.
 * @author doogle-dev
 *
 */
public interface IHashSpaceManager {
	/**
	 * This method retrieves the search results for the given query.
	 * @param query
	 * @return
	 */
	public List<ISearchResult> search(IQuery query);
}
