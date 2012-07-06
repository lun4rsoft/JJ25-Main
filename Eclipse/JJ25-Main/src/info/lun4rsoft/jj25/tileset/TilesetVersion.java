package info.lun4rsoft.jj25.tileset;

public enum TilesetVersion {
	UNKNOWN, //not yet set/unknown format
	JJ23,  //official jj2 tileset: 1.23 and lower
	JJ24, //official jj2 tileset: 1.24 and spinoffs (TSF, CC, etc)
	JJ25, //Jazz25 Tileset. Support for 16-bit colors and a few other features, tba later.
	JJ25HD, //Jazz25HD Tileset. Support for 32-bit colors, sizes up to 128x128 and bumpmaps, along more.
			//support for these will be slowly added once the main system is in place. Don't get your hopes up yet.
	PLUGIN //Custom user-defined format.
}
