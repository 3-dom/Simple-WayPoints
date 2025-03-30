package net.threeDom.simpleWaypoints.command

import net.threeDom.simpleWaypoints.command.commands.WayPointCommand
import net.threeDom.simpleWaypoints.command.commands.XYZCommand

object CommandManager
{
	private var commands: MutableMap<String, Command> = mutableMapOf()

	init
	{
		addCommand("wp", WayPointCommand)
		addCommand("xyz", XYZCommand)
	}

	fun getCommands(): MutableMap<String, Command>
	{
		return commands
	}

	private fun getCommand(key: String): Command?
	{
		val wp = commands[key] ?: return null

		return wp
	}

	private fun addCommand(key: String, cmd: Command)
	{
		if(commands.containsKey(key))
		{
			return
		}

		commands[key] = cmd
	}

	fun runCmd(input: String)
	{
		val args = input
			.split(" ")
			.toMutableList()
		var name = args.removeFirst()

		if(name.startsWith("."))
		{
			name = name.drop(1)
		}

		val cmd = getCommand(name)

		cmd?.process(args)
	}
}
