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
		BTreeAnagramFinder anagramFinder;
		String result;
		
		anagramFinder = new BTreeAnagramFinder();
		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("radium CaMe"));
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",Madam Curie", result);
		
		anagramFinder = new BTreeAnagramFinder();
		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("Elvis"));
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",livEs", result);
		
		anagramFinder = new BTreeAnagramFinder();
		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("fairy tales"));
		result = anagramFinder.getResults(false);
		Assert.assertEquals(",rail safety", result);
	}
	
	@Test
	public void findSimpleAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"abc",
				"bca",
				"cab");
		BTreeAnagramFinder anagramFinder = new BTreeAnagramFinder();
		
		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("cab"));
		
		String result = anagramFinder.getResults(false);

		Assert.assertEquals(",cab,bca,abc", result);
	}

//	@Test
//	public void findCaseInsensitiveAnagrams() {
//		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
//				"ABC",
//				"BcA",
//				"aBC",
//				"cab");
//		BTreeAnagramFinder anagramFinder = new BTreeAnagramFinder();
//		
//		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("CaB"));
//		
//		String result = anagramFinder.getResults(true);
//
//		Assert.assertEquals(",aBC,BcA,ABC", result);
//	}
//	

	@Test
	public void findLongSingleAnagrams() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"vastupidavustreening");
		BTreeAnagramFinder anagramFinder = new BTreeAnagramFinder();
		
		anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic("vastupidavustreening"));
		
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
			BTreeAnagramFinder anagramFinder = new BTreeAnagramFinder();
			anagramFinder.findAnagrams(fileData, ByteStringUtils.convertToArrayStatic(query));
			
		String result = anagramFinder.getResults(false);
		
		Assert.assertEquals("," + query, result);
		});
	}
}
