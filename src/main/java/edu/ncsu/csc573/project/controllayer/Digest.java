

 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ncsu.csc573.project.controllayer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author krishna
 */
public class Digest {

//#define MASTER_FILE "MASTER"

static int NUM_BITS_INTEGER = 8*32;
static int MAX_BITS_DIGEST = 8192;


//int map[128];
int[] map = null;
int[] digest = null;
static int MAX_CLASS_CUBED = (37*37*37+1);
//int digest[MAX_CLASS_CUBED]; 


/*
 * The following function checks if the character is alpha numeric, or new line or a space.
 * Returns 1 if the character is one of the above. 
 * Else Returns 0
 */
public boolean can_include_char_in_digest(char ch){

	if(!(ch >= 'a'&& ch<='z')&&!(ch >= 'A' && ch <= 'Z') && !(ch >='0'&& ch <= '9') && ch!='\n' && ch!=' ')
		return false;
	return true;
}


/*
 * When you encounter a new line or a space character stop digesting this word and continue to the next word
 */

public boolean stop_digest_this_word(char ch) {
	if(ch == ' ' || ch == '\n'){
		return true;	
	}
	return false;
}

/*
 * function: construct_ascii_mapping
 * called as a part of init_digest_table
 * classifies the characters into classes. a-z form class 1 to 26, 0-9 form class 27 to 36 
 * all the other characters form class 0
 */

public boolean construct_ascii_mapping() {
	int i;
        
        	for(i=0;i<128;i++){
		if(i>='a'&&i<='z'){
			map[i]=1+i-'a';
		}
		else if(i>='A'&&i<='Z'){
			map[i]=1+i-'A';
		}
		else if(i>='0'&& i<='9'){
			map[i]=1+26+i-'0';
		}else {
			map[i]=0;
		}
	}
	return false;
}


public boolean fill_digest_map() {
	int i;
	for(i=0;i<MAX_CLASS_CUBED;i++){
		digest[i]=-1;
	}
	return true;
}

public int get_hash(char a, char b, char c){
	/*
	 * Mult factor for ....
	 * First Character 37^0 = 0
	 * Second character 37^1 = 37 
	 * Third Character 37^2 = 1369
	 */
	//LOG("%c%c%c\n",a,b,c);
	return 1369*map[(int)a]+37*map[(int)b]+map[(int)c];
}

/* 
 * Remap digest: Compress the unused parts of the digest
 * Unused parts - trigrams which were not found from ASPELL Dictionary dump
 * The number of useful trigrams reduce from 37^3 to number of trigrams from Aspell
 */

public boolean remap_digest() {
	int count = 0;
	int i;
	int numElements = MAX_CLASS_CUBED;
	for(i = 0; i < numElements; i++) {
		if(0 == digest[i]){
			digest[i] = count;
			count ++;
		}	
	}
	for(i = 0; i < numElements; i++) {
		if(-1 == digest[i]) {
			digest[i] = count;
		}
	}
	//LOG("Max Pos where Bit is Set:%d\n",count);

	return true;
}


public boolean get_digest_for_file(String filename, String  a) throws FileNotFoundException, IOException {
	//int fd; /* fd of the master file obtained from the Aspell Dictionary*/
	char buffer = 0;
	int numRead = 0;
	char first,second,third;
	boolean master = false;
	int tmp_digest = -1;
	

	//int *a = (int *)arr;
	
	if(null == a){
		master = true;	
	}

	File fd = new File(filename);       //open(filename,O_RDONLY);
	if(fd == null) {
		if(master == true){
			System.out.println("Failed to open the File" + filename + " for creating digest map ");
			return false;
		}
		System.out.println("Failed to open File:" + filename + " for creating digest\n");
		return false;
	}
	first = second = third = '\0';
            FileInputStream file = new FileInputStream(fd);
            DataInputStream in = new DataInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
  //Read File Line By Line
            while ((strLine = br.readLine()) != null)
             //       while((fd,&buffer,1)>0) {
            {
                if(!can_include_char_in_digest(buffer)){
			continue;
		} 
		/*New line or a space */ 
		if(stop_digest_this_word(buffer)){
			/* Handle for last trigram of the line*/
			if(numRead <= 1) {
				second = third = '\0';
			}else if(numRead == 2)
			{
				/*We have 2 characters. Make the third NULL.Do Digest for it*/
				third = '\0';
			}
			else 
			{
				/*We have 2 characters followed by space. Make the third NULL.Do Digest for it*/
				first = second;
				second = third;
				third = '\0';
			}
			tmp_digest = get_hash(first,second,third);
			if(master == true){
				digest[tmp_digest]=0;
	
			}else {
				tmp_digest = digest[tmp_digest];
//				a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
			}
			numRead = 0;		
			first = second = third = '\0';
		} else {
			numRead++;
			if(numRead == 1){
				first = buffer;	
			}
			else if(numRead == 2){
				second = buffer;
			}else if (numRead == 3){
				third = buffer;
				tmp_digest = get_hash(first,second,third);
				if(master == true){
					digest[tmp_digest]=0;
				}else {
					tmp_digest = digest[tmp_digest];
//					a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
				}
			}else {
				/* 
				 * dont increment numRead. 
				 * Keep at 3 for processing. Useful when seeing a space or a newline 
				 */
				numRead = 3;
				first = second;
				second = third;
				third = buffer;
				tmp_digest = get_hash(first,second,third);
				if(master == true){
					digest[tmp_digest]=0;
				}else {
					tmp_digest = digest[tmp_digest];	
//					a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
				}
			}
		}
	}	
	in.close();
	return true;
}
//public boolean get_digest_for_query(const char *query, int query_len, void* arr) {
public boolean get_digest_for_query(String query, int query_len, String arr) {
	char buffer;
	int numRead = 0;
	char first,second,third;
	int tmp_digest = -1;
	int i;

	//int *a = (int *)arr;

		
		
	first = second = third = '\0';
	for(i = 0; i < query_len; i++) {

		buffer = query.charAt(i);
		if(!(can_include_char_in_digest(buffer))){
			continue;
		} 
		/*New line or a space */ 
		if(stop_digest_this_word(buffer)){
			/* Handle for last trigram of the line*/
			if(numRead <= 1) {
				second = third = '\0';
			}else if(numRead == 2)
			{
				/*We have 2 characters. Make the third NULL.Do Digest for it*/
				third = '\0';
			}
			else 
			{
				/*We have 2 characters followed by space. Make the third NULL.Do Digest for it*/
				first = second;
				second = third;
				third = '\0';
			}
			tmp_digest = get_hash(first,second,third);

			tmp_digest = digest[tmp_digest];
//			arr.charAt(tmp_digest/NUM_BITS_INTEGER)=arr.charAt(tmp_digest/NUM_BITS_INTEGER)|(1 << (tmp_digest%NUM_BITS_INTEGER));
		
			numRead = 0;		
			first = second = third = '\0';
		} else {
			numRead++;
			if(numRead == 1){
				first = buffer;	
			}
			else if(numRead == 2){
				second = buffer;
			}else if (numRead == 3){
				third = buffer;
				tmp_digest = get_hash(first,second,third);

				tmp_digest = digest[tmp_digest];
//				a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
			}else {
				/* 
				 * dont increment numRead. 
				 * Keep at 3 for processing. Useful when seeing a space or a newline 
				 */
				numRead = 3;
				first = second;
				second = third;
				third = buffer;

				tmp_digest = get_hash(first,second,third);
				tmp_digest = digest[tmp_digest];
//				a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
				
			}
		}
	}
	/* digest the last trigram of the query */
	if(numRead <=2) {
		third='\0';
		if(numRead <= 1) {
			second='\0';	
		}
		//printf("LastChar:%c%c%c Numread:%d\n",first,second,third,numRead);
		tmp_digest = get_hash(first,second,third);
		tmp_digest = digest[tmp_digest];
//		a[tmp_digest/NUM_BITS_INTEGER]=a[tmp_digest/NUM_BITS_INTEGER]|(1<<(tmp_digest%NUM_BITS_INTEGER));
	}
	return true;
}
/* 
 * Function: init_digest_table
 * Entry point for the library to set up digest mappings
 * 
 */
public boolean init_digest_table() throws FileNotFoundException, IOException {
	construct_ascii_mapping();
	fill_digest_map();
	if(false == get_digest_for_file("MASTER_FILE",null)){
		return false;	
	}
	remap_digest();
	return true;
}
//#define LOG(a,b) printf(a##b)
//#else
//#define LOG(a,b)  ;


/*
 * 37 classes. 
 * A-Z form 1 to 26
 * 0-9 form 27 to 36
 * all other chars form class 0
 * The max number of useful trigrams is reduced from 37^3 to 74XX after parsing 
 * ASPELL dictionary dump. Refer the file "MASTER" for the subset of strings used
 * MASTER is a text file.
 * We have chosen 8192 as upper bound since its a multiple of 32 and 1024
 */

/*
 * Func: get_digest_for_file
 * Returns: OK or ERROR based on the successful
 * Parameters: 
 *	1) Filename - Expected to be the current directory
 * 	2) any array of MAX_BITS_DIGEST bits
 *		EX: if an integer is 32 bits array should be for (MAX_BITS_DIGEST/32) integers
 *		    if it is a byte(char) array. it shoule be for (MAX_BITS_DIGEST/8) characters
 *		Refer test.c function:testfile() for sample usage
 */

//int get_digest_for_file(const char *filename, void *arr);

/*
 * Func: get_digest_for_query
 * Returns: OK or ERROR based on the successful
 * Parameters: 
 *	1) query - string to be queried. 
 *	2) query_len - strlen+1 of the query
 * 	2) any array of MAX_BITS_DIGEST bits
 *		EX: if an integer is 32 bits. array should be for (MAX_BITS_DIGEST/32) integers
 *		    if it is a byte(char) array. it shoule be for (MAX_BITS_DIGEST/8) characters
 *		Refer test.c function:testquery() for sample usage
 */
//int get_digest_for_query(const char *query, int query_len, void* arr) ;

/*
 * Func:init_digest_table
 * Returns: OK or ERROR based on the status 
 * Entry Point to the digest library. To be called from the main function 
 * of the program
 * Refer test.c for sample implemetation
 */
//int init_digest_table();
    
}

