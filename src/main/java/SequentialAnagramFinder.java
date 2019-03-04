public class SequentialAnagramFinder extends AnagramFinder {

	private static final int ALL_POSSBLE_CHARS_SIZE = 256;
	private boolean[] charsPresentsMap = new boolean[ALL_POSSBLE_CHARS_SIZE];

    private BinaryFinder binaryFinder = new BinaryFinder();
    
    @Override
	public void findAnagrams(byte[] fileData, String query) {
		this.fileData = fileData;
		binaryFinder.setFileData(fileData);
		this.searchWordOriginal = ByteStringUtils.convertToArray(query);
		int searchWordLen = searchWordOriginal.length;
		
		this.searchWordSorted = new byte[searchWordLen]; 
		System.arraycopy(searchWordOriginal, 0, searchWordSorted, 0, searchWordLen);
		ByteStringUtils.sortIgnoreCase(searchWordSorted, 0, searchWordLen);
		
		for (int i = 0; i < searchWordLen; i++) {
			char c = (char)(searchWordOriginal[i] & 0xFF);
			charsPresentsMap[ByteStringUtils.toUpperCase(c)] = true;
			charsPresentsMap[ByteStringUtils.toLowerCase(c)] = true;
		}
		
		int i = 0;
		int start = 0;
		while (i < fileData.length) {
			char c = (char)(fileData[i] & 0xFF);
			
			if (ByteStringUtils.isEndOfString(fileData, i)) {
				checkForAnagram(fileData, i, start);
				i = seekToEnd(fileData, i);
				start = i;
			} else if (charsPresentsMap[c]) {
				i++;
			} else {
				//System.out.print(" - ");
				//ByteStringUtils.printFromBuffer(fileData, start);
				while (!ByteStringUtils.isEndOfString(fileData, i++));
				i = seekToEnd(fileData, i);
				start = i;
			}
		}
		
		if (start != i) {
			checkForAnagram(fileData, i, start);
		}
	}

    byte[] tempSearchWordSorted = new byte[1024];
	private void checkForAnagram(byte[] fileData, int i, int start) {
		int foundWordLen = i - start;

		if (foundWordLen == searchWordSorted.length) {
			System.arraycopy(fileData, start, tempSearchWordSorted, 0, foundWordLen);
			ByteStringUtils.sortIgnoreCase(tempSearchWordSorted, 0, foundWordLen);
			if (0 == ByteStringUtils.stringCompareExtended(tempSearchWordSorted, 0, searchWordSorted, foundWordLen, null, true)) {
				pushResult(start);
			}
		}
	}

	private int seekToEnd(byte[] fileData, int i) {
		while (fileData.length > i && ByteStringUtils.isEndOfString(fileData, i)) {
			i++;
		}
		return i;
	}
}