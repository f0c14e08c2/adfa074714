// This factory intentionally is not singleton to don't spend time to class loading
public class AnagramFinderFactory {

    private static final SequentialAnagramFinder sequentialAnagramFinder = new SequentialAnagramFinder();
    private static final BTreeAnagramFinder btreeAnagramFinder = new BTreeAnagramFinder();
    
    private static final int EMPERICAL_THRESHOLD = 8;
    public AnagramFinder getAnagramFinder(String searchWord) {
    	
    	byte[] searchWordArr = ByteStringUtils.convertToArray(searchWord);
    	ByteStringUtils.sort(searchWordArr, 0, searchWordArr.length);
    	
    	int uniqueCharsSize = searchWordArr.length > 0 ? 1 : 0;
    	for (int i = 0; i < searchWordArr.length - 1; i++) {
			if (searchWordArr[i] != searchWordArr[i + 1]) {
				uniqueCharsSize++;
			}
		}

    	//System.out.println(uniqueCharsSize);
    	return uniqueCharsSize > EMPERICAL_THRESHOLD
    			? sequentialAnagramFinder 
    			: btreeAnagramFinder;
    }
}
