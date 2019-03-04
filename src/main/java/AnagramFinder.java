public abstract class AnagramFinder {

	protected byte[] fileData;
	protected byte[] searchWordSorted;
	protected byte[] searchWordOriginal;
    
	public static final String STR_COMMA = ",";
	
    private static final int MAX_RESULTS = 100;
    private int resultsSize = 0;
    private int[] resultsData = new int[MAX_RESULTS];
    
    
	public abstract void findAnagrams(byte[] fileData, byte[] searchWord);
    
    
    protected void pushResult(int resultIdx) {
    	if (resultsSize < MAX_RESULTS) {
    		resultsData[resultsSize++] = resultIdx;
    	} else {
    		throw new IllegalStateException("Too many results please increase MAX_RESULTS variable");
    	}
    }
    
    public int popResult() {
    	return resultsSize > 0 
    			? resultsData[--resultsSize] 
    			: -1;
    }
    
	public String getResults(boolean skipOriginal) {
		StringBuilder sb = new StringBuilder();
		int resultIdx = -1;
		while ((resultIdx = popResult()) >= 0) {
			boolean skip = skipOriginal && (0 == ByteStringUtils.stringCompareIgnoreCase(fileData, resultIdx, searchWordOriginal));
			if (!skip) {
			sb	.append(STR_COMMA)
				.append(ByteStringUtils.convertToString(fileData, resultIdx));
			}
		}
		return sb.toString();
	}
}