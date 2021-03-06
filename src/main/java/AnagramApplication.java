import java.io.File;

class AnagramApplication {

    private static final FileReader fileReader = new FileReader();
    private static final AnagramFinderFactory anagramFinderFactory = new AnagramFinderFactory();
    private static final ByteStringUtils byteStringUtils = new ByteStringUtils();
    
	public static void main(String[] argv) {
		
		if (argv.length < 1) {
			System.out.println("Please provide dicionary file path as first argument");
			return;
		} else if (argv.length < 2) {
			System.out.println("Please provide search criteria as second argument");
			return;
		}
				
		File fileName = new File(argv[0]);
		if (!fileName.exists()) {
			System.out.println("Please file '" + argv[0] + "' not found");
			return;
		}

	    long startTime = System.nanoTime();
	    String result = "";
	    
	    byte[] searchWord = byteStringUtils.convertToArray(argv[1]);
	    AnagramFinder anagramFinder = anagramFinderFactory.getAnagramFinder(searchWord);
		if (anagramFinder != null) {
		    anagramFinder.findAnagrams(
		    		fileReader.readFile(argv[0]),
					searchWord);
			
			result = anagramFinder.getResults(true);
		}
		
		long durationNs = System.nanoTime() - startTime;
	    System.out.println((durationNs / 1000) + result);
	}
}
