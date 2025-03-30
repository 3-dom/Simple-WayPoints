package net.threeDom.simpleWaypoints.util

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.item.ItemStack
import net.threeDom.simpleWaypoints.client.SimpleWaypointsClient.wpm
import net.threeDom.simpleWaypoints.util.MathUtil.q_rsqrt
import net.threeDom.simpleWaypoints.waypoint.WayPoint
import kotlin.math.abs

object GuiUtil
{
	fun createGPS(tr: TextRenderer?, context: DrawContext, wp: WayPoint?, player: ClientPlayerEntity)
	{
		if(wp == null) return
		if(wp.coOrds.size != 3) return

		val dX: Int = wp.coOrds[0]
		val dY = wp.coOrds[1]
		val dZ = wp.coOrds[2]

		val cX = player.x.toInt()
		val cY = player.y.toInt()
		val cZ = player.z.toInt()

		val oX = abs((cX - dX).toDouble()).toInt()
		val oY = abs((cY - dY).toDouble()).toInt()
		val oZ = abs((cZ - dZ).toDouble()).toInt()

		val dist = q_rsqrt((oX * oX + oY * oY + oZ * oZ).toFloat())

		val current = "Pos: $cX, $cY, $cZ"
		val dest = wp.name + ": $dX, $dY, $dZ"
		val offset = "Offset: $oX, $oY, $oZ"

		context.drawText(tr, current, 2, 2, 0xFFFFFF, true)
		context.drawText(tr, dest, 2, 12, 0xFFFFFF, true)
		context.drawText(tr, offset, 2, 22, 0xFFFFFF, true)

		if(dist > 0.15 && player.health > 0)
		{
			wpm.clearActiveWaypoint()
		}
	}

	fun renderItemHealth(context: DrawContext, tr: TextRenderer, `is`: ItemStack)
	{		// Simple dmg calc, doesn't take enchantment into consideration yet.
		val damage = `is`.maxDamage - `is`.damage
		val damageString = damage.toString()

		// Screensize calc
		val x = (context.scaledWindowWidth / 2) - (tr.getWidth(damageString) / 2)
		val y = context.scaledWindowHeight - 45

		// Render item health
		context.drawText(tr, damageString, x, y, 0xFFFFFF, true)
	}
}