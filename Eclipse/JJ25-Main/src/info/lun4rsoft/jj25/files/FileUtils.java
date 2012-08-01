package info.lun4rsoft.jj25.files;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * A class containing <b>static</b> methods to simplify dealing with files, the filesystem,
 * and writing/reading content to files.
 * @author wKtK
 * @version 1.0.0.0
 */
public class FileUtils {
	/**
	 * Gets the local Fileseparator, this is the character between items in a path.
	 * <p>
	 * E.G: '/' on Linux, '\' on Windows.
	 * </p>
	 * @return A String containing the FileSeparator for this OS.
	 */
	public static String getFileSeparator()
	{
		return System.getProperty("file.separator", "/");
	}
	
	/**
	 * Returns the local newline character(s). This is the data that causes a newline
	 * in textual files. Often '\n'.
	 * @return A String containing the Newline character(s) for this OS.
	 */
	public static String getLineSeparator()
	{
		return System.getProperty("line.separator", "\n");
	}
	
	public static File[] listFilesMatching(String dir, String regex) {
	    File root = new File(dir);
	    
		if(!root.isDirectory()) {
	        throw new IllegalArgumentException(root+" is no directory.");
	    }
	    final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
	    return root.listFiles(new FileFilter(){
	        @Override
	        public boolean accept(File file) {
	            return p.matcher(file.getName()).matches();
	        }
	    });
	}
}
