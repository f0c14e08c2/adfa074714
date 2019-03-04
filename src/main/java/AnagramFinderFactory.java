// This factory intentionally is not singleton to don't spend time to class loading
public class AnagramFinderFactory {

    private static final SequentialAnagramFinder sequentialAnagramFinder = new SequentialAnagramFinder();
    private static final BTreeAnagramFinder btreeAnagramFinder = new BTreeAnagramFinder();
    
    private static final int EMPERICAL_THRESHOLD = 9;
    public AnagramFinder getAnagramFinder(byte[] searchWord) {
    	if (searchWord.length < 2) {
    		return null;
    	}

    	ByteStringUtils.sort(searchWord, 0, searchWord.length);
    	
    	int uniqueCharsSize = searchWord.length > 0 ? 1 : 0;
    	for (int i = 0; i < searchWord.length - 1; i++) {
			if (searchWord[i] != searchWord[i + 1]) {
				uniqueCharsSize++;
			}
		}
    	
    	if (uniqueCharsSize < 2) {
    		return null;
    	}

    	return uniqueCharsSize > EMPERICAL_THRESHOLD
    			? sequentialAnagramFinder 
    			: btreeAnagramFinder;
    }
}
