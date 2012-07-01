package info.lun4rsoft.jj25.tileset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import info.lun4rsoft.jj25.utils.DataConv;
import info.lun4rsoft.jj25.zlib.FukkatsuZLib;

public class RawTileset24 {
	
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
	
	private int error;
	
	public RawTileset24()
	{
		super();
		
		error = 0;
		valid = false;
	}
	
	public RawTileset24(String filename)
	{
		super();
		
		error = 0;
		valid = false;
		
		ReadfromFile(filename);
		
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
	public synchronized boolean ReadfromFile(String filename)
	{
		setValid(false);
		setError(0);
		try {
			FileInputStream input_str = new FileInputStream(filename);
			
			//First, reading the header. This is always 262 bytes.
			header = new int[262];
			for (int i=0; i<header.length;i++)
			{
				header[i] = input_str.read();
			}
			
			
			//Validate if the file is valid.
			String magic = DataConv.BytesToString(header, 180, 4);
			if (!magic.equals("TILE"))
			{
				setError(4);
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
			
			
			System.out.println("tiles: "+DataConv.MSBToInt(buffer1, 257, 4));
			
			//Finally, close the file.
			input_str.close();
		} catch (FileNotFoundException e) {
			setValid(false);
			setError(1);
			return false;
			
		} catch (IOException e) {
			setValid(false);
			setError(2);
			return false;
		}
		
		setValid(true);
		return true;
	}

	public synchronized int[] getHeader() {
		return header;
	}

	public synchronized void setHeader(int[] header) {
		this.header = header;
	}

	public synchronized int[] getBuffer1() {
		return buffer1;
	}

	public synchronized void setBuffer1(int[] buffer1) {
		this.buffer1 = buffer1;
	}

	public synchronized int[] getBuffer2() {
		return buffer2;
	}

	public synchronized void setBuffer2(int[] buffer2) {
		this.buffer2 = buffer2;
	}

	public synchronized int[] getBuffer3() {
		return buffer3;
	}

	public synchronized void setBuffer3(int[] buffer3) {
		this.buffer3 = buffer3;
	}

	public synchronized int[] getBuffer4() {
		return buffer4;
	}

	public synchronized void setBuffer4(int[] buffer4) {
		this.buffer4 = buffer4;
	}

	public synchronized int getError() {
		return error;
	}

	public synchronized void setError(int error) {
		this.error = error;
	}

	public synchronized int getVersion() {
		return version;
	}

	public synchronized void setVersion(int version) {
		this.version = version;
	}

	public synchronized boolean isValid() {
		return valid;
	}

	public synchronized void setValid(boolean valid) {
		this.valid = valid;
	}
}
