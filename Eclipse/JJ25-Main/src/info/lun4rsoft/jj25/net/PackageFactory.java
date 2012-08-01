package info.lun4rsoft.jj25.net;

import info.lun4rsoft.jj25.utils.DataConv;

public class PackageFactory {
	
	
	/**
	 * Creates the packet sent on port 10055 to an official Jazz Jackrabbit 1.23/1.24 listserver.
	 * 
	 * @param name Server's name. Trimmed at 31, use only ASCII and extended-ASCII (0..255)
	 * @param version 4 (extended-)ASCII chars, displayed after "1." as the servers version.
	 * @param maxplayers Max number of client connections.
	 * @param players Current number of connected clients
	 * @param gamemode The gamemode.
	 * @return the listserverdata packet.
	 */
	public static byte[] makeListServer24Data(String name, String version, int maxplayers, int players, int gamemode)
	{
		byte msg[] = {
			0x44,0x27, //checksum
			0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00, // 33 bytes reserved for the name.
			0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
			0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
			0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
			(byte)players, (byte)maxplayers,
			(byte)gamemode,
			0x00,0x00,0x00,0x00 //4 bytes reserved for the version.
			
		};
		
		byte namebytes[];
		byte versionbytes[];
		
		
		//Convert name & version to bytes
		if (name.length()>31)
		{
			namebytes = DataConv.StringToBytes(name, 0, 31);
		} else {
			namebytes = DataConv.StringToBytes(name);
		}
		
		if (version.length()>4)
		{
			versionbytes = DataConv.StringToBytes(version, 0, 4);
		} else {
			versionbytes = DataConv.StringToBytes(version);
		}
		
		//construct array
		
		for (int i=0; i<namebytes.length;i++)
		{
			msg[2+i]=namebytes[i];
		}
		
		for (int i=0; i<4;i++)
		{
			msg[38+i]=versionbytes[i];
		}
		
		return msg;
	}
	
	/**
	 * Makes the UDP pong packet, the reply to the ping packet sent by clients in 1.23/1.24.
	 * @param NumberInListFromPing Received in the ping-packet, use the same number. 
	 * @param unknown1 For the 4 unknown values, use the ones received from the ping-packet.
	 * @param unknown2 ?
	 * @param unknown3 ?
	 * @param unknown4 ?
	 * @param Gamemode The gamemode, use an ordinal from {@link info.lun4rsoft.jj25.server.list.ListServer24Settings}
	 * @return the pong packet, already checksummed.
	 */
	public static byte[] makePongData(byte NumberInListFromPing, byte unknown1, byte unknown2, byte unknown3, byte unknown4, byte Gamemode)
	{
		byte pong[] = {0,0,0x04,NumberInListFromPing,unknown1,unknown2,unknown3,unknown4,0x00};
		pong[8] = (byte) (Gamemode & 0x07);
		
		generate24UDPChecksum(pong);
		return pong;
	}
	
	/**
	 * Makes a Query Reply UDP packet, as sent by 1.23/1.24 servers.
	 * @param NumberInList as provided by the query packet.
	 * @param uptimeInSeconds uptime of the system, in seconds.
	 * @param version Version of the server, 4 ascii characters.
	 * @param players Current number of players in the server.
	 * @param maxplayers Max. number of players in the server.
	 * @param gamemode The gamemode, use an ordinal from {@link info.lun4rsoft.jj25.server.list.ListServer24Settings}
	 * @param servername The servername. Trimmed at 31 bytes.
	 * @return The query reply packet, already checksummed.
	 */
	public static byte[] makeQueryReplyData(byte NumberInList, int uptimeInSeconds, String version, byte players, byte maxplayers, byte gamemode,String servername)
	{
		while (version.length()<4)
		{
			version+=" "; //add space (0x20)
		}
		
		if (servername.length()>31)
		{
			servername = servername.substring(0, 31);
		}
		
		byte msg[] = new byte[19+servername.length()];
		
		msg[0] = msg[1] = 0;
		msg[2] = 0x06; // ID
		
		//Number in list:
		msg[3] = NumberInList;
		
		//Uptime
		msg[4] = (byte) uptimeInSeconds;
		msg[5] = (byte) (uptimeInSeconds>>>8);
		msg[6] = (byte) (uptimeInSeconds>>>16);
		msg[7] = (byte) (uptimeInSeconds>>>24);
		
		//Version
		msg[8] = version.getBytes()[0];
		msg[9] = version.getBytes()[1];
		msg[10] = version.getBytes()[2];
		msg[11] = version.getBytes()[3];
		
		//Players/gamemode
		msg[12] = 0x00; //Unknown...
		msg[13] = players; 
		msg[14] = gamemode;
		msg[15] = maxplayers;
		
		//Servername
		msg[16] = (byte) servername.length();
		
		int offset = 17;
		byte servernamebytes[] = servername.getBytes();
		for (int i = 0; i < servername.length(); i++) {
			msg[offset] = servernamebytes[i];
			offset++; //increase offset
		}
		
		//Ohter unknown
		msg[offset++] = 0x00;
		msg[offset++] = 0x00;
		
		//Checksum
		generate24UDPChecksum(msg);
		
		return msg;
	}
	
	public byte[] makeDisconnectClientPackage(byte socketID,DisconnectReason24 reason, JazzNetVersion version)
	{
		byte msg[] = new byte[8];
		
		return msg;
	}
	
	/**
	 * Creates the UDP Checksum wanted by JJ2 1.24 and down. <br>
	 *  
	 * @param buff The array to insert the checksum into.
	 */
	public static void generate24UDPChecksum(byte buff[])
	{
		// Let x and y be the first 2 bytes respectively
		// The Jazz2 internals initialize them with value of 1
		int x = 1, y = 1;
		// The main formula
		for (int i = 2; i < buff.length; i++)
		{
		  x += buff[i];
		  y += x;
		}
		// Finalising - no idea why Jazz2 does this
		buff[0] = (byte) (x % 251);
		buff[1] = (byte) (y % 251);
		return;

	}

}
