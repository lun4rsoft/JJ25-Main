package info.lun4rsoft.jj25.files;

/**
 * Represents the filepool. This is the place where the content gets stored. Unless
 * configured otherwise, the filepool contains the following (and more):
 * <ul>
 * <li>Levels (*.j2l;*.j25l)</li>
 * <li>Tilesets (*.j2t;*.j25t)</li>
 * <li>Graphic data (*.j2a;*.j25a)</li>
 * <li>Other resources like Language-files and Episodes.</li>
 * <li>Settings/logs</li>
 * <li>The cache directory.</li>
 * </ul>
 * @author wKtK
 * @version 1.0.0.0
 */
public class FilePool {

	private static String poolPath = "";

	/**
	 * Returns the path to the Filepool, excluding the final separator ('/' or '\')
	 * @return The path to the Filepool.
	 */
	public static String getPoolPath() {
		return poolPath;
	}

	/**
	 * Set the path of the Filepool. The final separator is removed, if one is present.
	 * Always use the separator for the local system.
	 * @param poolPath The new path to the Filepool.
	 */
	public static void setPoolPath(String poolPath) {
		FilePool.poolPath = poolPath;
	}
	
	
}
