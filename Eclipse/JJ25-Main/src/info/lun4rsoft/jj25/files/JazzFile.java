package info.lun4rsoft.jj25.files;

public abstract class JazzFile {
	public abstract boolean ReadFromFile(String fullpath);
	public abstract boolean ReadFromFilePool(String name,boolean checkCached);
	
	public abstract boolean WriteToFile(String fullpath);
	public abstract boolean WriteToFilePool(String name,boolean toCached);
	
	public abstract int getError();
}
