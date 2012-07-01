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
}