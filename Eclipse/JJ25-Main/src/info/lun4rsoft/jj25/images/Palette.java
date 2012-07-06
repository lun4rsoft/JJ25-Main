package info.lun4rsoft.jj25.images;

import info.lun4rsoft.jj25.utils.DataConv;

public class Palette {
	private int index[];
	private int colorCount;
	
	private int transIndex;
	
	public Palette()
	{
		super();
	}
	
	public boolean loadFrom24Data(byte data[], int off)
	{
		colorCount = 256;
		index = new int[colorCount];
		//Always black, which is the first color.
		transIndex =0;
		
		for (int i = 0; i < colorCount; i++) {
			index[i] = DataConv.RGBAToColor(data[off+(i*4)], data[off+(i*4)]+1, data[off+(i*4)]+2, data[off+(i*4)]+3);
		}
		
		
		return true;
	}
}
