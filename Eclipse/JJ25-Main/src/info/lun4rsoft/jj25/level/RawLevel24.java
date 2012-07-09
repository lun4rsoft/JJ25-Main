package info.lun4rsoft.jj25.level;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import info.lun4rsoft.jj25.files.JazzFile;
import info.lun4rsoft.jj25.utils.DataConv;
import info.lun4rsoft.jj25.zlib.FukkatsuZLib;

public class RawLevel24 extends JazzFile {
	
	//version identifiers.
	public static final int VER_23 = 0x200;
	public static final int VER_24 = 0x201;
	
	private int header[];
	
	private int buffer1[];
	private int buffer2[];
	private int buffer3[];
	private int buffer4[];
	
	private int version;
	
	private boolean valid;
	
	
	public RawLevel24()
	{
		setValid(false);
	}
	
	
	//Error codes:
		/*
		 * 0: OK
		 * 1: File not found
		 * 2: IO Exception
		 * 3: Empty File
		 * 4: Unexpected Header
		 * 5: Unable to set a lock (only on write)
		 */
	@Override
	public boolean ReadFromFile(String fullpath) {
		setValid(false);
		try {
			FileInputStream input_str = new FileInputStream(fullpath);
			
			//First, reading the header. This is always 262 bytes.
			header = new int[262];
			for (int i=0; i<header.length;i++)
			{
				header[i] = input_str.read();
			}
			
			//Validate if the file is valid.
			String magic = DataConv.BytesToString(header, 180, 4);
			if (!magic.equals("LEVL"))
			{
				//setError(RawTilesetErrorcode.FAULTYHEADER);
				System.out.println(magic);
				
				return false;
			}
			
			//After that, read the version.
			setVersion((header[221]<<8)+header[220]);
			
			//Store values in the header.
			
			for (int i =0;i<header.length;i++)
			{
				header[i] = (int)(header[i] & 0xFF);
			}
			
			//Then extract the compressed and decompressed sizes from the header.
			int CData[] = new int[4];
			int UData[] = new int[4];
			
			CData[0]=DataConv.MSBToInt(header, 230, 4);
			UData[0]=DataConv.MSBToInt(header, 234, 4);
			CData[1]=DataConv.MSBToInt(header, 238, 4);
			UData[1]=DataConv.MSBToInt(header, 242, 4);
			CData[2]=DataConv.MSBToInt(header, 246, 4);
			UData[2]=DataConv.MSBToInt(header, 250, 4);
			CData[3]=DataConv.MSBToInt(header, 254, 4);
			UData[3]=DataConv.MSBToInt(header, 258, 4);
			
			//Read and decompress buffers:
			int Cbuffer[];
			
			
			//--Read buffer 1
			Cbuffer = new int[CData[0]];
			for (int i=0; i<Cbuffer.length; i++)
			{
				Cbuffer[i]=input_str.read();
			}
			//--decompress
			buffer1 = FukkatsuZLib.jj2ZLibDecompress(Cbuffer, CData[0], UData[0]);
			
			//--Read buffer 2
			Cbuffer = new int[CData[1]];
			for (int i=0; i<Cbuffer.length; i++)
			{
				Cbuffer[i]=input_str.read();
			}
			//--decompress
			buffer2 = FukkatsuZLib.jj2ZLibDecompress(Cbuffer, CData[1], UData[1]);
			
			//--Read buffer 3
			Cbuffer = new int[CData[2]];
			for (int i=0; i<Cbuffer.length; i++)
			{
				Cbuffer[i]=input_str.read();
			}
			//--decompress
			buffer3 = FukkatsuZLib.jj2ZLibDecompress(Cbuffer, CData[2], UData[2]);
			
			//--Read buffer 4
			Cbuffer = new int[CData[3]];
			for (int i=0; i<Cbuffer.length; i++)
			{
				Cbuffer[i]=input_str.read();
			}
			//--decompress
			buffer4 = FukkatsuZLib.jj2ZLibDecompress(Cbuffer, CData[3], UData[3]);
			
			
			//Finally, close the file.
			input_str.close();
			
		} catch (FileNotFoundException e) {
			setValid(false);
			//setError(RawTilesetErrorcode.FILENOTFOUND);
			return false;
			
		} catch (IOException e) {
			setValid(false);
			//setError(RawTilesetErrorcode.IOEXCEPTION);
			return false;
		}
		return true;
	}

	@Override
	public boolean ReadFromFilePool(String name, boolean checkCached) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean WriteToFile(String fullpath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean WriteToFilePool(String name, boolean toCached) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public synchronized int[] getHeader() {
		return header;
	}
	
	public synchronized int[] getHeaderCopy() {
		return header.clone();
	}

	public synchronized void setHeader(int[] header) {
		this.header = header;
	}

	public synchronized int[] getBuffer1() {
		return buffer1;
	}
	
	public synchronized int[] getBuffer1Copy() {
		return buffer1.clone();
	}

	public synchronized void setBuffer1(int[] buffer1) {
		this.buffer1 = buffer1;
	}

	public synchronized int[] getBuffer2() {
		return buffer2;
	}
	
	public synchronized int[] getBuffer2Copy() {
		return buffer2.clone();
	}

	public synchronized void setBuffer2(int[] buffer2) {
		this.buffer2 = buffer2;
	}

	public synchronized int[] getBuffer3() {
		return buffer3;
	}
	
	public synchronized int[] getBuffer3Copy() {
		return buffer3.clone();
	}

	public synchronized void setBuffer3(int[] buffer3) {
		this.buffer3 = buffer3;
	}

	public synchronized int[] getBuffer4() {
		return buffer4;
	}
	
	public synchronized int[] getBuffer4Copy() {
		return buffer4.clone();
	}

	public synchronized void setBuffer4(int[] buffer4) {
		this.buffer4 = buffer4;
	}

	@Override
	public int getError() {
		// TODO Auto-generated method stub
		return 0;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}

}
