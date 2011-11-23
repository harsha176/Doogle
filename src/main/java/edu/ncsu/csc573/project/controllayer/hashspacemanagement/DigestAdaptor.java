package edu.ncsu.csc573.project.controllayer.hashspacemanagement;

import java.io.File;

public class DigestAdaptor implements IDigest {
	private Digest digest;
	private static DigestAdaptor digestAdaptor = null;
	
	// create a private class
	private DigestAdaptor() {
		
	}
	public static IDigest getInstance() throws Exception{
		if(digestAdaptor == null) {
			digestAdaptor = new DigestAdaptor();
			digestAdaptor.initialize();
		}
		return digestAdaptor;
	}
	private void initialize() throws Exception {
		digest = new Digest();
		digest.init_digest_table();
	}

	public byte[] getDigest(File file) throws Exception {
		byte[] digest_bits = new byte[Digest.MAX_BITS_DIGEST/8];
		if(!digest.get_digest_for_file(file.getAbsolutePath(), digest_bits)) {
			throw new Exception("Failed to compute digest for the file " + file.getAbsolutePath());
		}
		return digest_bits;
	}

	public byte[] getDigest(String query) throws Exception{
		byte[] digest_bits = new byte[Digest.MAX_BITS_DIGEST/8];
		if(!digest.get_digest_for_query(query.getBytes(), digest_bits)) {
			throw new Exception("Failed to compute digest for the query " + query);
		}
		return digest_bits;
	}

}
