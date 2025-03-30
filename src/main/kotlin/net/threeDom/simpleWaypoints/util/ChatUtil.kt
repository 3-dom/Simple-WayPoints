package net.threeDom.simpleWaypoints.util

import net.minecraft.text.Style
import net.minecraft.text.Text
import net.threeDom.simpleWaypoints.client.SimpleWaypointsClient

object ChatUtil
{

	fun printError(msg: String)
	{
		printMsg("Error", msg, 0xFF5555)
	}

	fun printInfo(msg: String)
	{
		printMsg("Info", msg, 0x55FF55)
	}

	private fun printMsg(label: String, msg: String, color: Int)
	{
		val mc = SimpleWaypointsClient.mc

		// Fuck this gay ass text parsing object.
		val txt = Text
			.literal("[")
			.append(Text
				.literal(label)
				.setStyle(Style.EMPTY.withColor(color)))
			.append(Text.of("] $msg"))

		mc.inGameHud.chatHud.addMessage(txt)
	}
}