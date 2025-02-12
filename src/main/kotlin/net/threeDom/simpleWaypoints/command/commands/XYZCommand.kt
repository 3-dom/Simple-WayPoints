package net.threeDom.simpleWaypoints.command.commands

import net.threeDom.simpleWaypoints.client.SimpleWaypointsClient
import net.threeDom.simpleWaypoints.command.Command

object XYZCommand : Command() {
	override val cmd: String = "xyz"
	override val desc: String = "Sends a message including your current coOrds."

	override fun process(input: List<String>) {
		val mc = SimpleWaypointsClient.mc;
		val player = mc.player ?: return

		val x = player.x.toInt()
		val y = player.y.toInt()
		val z = player.z.toInt()

		player.networkHandler.sendChatMessage("X:$x, Y:$y, Z:$z")
	}
}