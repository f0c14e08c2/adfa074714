import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class AnagramFinderTest {

	@Test
	public void findAllOriginalAnagrams2() {
		List<String> dataList = Stream.of(
				"Aleksander",
				"Hiina viisakus",
				"Pandora laegas")
				
		.collect(Collectors.toList());
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(dataList.toArray(new String[]{}));
		
		AnagramFinder anagramFinder = new AnagramFinder();
		
		dataList.forEach(query -> {
			anagramFinder.findAnagrams(fileData, query);
			
		String result = anagramFinder.getResults(false);
		
		Assert.assertEquals("," + query, result);
		});
	}
	
	@Test
	public void findAllOriginalAnagrams() {
		List<String> dataList = Stream.of(
				"AIDS",
				"Aadam",
				"Achilleus",
				"Aleksander",
				"Hiina viisakus",
				"Pandora laegas",
				"a",
				"a cappella",
				"a-vitamiin",
				"aa",
				"ažuurtikand",
				"b-vitamiin",
				"uvertüür",
				"v-kesksõna",
				"vastupidamine",
				"vastupidav",
				"vastupidavus",
				"vastupidavusharjutus",
				"vastupidavustreening",
				"vastupidi",
				"ž",
				"žmuud",
				"žurnalist",
				"žurnalistika",
				"žürii")
				
		.collect(Collectors.toList());
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(dataList.toArray(new String[]{}));
		
		AnagramFinder anagramFinder = new AnagramFinder();
		
		dataList.forEach(query -> {
			anagramFinder.findAnagrams(fileData, query);
			
		String result = anagramFinder.getResults(false);
		
		Assert.assertEquals("," + query, result);
		});
	}
}
