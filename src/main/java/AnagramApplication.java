import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.base.Strings;

class AnagramApplication {
	
    private static final String FALLBACK_FILEPATH = "/home/seregia/Documents/projects/java/anagramApplication/src/main/resources/lemmad.txt";
    private static final String FALLBACK_WORD = "vits";

	public static void main(String[] argv) {
		
		final String filePath = argv.length >= 1 && !Strings.isNullOrEmpty(argv[0])
				? argv[0]
				: FALLBACK_FILEPATH;
				
		final String word = argv.length >= 2 && !Strings.isNullOrEmpty(argv[1])
				? argv[1]
				: FALLBACK_WORD;			
				
		File fileName = new File(filePath);
		if (!fileName.exists()) {
			System.out.println("Please provide dicionary file path as first argument");
			return;
		}

		
			
	    long startTime = System.currentTimeMillis();
	    String result = "";
	    
	    File file = new File(filePath);
	    byte[] fileData = new byte[(int) file.length()];
	    DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
		    dis.readFully(fileData);
		    dis.close();
	    
		} catch ( IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		
		//reovesi
		
		//byte[] searchWord = {'r','e','o','v','e','s','i'};
		//byte[] searchWord = {'m','u','r','d','j','a'};
		byte[] searchWord = "vastupidavustreening".getBytes(Charset.forName("CP1257"));

		
		recursion(fileData, searchWord, new byte[]{});
		
		
		//printFromBuffer(fileData,
		//		locateSolidWord(fileData, fileData.length / 2));
		 
		long stop = System.currentTimeMillis() - startTime;
	    System.out.println(stop + result);
	}

	private static void recursion(byte[] fileData, byte[] searchWord, byte[] maskedChars) {
		
		byte[] maskedWord = applyMask(searchWord, maskedChars);
		addTabs(maskedChars);
		System.out.println("D: " + maskedChars.length + " MW: " + new String(maskedWord) + " M: " + new String(maskedChars));
		for (int i = 0; i < maskedWord.length; i++) {
			byte b = maskedWord[i];
			
			boolean contains = false;
			for (int j = 0; j < i && !contains; j++) {
				contains = b == maskedWord[j];
			}
			
			if (!contains) {
				byte[] query = addToMask(b, maskedChars);
				
				int responseIdx = locatePositionByString(fileData, query);
				addTabs(maskedChars);
				System.out.print("q: " + new String(query) + " res: ");
				printFromBuffer(fileData, responseIdx);
	
				if (query.length == searchWord.length) {
					if (0 == stringCompare(fileData, responseIdx, query)) {
						addTabs(maskedChars);
						System.out.print("RESULT -> ");
						printFromBuffer(fileData, responseIdx);
					}
				} else if (0 == stringCompareWithMaxLen(fileData, responseIdx, query, query.length)) {
					recursion(fileData, searchWord, query);
				}
			}
		}
	}

	private static void addTabs(byte[] maskedWord) {
		for (int i = 0; i < maskedWord.length; i++) {
			System.out.print("\t");
		}
	}

	private static byte[] addToMask(byte b, byte[] maskedChars) {
		byte[] ret = new byte[maskedChars.length + 1];
		System.arraycopy(maskedChars, 0, ret, 0, maskedChars.length);
		ret[ret.length - 1] = b;
		return ret;
	}

	private static byte[] applyMask(byte[] searchWord, byte[] maskedChars) {
		
		byte[] maskedCharsCopy = new byte[maskedChars.length];
		System.arraycopy(maskedChars, 0, maskedCharsCopy, 0, maskedCharsCopy.length);
		
		int newSize = searchWord.length - maskedChars.length;
		byte[] maskedWord = new byte[newSize];
		
		int maskedIdx = 0;
		for (int i = 0; i < searchWord.length; i++) {
			byte b = searchWord[i];
			
			boolean isMasked = false;
			for (int j = 0; j < maskedCharsCopy.length && !isMasked; j++) {
				isMasked = b == maskedCharsCopy[j];
				if (isMasked) {
					maskedCharsCopy[j] = 0;
				}
			}
			
			if (!isMasked) {
				maskedWord[maskedIdx++] = b;
			}
		}
		
		return maskedWord;
	}

	private static int locatePositionByString(byte[] fileData, byte[] searchWord) {
		int upperBound = fileData.length - 1;
		int lowerBound = 0; 
		
		while (true) {
			int tryPostion = lowerBound + (upperBound - lowerBound) / 2;
			//System.out.println(" T: " + tryPostion);
			
			tryPostion = locateSolidWord(fileData, tryPostion);
			//printFromBuffer(fileData, tryPostion);
			int compareResult = stringCompare(fileData, tryPostion, searchWord);
			//System.out.println(" CMP: " + compareResult);
			if (compareResult > 0) {
				if(upperBound == tryPostion) {
					return tryPostion;
				}
				upperBound = tryPostion;
			} else if (compareResult < 0) {				
				if(lowerBound == tryPostion) {
					return tryPostion;
				}
				lowerBound = tryPostion;
			} else {
				return tryPostion;
			}
			//System.out.println(" U " + upperBound + " L " + lowerBound);
		}
	}
	
	private static boolean isEndOfString(byte[] str, int strOffset) {
		if (strOffset >= str.length) {
			return false;
		}
		byte b = str[strOffset];
		
		return b == '\n' || b == '\r';
	}
	
	 private static int stringSize(byte[] str1, int str1Offset) { 
    	int i = 0;
        while (!isEndOfString(str1, str1Offset + i) && i + str1Offset < str1.length) {
            i++;
        }
        return i;
    }
 
	private static int stringCompare(byte[] str1, int str1Offset, byte[] str2) {
		 return stringCompareWithMaxLen(str1, str1Offset, str2, Integer.MAX_VALUE);
	}
	
    private static int stringCompareWithMaxLen(byte[] str1, int str1Offset, byte[] str2, int maxLenToCompare) { 
    	int i = 0;
    	boolean isStr1SizeLimit = false; 
    	boolean isStr2SizeLimit = false;
    	
        while (true) {
        	
        	isStr1SizeLimit = maxLenToCompare == i || isEndOfString(str1, str1Offset + i) || i + str1Offset >= str1.length;
        	isStr2SizeLimit = maxLenToCompare == i || isEndOfString(str2, i) || i >= str2.length;
        	
        	if (isStr1SizeLimit || isStr2SizeLimit) {
        		return (isStr2SizeLimit ? 1 : 0) - (isStr1SizeLimit ? 1 : 0);  
        	}
        	
        	char str1_ch = (char)str1[i + str1Offset]; 
            char str2_ch = (char)str2[i];
  
            if (str1_ch != str2_ch) { 
                return str1_ch - str2_ch; 
            }
            
            i++;
        }
    }
    
	private static void printFromBuffer(byte[] buffer, int offset) {
		int m = offset;
		while (m < buffer.length) {
			System.out.print(new String(new byte[]{buffer[m++]}, Charset.forName("CP1257")));
			//System.out.print(Character. buffer[m++]));
			if (buffer[m] == '\n' || buffer[m] == '\r') {
				System.out.println();
				break;
			}
		}
	}
	
	private static int locateSolidWord(byte[] buffer, int offset) {
		boolean nextLineFound = offset == 0;
		for (int i = offset; i < buffer.length; i++) {
			byte b = buffer[i];
			
			if (b == '\n' || b == '\r') {
				nextLineFound = true;
			}
			else if (nextLineFound) {
				return i;
			}
		}
		return -1;
	}
}