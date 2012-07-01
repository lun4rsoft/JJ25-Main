package info.lun4rsoft.jj25.zlib;

public class FukkatsuZLib {
	
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
