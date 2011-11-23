package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

public class HashSpaceManagerFactory {
	private static IHashSpaceManager hashmanager= null;
	
	public static synchronized IHashSpaceManager getInstance() {
		if(hashmanager == null) {
			hashmanager = new HashSpaceManager();
		}
		return hashmanager;
	}
}
