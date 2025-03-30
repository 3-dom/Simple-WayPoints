package net.threeDom.simpleWaypoints.command

abstract class Command
{

	abstract val cmd: String
	abstract val desc: String

	abstract fun process(input: List<String>)
}