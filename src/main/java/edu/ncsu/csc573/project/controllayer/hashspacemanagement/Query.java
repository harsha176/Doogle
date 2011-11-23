package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import edu.ncsu.csc573.project.common.ByteOperationUtil;

public class Query implements IQuery{
	private byte[] digest;
	
	public byte[] getQueryDigest() {
		return digest;
	}
	
	public Query(byte[] digest) {
		this.digest = digest;
	}
	
	public Query(String encDigest) {
		digest = ByteOperationUtil.convertStringToBytes(encDigest);
	}

}
