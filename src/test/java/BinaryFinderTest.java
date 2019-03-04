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
		
		int stringLocation = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic(("a")));
		
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
		
		int stringLocationAaa = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("Aaa"));
		int stringLocationAc = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("Ac"));
		int stringLocationAl = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("Al"));
		int stringLocationH = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("H "));
		int stringLocationA = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("a"));
		int stringLocationU = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("u"));
		int stringLocationVdash = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("v-"));
		int stringLocationVastupidami = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("vastupidami"));
		int stringLocationVastupidavu = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("vastupidavu"));
		int stringLocationVe = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic("ve"));
		
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
			int stringLocation = binaryFinder.locateNearestWordPosition(ByteStringUtils.convertToArrayStatic(query));
			Assert.assertEquals(query, ByteStringUtils.convertToString(fileData, stringLocation));
		});
	}
}
