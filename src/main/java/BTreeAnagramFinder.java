public class BTreeAnagramFinder extends AnagramFinder {
   
    private BinaryFinder binaryFinder = new BinaryFinder();
        
    private int[] compareDepth = new int[1];
    
    @Override
	public void findAnagrams(byte[] fileData, String query) {
		this.fileData = fileData;
		binaryFinder.setFileData(fileData);
		
		this.searchWordOriginal = ByteStringUtils.convertToArray(query);
		
		int searchWordLen = searchWordOriginal.length;
		this.searchWordSorted = new byte[searchWordOriginal.length * 2]; 
		System.arraycopy(searchWordOriginal, 0, searchWordSorted, 0, searchWordLen);
		System.arraycopy(searchWordOriginal, 0, searchWordSorted, searchWordLen, searchWordLen);
		
		ByteStringUtils.toUpperCase(searchWordSorted, 0, searchWordLen);
		ByteStringUtils.toLowerCase(searchWordSorted, searchWordLen, searchWordLen);
		
		ByteStringUtils.sort(searchWordSorted, 0, searchWordSorted.length);
		
		findAnagrams(new byte[]{});
	}
    
	private void findAnagrams(byte[] maskedChars) {
		
		byte[] maskedWord = ByteStringUtils.applyMask(searchWordSorted, maskedChars);
	
		//addTabs(maskedChars);
		//System.out.println("D: " + maskedChars.length + " MW: " + new String(maskedWord,  Charset.forName("CP1257")) + " M: " + new String(maskedChars,  Charset.forName("CP1257")));
		char lastCharFromPreviousResult = 0;
		
		for (int i = 0; i < maskedWord.length; i++) {
			byte b = maskedWord[i];

			if ((char)b >= lastCharFromPreviousResult) {
				
				// Optimization: Skip identical branches starting on same letter
				boolean contains = false;
				for (int j = 0; j < i && !contains; j++) {
					contains = b == maskedWord[j];
				}
				
				if (!contains) {
					byte[] query = ByteStringUtils.addToMask(maskedChars, b);

					int responseIdx = binaryFinder.locateNearestWordPosition(query);
//					addTabs(maskedChars);
//					System.out.print("q: " + new String(query, Charset.forName("CP1257")) + " res: ");
//					if (responseIdx >= 0)
//						ByteStringUtils.printFromBuffer(fileData, responseIdx);
//					else 
//						System.out.println("responseIdx: " + responseIdx);
//					
					if (responseIdx >= 0) {

						boolean queryEqResponse = (0 == ByteStringUtils.stringCompareWithMaxLenWithDepth(fileData, responseIdx, query, query.length, compareDepth));
						
						// Optimization: Skip all following branches if response doesn't match to root criteria 
						if (maskedChars.length > compareDepth[0]) {
							return;
						}
						
						lastCharFromPreviousResult = (char)fileData[responseIdx + maskedChars.length];
						
						if (query.length == searchWordOriginal.length) {
							if (0 == ByteStringUtils.stringCompare(fileData, responseIdx, query)) {
								pushResult(responseIdx);
								//System.out.print("RESULT -> ");
								//ByteStringUtils.printFromBuffer(fileData, responseIdx);
							}
						}
						
						// Optimization: Continue recursion if response starts from criteria 
						else if (queryEqResponse) {
							findAnagrams(query);
						}
					}
				}
			}
		}
	}

	private void addTabs(byte[] maskedWord) {
		for (int i = 0; i < maskedWord.length; i++) {
			System.out.print("\t");
		}
	}
}