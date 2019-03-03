import java.io.File;

class AnagramApplication {

    private static final FileReader fileReader = new FileReader();
    private static final AnagramFinder anagramFinder = new AnagramFinder();
    
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

		//while (true) {
		    long startTime = System.nanoTime();
			anagramFinder.findAnagrams(
					fileReader.readFile(argv[0]),
					argv[1]);
			
			String result = anagramFinder.getResults(true);
	
			long durationNs = System.nanoTime() - startTime;
		    System.out.println((durationNs / 1000) + "µs" + result);
		//}
	}
}
