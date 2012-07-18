package info.lun4rsoft.jj25.utils;

public class DataConv {
	public static int LSBToInt(int buf[],int off,int len)
	{
		int x=0;
		
		for (int i =1;i<=len;i++)
		{
			x+=(buf[off+(len-i)] << (i-1)*8);
		}
		
		return x;
	}
	
	public static int MSBToInt(int buf[],int off,int len)
	{
		int x=0;
		
		for (int i =0;i<len;i++)
		{
			x+=(buf[off+i] << (i)*8);
		}
		
		return x;
	}
	
	public static String BytesToString(int buf[], int off, int len)
	{
		String out = "";
		
		for (int i=0;i<len;i++)
		{
			out += (char) buf[off+i];
		}
		
		return out;
	}
	
	public static byte[] StringToBytes(String str)
	{
		return str.getBytes();
	}
	
	public static byte[] StringToBytes(String str, int off, int len)
	{
		return str.substring(off, off+len).getBytes();
	}
	
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
	
	public static int RGBAToColor(int r, int g, int b, int a)
	{
		return (r << 24) + (g << 16) + (b << 8) + a;
	}
}