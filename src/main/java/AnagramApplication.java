import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.TreeSet;

import com.google.common.base.Strings;

class AnagramApplication {
	
    private static final String FALLBACK_FILEPATH = "/home/seregia/Documents/projects/java/anagramApplication/src/main/resources/lemmad.txt";
    private static final String FALLBACK_WORD = "vits";

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

			
	    long startTime = System.currentTimeMillis();
	    String result = "";
	    
	    RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(fileName, "r");
			
	        file.seek(0);
	        
	        TreeSet<Character> lineChSet = new TreeSet<>(); 
	        TreeSet<Character> wordChSet = new TreeSet<>();
	        
	        while (file.getFilePointer() < file.length()) {
	        	String line = file.readLine();
	        	
	        	if (word.length() == line.length()) {
	        		lineChSet.clear();
	        		wordChSet.clear();
	        		
	        		for (int i = 0; i < word.length(); i++) {
						Character wordCh = word.charAt(i);
						Character lineCh = line.charAt(i);
						
						wordChSet.add(wordCh);
						lineChSet.add(lineCh);
					} 
	        		
	        		if (wordChSet.equals(lineChSet)) {
	        		    System.out.println("Matched: " + line);
	        		}
	        	}
	        }
	        
	        //file.readFully(bytes, 0, (int)file.length());
	        //bytes=null;
	        //file.read(bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		long stop = System.currentTimeMillis() - startTime;
	    System.out.println(stop + result);
	}
}