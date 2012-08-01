package info.lun4rsoft.jj25.utils;

/**
 * A class containing useful methods for converting data between various types.
 * <p>
 * All methods here should be <b>static</b>, the need to instantiate this class
 * should never arrive.
 * </p>
 * <p>
 * Users adding methods to DataConv are encouraged to do so, but are asked to
 * keep the following guidelines in mind:
 * <ul>
 * <li>Describe clearly what the method does, and where it could be useful. Be
 * sure to provide atleast a Javadoc. Additional comments in the code are encouraged.</li>
 * 
 * <li>Methods in DataConv should deal with alteration of Jazz25 data types to
 * one of the following:
 * <ul><li>Other Jazz25 Datatypes. <i>e.g: Palette to Palette color conversion</i>;</li>
 * <li>Plain Java datatypes. <i>e.g: Tileset's tile to one of Java's Image classes</i>;</li>
 * <li>Widespread 3rd party datatypes.<i>e.g: Tileset to PSD</i>;</li></ul>
 * Or the other way around. Java datatypes to other Javadatatypes is also allowed,
 * as long as it contributes something to Jazz25. Please use common sense here.
 * In other words, DataConv might need a <b>RGBAToBytes</b>, but not a 
 * <b>LevelToJohnniesFirstPluginsCoolDatatype</b>.
 * </li>
 * <li>DataConv methods should work with memory only. Don't write to files, or
 * communicate over an internet connection. If your conversion needs to do this,
 * write your own class. For common used filetypes like PNG, return the raw data
 * in a buffer and let the users handle the writing to file.</li>
 * 
 * </ul>
 * </p>
 * 
 * @author wKtK
 * @version 0.2.1.0
 */
public class DataConv {
	
	/**
	 * Constructs an int using the least significant bytes of a buffer.
	 * Used to convert ints split in 4 bytes in LSB buffers back to 1 int.
	 * 
	 * @param buf The buffer that contains the values.
	 * @param off The offset to start.
	 * @param len Howmany bytes to go from the offset. Should be (len &lt;= 4)
	 * @return The int assembled from the bytes.
	 */
	public static int LSBToInt(int buf[],int off,int len)
	{
		int x=0;
		
		for (int i =1;i<=len;i++)
		{
			x+=(buf[off+(len-i)] << (i-1)*8);
		}
		
		return x;
	}
	
	/**
	 * Constructs an int using the most significant bytes of a buffer.
	 * Used to convert ints split in 4 bytes in MSB buffers back to 1 int.
	 * 
	 * @param buf The buffer that contains the values.
	 * @param off The offset to start.
	 * @param len Howmany bytes to go from the offset. Should be (len &lt;= 4)
	 * @return The int assembled from the bytes.
	 */
	public static int MSBToInt(int buf[],int off,int len)
	{
		int x=0;
		
		for (int i =0;i<len;i++)
		{
			x+=(buf[off+i] << (i)*8);
		}
		
		return x;
	}
	
	/**
	 * Converts an array of bytes to a textual String, each byte converted
	 * into it's textual representation.
	 * <p>
	 * E.g. the following array <i>{67,97,107,101}</i> would result in <i>"Cake"</i>.
	 * </p>
	 * For a version that doesn't translate the bytes to characters, but
	 * retains their numerical value, see {@link DataConv#BytesValueToString(int[], int, int)}
	 * @param buf the buffer containing the bytes.
	 * @param off the offset to start at.
	 * @param len the number of bytes from the buffer to convert.
	 * @return A String of length <b>len</b>.
	 */
	public static String BytesToString(int buf[], int off, int len)
	{
		String out = "";
		
		for (int i=0;i<len;i++)
		{
			out += (char) buf[off+i];
		}
		
		return out;
	}
	
	/**
	 * Converts a String into an array of bytes. For strings passed over the net or
	 * to Jazz Jackrabbits internal routines, this should be preferred over str.getBytes()
	 * because if a version of an Operating System turns out to be incompatible with
	 * the charsets Jazz Jackrabbit (1.21 - 1.25) uses, this method will be expanded to include
	 * fixes.
	 * <p>The same as calling {@link DataConv#StringToBytes(String, int, int)} with (str, 0, str.length()).</p>
	 * @param str The String to convert to bytes.
	 * @return A byte array containing the Strings characters converted to bytes, using a charset
	 * compatible with Jazz Jackrabbit.
	 */
	public static byte[] StringToBytes(String str)
	{
		return str.getBytes();
	}
	
	/**
	 * Converts a String into an array of bytes. For strings passed over the net or
	 * to Jazz Jackrabbits internal routines, this should be preferred over str.getBytes()
	 * because if a version of an Operating System turns out to be incompatible with
	 * the charsets Jazz Jackrabbit (1.21 - 1.25) uses, this method will be expanded to include
	 * fixes.
	 * <p>Overloaded version to allow for offset and length.</p>
	 * @param str The string to convert to bytes.
	 * @param off Where to start.
	 * @param len How many characters to convert.
	 * @return A byte array containing the Strings characters converted to bytes, using a charset
	 * compatible with Jazz Jackrabbit.
	 */
	public static byte[] StringToBytes(String str, int off, int len)
	{
		return str.substring(off, off+len).getBytes();
	}
	
	/**
	 * Converts a buffer of bytes to a string, without converting the bytes to characters.
	 * <p>Mainly intended for debuggin or logging</p>
	 * @param buf The bytebuffer
	 * @param off Offset
	 * @param len Length
	 * @return A string consisting of the bytes' value, separated by ",".
	 * 
	 */
	public static String BytesValueToString(int buf[], int off, int len)
	{
		String out = "";
		
		for (int i=0;i<len;i++)
		{
			if (!out.equalsIgnoreCase(""))
			{
				out += ",";
			}
			out += ""+ buf[off+i];
		}
		
		return out;
	}
	
	/**
	 * Converts a buffer of bytes to a string, without converting the bytes to characters.
	 * <p>Mainly intended for debuggin or logging</p>
	 * @param buf The bytebuffer
	 * @param off Offset
	 * @param len Length
	 * @return A string consisting of the bytes' value, separated by ",".
	 * 
	 */
	public static String BytesValueToString(byte buf[], int off, int len)
	{
		String out = "";
		
		for (int i=0;i<len;i++)
		{
			if (!out.equalsIgnoreCase(""))
			{
				out += ",";
			}
			
			out += ""+ (int) buf[off+i];
		}
		
		return out;
	}
	
	/**
	 * Handles the bitshifting neccesary to convert 4 bytes containing red,
	 * green, blue and alpha to 1 single int in RGBA format.
	 * @param r Red component 0xFF000000
	 * @param g Green component 0x00FF0000
	 * @param b Blue component 0x0000FF00
	 * @param a Alpha component 0x000000FF
	 * @return The int containing the 4 values as RGBA.
	 */
	public static int RGBAToColor(int r, int g, int b, int a)
	{
		return (r << 24) + (g << 16) + (b << 8) + a;
	}
}