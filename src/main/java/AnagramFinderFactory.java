// This factory intentionally is not singleton to don't spend time to class loading
public class AnagramFinderFactory {

    private static final SequentialAnagramFinder sequentialAnagramFinder = new SequentialAnagramFinder();
    private static final BTreeAnagramFinder btreeAnagramFinder = new BTreeAnagramFinder();
    
    private static final int EMPERICAL_THRESHOLD = 9;
    public AnagramFinder getAnagramFinder(byte[] searchWord) {
    	
    	// Optimization: Skip whole validation if only one character is entered
    	if (searchWord.length < 2) {
    		return null;
    	}

    	byte[] searchWordSorted = new byte[searchWord.length];
		System.arraycopy(searchWord, 0, searchWordSorted, 0, searchWordSorted.length);
    	ByteStringUtils.sort(searchWordSorted, 0, searchWordSorted.length);
    	
    	int uniqueCharsSize = 1;
    	for (int i = 0; i < searchWordSorted.length - 1; i++) {
			if (searchWordSorted[i] != searchWordSorted[i + 1]) {
				uniqueCharsSize++;
			}
		}
    	
    	// Optimization: Skip whole validation if only one unique character is entered
    	if (uniqueCharsSize < 2) {
    		return null;
    	}
    	
    	return uniqueCharsSize > EMPERICAL_THRESHOLD
    			? sequentialAnagramFinder 
    			: btreeAnagramFinder;
    }
}
