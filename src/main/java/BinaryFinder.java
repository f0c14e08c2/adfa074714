import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BinaryFinder {
	
	private byte[] fileData;
	
	int lowerBound;
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
		final char firstChar = (char)(searchWord[0] & 0xFF);
		final int cachedLowerBound = wordDistributionMap[firstChar];
		final int cachedUpperBound = wordDistributionMap[firstChar + 1];
		final boolean fromCache = cachedLowerBound >= 0 && cachedUpperBound >= 0; 

		lowerBound = fromCache ? cachedLowerBound : 0;
		upperBound = fromCache ? cachedUpperBound : fileData.length - 1;
		
		int wordPostion = locateNearestWordPositionInt(searchWord);
		
		if (!fromCache && searchWord.length == 1) {
			final char nextChar = (char)(firstChar + 1);

			wordDistributionMap[firstChar] = wordPostion;
			upperBound = fileData.length - 1;
			wordDistributionMap[nextChar] = locateNearestWordPositionInt(new byte[] {(byte)nextChar});
		}
		
		return wordPostion;
	}

	private int locateNearestWordPositionInt(byte[] searchWord) {
		upperBound = upperBound << 1;
		lowerBound = lowerBound << 1;
		
		int wordPostion = -1;
		
		int prevWordPostion = -1;
		int lastDiffWordPostion = -1;
		int compareResult = 0;
		while (true) {
			int tryPostion = (int)(lowerBound + (upperBound - lowerBound) / 2);
			//System.out.println(" T: " + tryPostion);
			
			wordPostion = ByteStringUtils.locateSolidWord(fileData, tryPostion >> 1);
			if (wordPostion == -1) {
				break;
			}
			//tryPostion = wordPostion;
			compareResult = ByteStringUtils.stringCompareWithDepth(fileData, wordPostion, searchWord, 0, null);
			
			if (prevWordPostion != wordPostion) {
				lastDiffWordPostion = prevWordPostion;
				prevWordPostion = wordPostion;
			}
			
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
		
		return wordPostion;
	}
}
