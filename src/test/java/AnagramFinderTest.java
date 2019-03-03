import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class AnagramFinderTest {

	@Test
	public void findAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"Madam Curie",
				"livEs",
				"lives",
				"rail safety"
				);
		AnagramFinder anagramFinder;
		String result;
		
		anagramFinder = new AnagramFinder();
		anagramFinder.findAnagrams(fileData, "Radium came");
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",Madam Curie", result);
		
		anagramFinder = new AnagramFinder();
		anagramFinder.findAnagrams(fileData, "Elvis");
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",lives,livEs", result);
		
		anagramFinder = new AnagramFinder();
		anagramFinder.findAnagrams(fileData, "fairy tales");
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",rail safety", result);
	}
	
	@Test
	public void findSimpleAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"abc",
				"bca",
				"cab");
		AnagramFinder anagramFinder = new AnagramFinder();
		
		anagramFinder.findAnagrams(fileData, "cab");
		
		String result = anagramFinder.getResults(false);

		Assert.assertEquals(",cab,bca,abc", result);
	}

	@Test
	public void findCaseInsensitiveAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"ABC",
				"BcA",
				"aBC",
				"cab");
		AnagramFinder anagramFinder = new AnagramFinder();
		
		anagramFinder.findAnagrams(fileData, "CaB");
		
		String result = anagramFinder.getResults(true);

		Assert.assertEquals(",aBC,BcA,ABC", result);
	}
	

	@Test
	public void findLongSingleAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"vastupidavustreening");
		AnagramFinder anagramFinder = new AnagramFinder();
		
		anagramFinder.findAnagrams(fileData, "vastupidavustreening");
		
		String result = anagramFinder.getResults(false);

		Assert.assertEquals(",vastupidavustreening", result);
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
				"fiskaalpoliitika",
				"uvertüür",
				"v-kesksõna",
				"vastupidamine",
				"vastupidav",
				"vastupidavus",
				"vastupidavusharjutus",
				"vastupidavustreening",
				"vastupidi",
				"ž",
				"ženšennitinktuur",
				"žmuud",
				"žurnalist",
				"žurnalistika",
				"žürii")
				
		.collect(Collectors.toList());
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(dataList.toArray(new String[]{}));
			
		dataList.forEach(query -> {
			AnagramFinder anagramFinder = new AnagramFinder();
			anagramFinder.findAnagrams(fileData, query);
			
		String result = anagramFinder.getResults(false);
		
		Assert.assertEquals("," + query, result);
		});
	}
}
