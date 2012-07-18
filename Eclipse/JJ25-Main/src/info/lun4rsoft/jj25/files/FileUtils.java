package info.lun4rsoft.jj25.files;

public class FileUtils {
	public static String getFileSeparator()
	{
		return System.getProperty("file.separator", "/");
	}
	
	public static String getLineSeparator()
	{
		return System.getProperty("line.separator", "\n");
	}
}
