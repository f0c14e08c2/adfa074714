import java.nio.charset.Charset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ByteStringTestUtils {

	public static byte[] convertStringsToArray(String ... strings) {
		byte[] fileData = Stream.of(strings)
		.collect(Collectors.joining("\r\n")).getBytes(Charset.forName("CP1257"));
		return fileData;
	}
}