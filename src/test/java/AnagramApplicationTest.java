import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class AnagramApplicationTest {

	@Test
	public void findAllAnagram() {
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
		byte[] fileData = convertToArray(dataList.toArray(new String[]{}));
		
		AnagramApplication.setFileData(fileData);
		dataList.forEach(query -> {
			AnagramApplication.setSearchWord(convertToArray(query));
			AnagramApplication.findAnagram(new byte[]{});
			
			//Assert.assertEquals(query, convertToString(fileData, stringLocation));
		});
	}
	
	@Test
	public void locateAllWords() {
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
		byte[] fileData = convertToArray(dataList.toArray(new String[]{}));
		
		dataList.forEach(query -> {
			int stringLocation = AnagramApplication.locatePositionByString(fileData, convertToArray(query));
			Assert.assertEquals(query, convertToString(fileData, stringLocation));
		});
	}
	
	@Test
	public void locateFirstWord() {
		byte[] fileData = convertToArray(
				"b-vitamiin");
		
			int stringLocation = AnagramApplication.locateSolidWord(fileData, 0);
			
			Assert.assertEquals(stringLocation, 0);
	}
	
	@Test
	public void locateWordByFirstChar() {
		byte[] fileData = convertToArray(
				"\r\nb-vitamiin");
		
			int stringLocation = AnagramApplication.locateSolidWord(fileData, 2);
			
			Assert.assertEquals(stringLocation, 2);
	}
	
	@Test
	public void locateNextWord() {
		byte[] fileData = convertToArray(
				"012",
				"567");
		
			int stringLocation = AnagramApplication.locateSolidWord(fileData, 2);
			
			Assert.assertEquals(5, stringLocation);
	}
	
	@Test
	public void locatePreviousWord() {
		byte[] fileData = convertToArray(
				"012",
				"567");
		
			int stringLocation = AnagramApplication.locateSolidWord(fileData, 1);
			
			Assert.assertEquals(0, stringLocation);
	}
	
	
	@Test
	public void locateRangeStart() {
		byte[] fileData = convertToArray(
				"uvertüür",
				"v-kesksõna");
		
			int stringLocation = AnagramApplication.locatePositionByString(fileData, convertToArray("v"));
			
			Assert.assertEquals("v-kesksõna", convertToString(fileData, stringLocation));
	}
	
	private String convertToString(byte[] fileData, int stringLocation) {
		int stringSize = AnagramApplication.stringSize(fileData, stringLocation);
		
		String ret = new String(fileData, stringLocation, stringSize, Charset.forName("CP1257"));
		System.out.println(ret);
		
		return ret;
	}

	private byte[] convertToArray(String ... strings) {
		byte[] fileData = Stream.of(strings)
		.collect(Collectors.joining("\r\n")).getBytes(Charset.forName("CP1257"));
		return fileData;
	}

}
