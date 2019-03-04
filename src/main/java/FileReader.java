import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {
	
	static {
		// Preload classes
		File.class.getName();
		DataInputStream.class.getName();
		FileInputStream.class.getName();
		IOException.class.getName();
		RuntimeException.class.getName();
	}

	public byte[] readFile(String pathToFile) {
	    
	    File file = new File(pathToFile);
	    byte[] fileData = new byte[(int)file.length()];
	    DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
		    dis.readFully(fileData);
		    dis.close();
	    
		} catch ( IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return fileData;
	}
}