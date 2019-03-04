import org.junit.Assert;
import org.junit.Test;

public class ByteStringUtilsTest {

	@Test
	public void toUpperCase() {
		byte[] fileData = ByteStringUtils.convertToArrayStatic(
				"abc -abczäöõžabc");
		
			ByteStringUtils.toUpperCase(fileData, 3, 10);
			
			Assert.assertEquals("abc -ABCZÄÖÕŽabc",
					ByteStringUtils.convertToString(fileData, 0));
	}
	
	@Test
	public void toLowerCase() {
		byte[] fileData = ByteStringUtils.convertToArrayStatic(
				"ABC -ABCZÄÖÕŽABC");
		
			ByteStringUtils.toLowerCase(fileData, 3, 10);
			
			Assert.assertEquals("ABC -abczäöõžABC",
					ByteStringUtils.convertToString(fileData, 0));
	}
	
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
