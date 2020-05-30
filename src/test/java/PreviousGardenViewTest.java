
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

@SuppressWarnings("deprecation")
public class PreviousGardenViewTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void testLoadSavedGardens() throws IOException {
		 // Create a temporary file.
	     final File tempFile = tempFolder.newFile("tempFile.txt");
	   
	     // Write something to it.
	     //FileUtils.writeStringToFile(tempFile, "hello world");
	   
	     // Read it from temp file
	     final String s = FileUtils.readFileToString(tempFile);
	   
	     // Verify the content
	     Assert.assertEquals("test", s);
	}

}

