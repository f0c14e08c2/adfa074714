import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

class ByteStringUtils {
	
	private static final String ENCODING_CP1257 = "CP1257";

	public static void swap(byte[] x, int a, int b) {
		byte t = x[a];
		x[a] = x[b];
		x[b] = t;
	}
	
	public static void sort(byte[] dest, int low, int high) {
         for (int i=low; i<high; i++)
             for (int j=i; j>low && (((char)dest[j-1]) > ((char)dest[j])); j--)
                 swap(dest, j, j-1);
         return;
	}

	public static byte[] addToMask(byte[] maskedChars, byte b) {
		byte[] ret = new byte[maskedChars.length + 1];
		System.arraycopy(maskedChars, 0, ret, 0, maskedChars.length);
		ret[ret.length - 1] = b;
		return ret;
	}

	public static byte[] applyMask(byte[] searchWord, byte[] maskedChars) {
		
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

	public static boolean isEndOfString(byte[] str, int strOffset) {
		if (strOffset >= str.length) {
			return true;
		}
		
		byte b = str[strOffset];
		
		return b == '\n' || b == '\r';
	}
	
	public static int stringSize(byte[] str1, int str1Offset) { 
    	int i = 0;
        while (!isEndOfString(str1, str1Offset + i) && i + str1Offset < str1.length) {
            i++;
        }
        return i;
    }
 
	public static int stringCompare(byte[] str1, int str1Offset, byte[] str2) {
		 return stringCompareWithMaxLen(str1, str1Offset, str2, Integer.MAX_VALUE);
	}
	
	public static int stringCompareWithDepth(byte[] str1, int str1Offset, byte[] str2, int str2Offset, int[] compareDepth) {
		return stringCompareInt(str1, str1Offset, str2, str2Offset, Integer.MAX_VALUE, compareDepth);
	}
	
	public static int stringCompareWithMaxLen(byte[] str1, int str1Offset, byte[] str2, int maxLenToCompare) {
		return stringCompareInt(str1, str1Offset, str2, 0, maxLenToCompare, null);
	}
	
	private static int stringCompareInt(byte[] str1, int str1Offset, byte[] str2, int str2Offset, int maxLenToCompare, int[] compareDepth) { 
    	int i = 0;
    	boolean isStr1SizeLimit = false; 
    	boolean isStr2SizeLimit = false;
    	int cmpRet = 0;
    	
        while (true) {
        	isStr1SizeLimit = isEndOfString(str1, str1Offset + i);
        	isStr2SizeLimit = isEndOfString(str2, str2Offset + i);
        	
        	if (maxLenToCompare == i) {
        		cmpRet = 0;
        		break;
        	}
        	
        	if (isStr1SizeLimit || isStr2SizeLimit) {
        		cmpRet = (isStr2SizeLimit ? 1 : 0) - (isStr1SizeLimit ? 1 : 0);
        		break;
        	}
        	
        	int str1_ch = (int)(str1[i + str1Offset] & 0xFF); 
        	int str2_ch = (int)(str2[i] & 0xFF);
  
            if (str1_ch != str2_ch) { 
            	cmpRet = (str1_ch - str2_ch);
                break;
            }
            i++;
        }
        if (compareDepth != null) {
        	compareDepth[0] = i;
        }

        return cmpRet;
    }
    
	public static int locateSolidWord(byte[] buffer, int offset) {
		
		for (int i = 0; i + offset < buffer.length; i++) {
			final int wordEdge;
			if (offset - i == 0) {
				wordEdge = -1;
			} else if (isEndOfString(buffer, offset - i)) {
				wordEdge = -1;
			} else if (isEndOfString(buffer, offset + i)) {
				wordEdge = 1;
			} else {
				wordEdge = 0;
			}

			for (int j = wordEdge > 0 ? offset + i : offset - i; j < buffer.length && wordEdge != 0; j++) {
				if (!isEndOfString(buffer, j)){
					return j;
				}
					
			}
		}
		return -1;
	}
	
	public static String convertToString(byte[] fileData, int stringLocation) {
		final int stringSize = ByteStringUtils.stringSize(fileData, stringLocation);
		return new String(fileData, stringLocation, stringSize, Charset.forName(ENCODING_CP1257));
	}

	public static byte[] convertToArray(String string) {
		return string.getBytes(Charset.forName(ENCODING_CP1257));
	}

	public static void printFromBuffer(byte[] buffer, int offset) {
		int m = offset;
		while (m < buffer.length) {
			System.out.print(new String(new byte[]{buffer[m++]}, Charset.forName(ENCODING_CP1257)));
			//System.out.print(Character. buffer[m++]));
			if (m >= buffer.length || (buffer[m] == '\n' || buffer[m] == '\r')) {
				System.out.println();
				break;
			}
		}
	}
}