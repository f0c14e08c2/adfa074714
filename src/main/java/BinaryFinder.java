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
		int res = -1;
		
		if (previousResult >= 0) {
			if (ByteStringUtils.stringCompare(previousSearchWord, 0, searchWord) < 0) {
				if (ByteStringUtils.stringCompare(fileData, previousResult, searchWord) > 0) {
					return previousResult;
				}
			}
		} 
		
		res = locateNearestWordWithDictionaryDistribution(searchWord);
		
		previousResult = res;
		previousSearchWord = searchWord;
		
		return res;
	}

	private int locateNearestWordWithDictionaryDistribution(byte[] searchWord) {
		final char firstChar = (char) (searchWord[0] & 0xFF);
		final char nextChar = (char)(firstChar + 1);
		boolean fromCache = false;
		
		if (searchWord.length == 1) {
			final int cachedLowerBound = wordDistributionMap[firstChar];
			final int cachedUpperBound = wordDistributionMap[nextChar];
			fromCache = cachedLowerBound >= 0 && cachedUpperBound >= 0;
			if (fromCache) {
				lowerBound = cachedLowerBound;
				upperBound = cachedUpperBound;
			}
		} 
		
		if (!fromCache) {
			lowerBound = prevousCachedLowerBound;
			upperBound = fileData.length - 1;
		}
		
		int wordPostion = locateNearestWordPositionInt(searchWord);
		
		if (!fromCache && searchWord.length == 1 && wordPostion >= 0) {
			
			wordDistributionMap[firstChar] = wordPostion;
			
			if (fileData[wordPostion] != searchWord[0]) {
				wordDistributionMap[nextChar] = wordPostion;
			} else {
				upperBound = fileData.length - 1;
				wordDistributionMap[nextChar] = locateNearestWordPositionInt(new byte[] {(byte)nextChar});
			}
			
//			System.out.println("Caching range for'" + firstChar + "' first(" + wordDistributionMap[firstChar] + ") last(" + wordDistributionMap[nextChar] + ") range");
//			ByteStringUtils.printFromBuffer(fileData, wordDistributionMap[firstChar]);
//			if (wordDistributionMap[nextChar] != -1) {
//				ByteStringUtils.printFromBuffer(fileData, wordDistributionMap[nextChar]);
//			}
		}
		
		if (wordPostion > 1) {
			prevousCachedLowerBound = wordPostion - 2;
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
				//tryPostion = wordPostion;
				compareResult = ByteStringUtils.stringCompare(fileData, wordPostion, searchWord);

				if (prevWordPostion != wordPostion) {
					lastDiffWordPostion = prevWordPostion;
					prevWordPostion = wordPostion;
				}
			}
			
			//System.out.println(" TPos: " + tryPostion + " WPos: " + (wordPostion << 1));
			
			prevTryPostion = tryPostion;
			
			//ByteStringUtils.printFromBuffer(fileData, wordPostion);
			//System.out.print(" CMP: " + compareResult + " ");
			//ByteStringUtils.printFromBuffer(searchWord, 0);
			//System.out.println("");
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

			//System.out.println(" U " + upperBound + " L " + lowerBound);
		}	
		
		if (lastDiffWordPostion != -1 && lastDiffWordPostion != wordPostion && compareResult < 0) {
			wordPostion = lastDiffWordPostion;
		}
		//System.out.println("Bin search complete " + wordPostion);
		
		return wordPostion;
	}

}
