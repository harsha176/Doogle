package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import edu.ncsu.csc573.project.common.ByteOperationUtil;

public class DefaultMatcher implements IMatcher {
	private static final double THRESHOLD = 0.80;

	public boolean isMatches(byte[] query, byte[] other) {
		byte[] result = ByteOperationUtil.and(query, other);
		int querySetBits = ByteOperationUtil.countSetBits(query);
		int resultSetBits = ByteOperationUtil.countSetBits(result);
		double matchFactor = ((resultSetBits * 1.0) / querySetBits);
		if (matchFactor >= THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

}
