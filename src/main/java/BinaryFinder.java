import java.util.Arrays;

public class BinaryFinder {
		
	private byte[] fileData;
	private byte[] previousSearchWord = null;
	private int previousResult = -1;
	
	int lowerBound;
	int prevousCachedLowerBound = 0;
	int upperBound;
	
	private static final int WORD_DISTRIBUTION_SIZE = 256;
	private int[] wordDistributionMap = createEmpty(WORD_DISTRIBUTION_SIZE);

	private int[] createEmpty(int size) {
		int[] result = new int[size];
		Arrays.fill(result, -1);
		return result;
	}
	
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public int locateNearestWordPosition(byte[] searchWord) {

		// Optimization: Checks that previous result was "bigger" than current search word 
		if (previousResult >= 0) {
			if (ByteStringUtils.stringCompare(previousSearchWord, 0, searchWord) < 0) {
				if (ByteStringUtils.stringCompare(fileData, previousResult, searchWord) > 0) {
					return previousResult;
				}
			}
		} 
		
		previousResult = locateNearestWordWithDictionaryDistribution(searchWord);
		previousSearchWord = searchWord;
		
		return previousResult;
	}

	private int locateNearestWordWithDictionaryDistribution(byte[] searchWord) {
		final char firstChar = (char) (searchWord[0] & 0xFF);
		final char nextChar = (char)(firstChar + 1);
		
		final int cachedLowerBound = wordDistributionMap[firstChar];
		final int cachedUpperBound = wordDistributionMap[nextChar];
		boolean fromCache = cachedLowerBound >= 0 && cachedUpperBound >= 0;
		lowerBound = fromCache ? cachedLowerBound : prevousCachedLowerBound;
		upperBound = fromCache ? cachedUpperBound : fileData.length - 1;
		
		int wordPostion = locateNearestWordPositionInt(searchWord);
		
		// Optimization: Skip already processed part of buffer for next call
		if (wordPostion > 1) {
			prevousCachedLowerBound = wordPostion - 2;
		}
		
		// Optimization: Reduces binary search range for first char 
		if (!fromCache && searchWord.length == 1 && wordPostion >= 0) {
			wordDistributionMap[firstChar] = wordPostion;
			
			if (fileData[wordPostion] != searchWord[0]) {
				wordDistributionMap[nextChar] = wordPostion;
			} else if (nextChar > ByteStringUtils.CHAR_z$) {
				wordDistributionMap[nextChar] = fileData.length - 1;
			} else {
				lowerBound = wordPostion;
				upperBound = fileData.length - 1;
				wordDistributionMap[nextChar] = locateNearestWordPositionInt(new byte[] {(byte)nextChar});
			}
		}
			
		return wordPostion;
	}

	private int locateNearestWordPositionInt(byte[] searchWord) {
		upperBound = upperBound << 1;
		lowerBound = lowerBound << 1;
		
		int wordPostion = -1;
		int prevTryPostion = -1;
		int prevWordPostion = -1;
		int lastDiffWordPostion = -1;
		int compareResult = 0;
		
		while (true) {
			int tryPostion = (int)(lowerBound + (upperBound - lowerBound) / 2);
			int tryPostionNorm = (tryPostion >> 1);
			if (!((tryPostionNorm >= (wordPostion) && tryPostion <= prevTryPostion)
					|| (tryPostionNorm <= (wordPostion) && tryPostion >= prevTryPostion))) {
				wordPostion = ByteStringUtils.locateSolidWord(fileData, tryPostionNorm);
				if (wordPostion == -1) {
					break;
				}
				compareResult = ByteStringUtils.stringCompare(fileData, wordPostion, searchWord);

				if (prevWordPostion != wordPostion) {
					lastDiffWordPostion = prevWordPostion;
					prevWordPostion = wordPostion;
				}
			}
			
			prevTryPostion = tryPostion;
			
			if (compareResult < 0) {				
				if(lowerBound == tryPostion) {
					break;
				}
				lowerBound = tryPostion;
			} else if (compareResult > 0) {
				if(upperBound == tryPostion) {
					break;
				}
				upperBound = tryPostion;
			} else {
				return wordPostion;
			}
		}	
		
		if (lastDiffWordPostion != -1 && lastDiffWordPostion != wordPostion && compareResult < 0) {
			// For partial match return "bigger" result
			wordPostion = lastDiffWordPostion;
		}
		
		return wordPostion;
	}
}
