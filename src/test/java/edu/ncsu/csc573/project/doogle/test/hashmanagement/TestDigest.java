package edu.ncsu.csc573.project.doogle.test.hashmanagement;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc573.project.controllayer.hashspacemanagement.Digest;

public class TestDigest {
	public int sizeofchar = 1;
	public char[] digest_bits = new char[Digest.MAX_BITS_DIGEST];
	public int NUM_BITS_CHAR = 8;
	//@Test
	public void testquery(){
		Digest dig = new Digest();
		
		if(!dig.init_digest_table()) {
			
			System.out.println("Fail to initialize digest utility");
			return;
		}
		
		String query = "This is a query";
		int i;
		/* 
		 * Make sure none of the bits are set in digest_bits before calling 
		 */

		//memset(digest_bits,0,sizeof(digest_bits));
		System.out.println(("TESTING QUERY: " + query));
		dig.get_digest_for_query(query, digest_bits);

		/* To find which bits are set */
		System.out.print("Bits are set in:");
		for(i = 0;i < Digest.MAX_BITS_DIGEST; i++) {
			if((digest_bits[i/NUM_BITS_CHAR]&(1<<(i%NUM_BITS_CHAR))) == 1) {
				System.out.print(" "+i);
			}
		}
		System.out.println();

		return;
	}
}
