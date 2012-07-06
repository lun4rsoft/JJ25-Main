package info.lun4rsoft.jj25.tileset;

import java.io.FileNotFoundException;
import java.io.IOException;

import info.lun4rsoft.jj25.images.Palette;

public class Tileset {
	
	public static final int JJ24_TILESIZE = 32;
	
	private TilesetVersion version;
	private Palette palette;
	
	private int imageData[][][]; //[tile][x][y] = pal_index. This array consists only of unique tiles
	private boolean maskData[][][]; // " " " = solid y/n
	
	private int tileAddr[]; //The tiles address in imageData[][][].
	
	private boolean valid;
	
	public Tileset()
	{
		super();
		valid = false;
	}
	
	public boolean loadFromFile(String filename) throws IOException, FileNotFoundException
	{
		//initialization
		setValid(false);
		setVersion(TilesetVersion.UNKNOWN);
		//1.23 or 1.24:
		RawTileset24 raw = new RawTileset24();
		if (!raw.ReadFromFile(filename))
		{
			//Error...
			RawTilesetErrorcode err = raw.getErrorCode();
			
			switch (err) {
			case FILENOTFOUND:
				throw new FileNotFoundException("Tileset \""+filename+"\" could not be found...");
			case IOEXCEPTION:
				throw new IOException("IOExeption while parsing tileset \""+filename+"\"...");
			default:
				break;
			}
			
			return false;
		}
		return true;
	}

	public TilesetVersion getVersion() {
		return version;
	}

	public void setVersion(TilesetVersion version) {
		this.version = version;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
