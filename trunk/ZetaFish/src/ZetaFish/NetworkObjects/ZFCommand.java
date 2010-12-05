package ZetaFish.NetworkObjects;

import java.io.Serializable;

/**
 * Network object used to send command to the server.
 * 
 * Design 4.6.5 v1.5
 */
public class ZFCommand implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Commands
	 */
	public enum CommandType
	{
		NONE,
		START_GAME,
		NEW_GAME,
		
		TURN_DONE
	}
	
	private CommandType command = CommandType.NONE;
	
	/**
	 * Constructor
	 * @param cmd Command
	 */
	public ZFCommand(CommandType cmd)
	{
		this.command = cmd;
	}
	
	/**
	 * Get the command
	 * @return CommandType
	 */
	public CommandType getCommand()
	{
		return this.command;
	}

}
