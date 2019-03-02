import java.io.File;

import com.google.common.base.Strings;

class AnagramApplication {
	
    private static final String FALLBACK_FILEPATH = "/home/seregia/Documents/projects/java/anagramApplication/src/main/resources/lemmad.txt";
    private static final String FALLBACK_WORD = "vits";

    private static final FileReader fileReader = new FileReader();
    private static final AnagramFinder anagramFinder = new AnagramFinder();
    
	public static void main(String[] argv) {
		
		final String filePath = argv.length >= 1 && !Strings.isNullOrEmpty(argv[0])
				? argv[0]
				: FALLBACK_FILEPATH;
				
		final String word = argv.length >= 2 && !Strings.isNullOrEmpty(argv[1])
				? argv[1]
				: FALLBACK_WORD;			
				
		File fileName = new File(filePath);
		if (!fileName.exists()) {
			System.out.println("Please provide dicionary file path as first argument");
			return;
		}

	    long startTime = System.nanoTime();

		anagramFinder.findAnagrams(
				fileReader.readFile(filePath),
				"vahelduvvoolugeneraator");
		
		String result = anagramFinder.getResults(true);
		
		long durationNs = System.nanoTime() - startTime;
	    System.out.println((durationNs / 1000) + "µs" + result);
	}
}
