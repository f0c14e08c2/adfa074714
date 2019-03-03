import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class BinaryFinderTest {

	@Test
	public void locateSingleChar() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"a",
				"b");
		BinaryFinder binaryFinder = new BinaryFinder();
		binaryFinder.setFileData(fileData);
		
		int stringLocation = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("a"));
		
		Assert.assertEquals("a", ByteStringUtils.convertToString(fileData, stringLocation));
	}

	@Test
	public void locateRangeStart() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"Aadam",
				"AcCilleus",
				"Achilleus",
				"Aleksander",
				"Hiina viisakus",
				"a cappella",
				"uvertüür",
				"uw",
				"v-kesksõna",
				"vastupidamine",
				"vastupidavustreening",
				"vastupidi",
				"ž");
		BinaryFinder binaryFinder = new BinaryFinder();
		binaryFinder.setFileData(fileData);
		
		int stringLocationAaa = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("Aaa"));
		int stringLocationAc = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("Ac"));
		int stringLocationAl = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("Al"));
		int stringLocationH = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("H "));
		int stringLocationA = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("a"));
		int stringLocationU = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("u"));
		int stringLocationVdash = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("v-"));
		int stringLocationVastupidami = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("vastupidami"));
		int stringLocationVastupidavu = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("vastupidavu"));
		int stringLocationVe = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray("ve"));
		
		Assert.assertEquals("Aadam", ByteStringUtils.convertToString(fileData, stringLocationAaa));
		Assert.assertEquals("AcCilleus", ByteStringUtils.convertToString(fileData, stringLocationAc));
		Assert.assertEquals("Aleksander", ByteStringUtils.convertToString(fileData, stringLocationAl));
		Assert.assertEquals("Hiina viisakus", ByteStringUtils.convertToString(fileData, stringLocationH));
		Assert.assertEquals("a cappella", ByteStringUtils.convertToString(fileData, stringLocationA));
		
		Assert.assertEquals("v-kesksõna", ByteStringUtils.convertToString(fileData, stringLocationVdash));
		Assert.assertEquals("uvertüür", ByteStringUtils.convertToString(fileData, stringLocationU));
		Assert.assertEquals("vastupidamine", ByteStringUtils.convertToString(fileData, stringLocationVastupidami));
		Assert.assertEquals("vastupidavustreening", ByteStringUtils.convertToString(fileData, stringLocationVastupidavu));
		Assert.assertEquals("ž", ByteStringUtils.convertToString(fileData, stringLocationVe));
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
				"žmuud",
				"žurnalist",
				"žurnalistika",
				"žürii")
				
		.collect(Collectors.toList());
		
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(dataList.toArray(new String[]{}));
		BinaryFinder binaryFinder = new BinaryFinder();
		binaryFinder.setFileData(fileData);
		
		dataList.forEach(query -> {
			int stringLocation = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArray(query));
			Assert.assertEquals(query, ByteStringUtils.convertToString(fileData, stringLocation));
		});
	}
}
