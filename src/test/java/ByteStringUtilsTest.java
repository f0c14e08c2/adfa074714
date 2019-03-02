import org.junit.Assert;
import org.junit.Test;

public class ByteStringUtilsTest {

	@Test
	public void locateFirstWord() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"b-vitamiin");
		
			int stringLocation = ByteStringUtils.locateSolidWord(fileData, 0);
			
			Assert.assertEquals(stringLocation, 0);
	}
	
	@Test
	public void locateWordByFirstChar() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"\r\nb-vitamiin");
		
			int stringLocation = ByteStringUtils.locateSolidWord(fileData, 2);
			
			Assert.assertEquals(stringLocation, 2);
	}
	
	@Test
	public void locateNextWord() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"012",
				"567");
		
			int stringLocation = ByteStringUtils.locateSolidWord(fileData, 2);
			
			Assert.assertEquals(5, stringLocation);
	}
	
	@Test
	public void locatePreviousWord() {
		byte[] fileData = ByteStringTestUtils.convertStringsToArray(
				"012",
				"567");
		
			int stringLocation = ByteStringUtils.locateSolidWord(fileData, 1);
			
			Assert.assertEquals(0, stringLocation);
	}
}
