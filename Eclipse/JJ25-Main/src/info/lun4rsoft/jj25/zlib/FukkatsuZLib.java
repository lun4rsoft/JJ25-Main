package info.lun4rsoft.jj25.zlib;

/**
 * A class inplementing simple methods and helpers to compress and decompress ZLib compressed buffers,
 * as used by JJ2. It uses the opensource JZlib project as it's backend, of which a slightly
 * modified version is included in the info.lun4rsoft.jj25.zlib package.
 * <p>
 * For more info, check out the JZlib project at <a href="http://www.jcraft.com/jzlib/">http://www.jcraft.com/jzlib/</a>.
 * </p>
 * @author wKtK
 * @version 1.2.1.0
 */
public class FukkatsuZLib {
	
	/**
	 * Inflates a zlib compressed buffer of bytes back to the original content.
	 * This uses the same compression all JJ1.24 and back use for levels, tilesets etc. 
	 * @param buff The buffer containing the compressed data.
	 * @param CSize The number of bytes in the compressed buffer.
	 * @param USize The number of bytes in the decompressed buffer.
	 * @return The original, decompressed/inflated data.
	 */
	@SuppressWarnings("deprecation")
	public static int[] jj2ZLibDecompress(int[] buff, int CSize, int USize) {
		int[] output = new int[USize];
		
		byte[] tmpout = new byte[USize];
		byte[] tmpin = new byte[CSize];
		
		for (int i = 0;i<CSize;i++) {
			tmpin[i] = (byte) (buff[i]);
		}
		
		
		int err;
		
		 Inflater inflater = new Inflater();

		    inflater.setInput(tmpin);
		    inflater.setOutput(tmpout);

		    err=inflater.init();
		    

		    while(inflater.total_out<USize &&
		      inflater.total_in<CSize) {
		      inflater.avail_in=inflater.avail_out=1; /* force small buffers */
		      err=inflater.inflate(JZlib.Z_NO_FLUSH);
		      if(err==JZlib.Z_STREAM_END) break;
		    }

		    err=inflater.end();
		    
		    for (int i = 0; i<USize; i++) {
		    	output[i] = (int) (tmpout[i] & 0xFF);
		    }
		
		return output;
	}

}
