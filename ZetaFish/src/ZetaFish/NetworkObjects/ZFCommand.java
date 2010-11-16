package ZetaFish.NetworkObjects;

import java.io.Serializable;


public class ZFCommand implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public enum CommandType
	{
		NONE,
		START_GAME,
		NEW_GAME,
		
		TURN_DONE
	}
	
	private CommandType command = CommandType.NONE;
	
	public ZFCommand(CommandType cmd)
	{
		this.command = cmd;
	}
	
	public CommandType getCommand()
	{
		return this.command;
	}

}
