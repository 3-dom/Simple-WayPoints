package net.threeDom.simpleWaypoints.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.item.ItemStack
import net.threeDom.simpleWaypoints.command.CommandManager
import net.threeDom.simpleWaypoints.util.ChatUtil
import net.threeDom.simpleWaypoints.util.GuiUtil
import net.threeDom.simpleWaypoints.waypoint.WayPointManager

object SimpleWaypointsClient : ClientModInitializer {
    val mc: MinecraftClient = MinecraftClient.getInstance()
    var wpm: WayPointManager = WayPointManager
    val cu: ChatUtil = ChatUtil

    private val cm: CommandManager = CommandManager
    private val gu: GuiUtil = GuiUtil

    override fun onInitializeClient() {
        wpm.loadMapFile()
        var isDead = false

        HudRenderCallback.EVENT.register { drawContext, _ ->
            val wp = wpm.getActiveWaypoint()
            val tr: TextRenderer = mc.textRenderer

            // Ensure the player exists (this should never fail)
            val player = mc.player ?: return@register

            if (wp != null) {
                gu.createGPS(tr, drawContext, wp, player)
            }

            val stack: ItemStack = player.mainHandStack
            if (stack.isDamageable) {
                gu.renderItemHealth(drawContext, tr, stack)
            }
        }

        ClientSendMessageEvents.ALLOW_CHAT.register { message ->
            if (message.startsWith(".")) {
                cm.runCmd(message)
                return@register false
            }
            return@register true
        }

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val player = client.player ?: return@register

            if (!isDead && player.health <= 0) {
                isDead = true
                wpm.addWaypoint("Death", listOf(player.x.toInt(), player.y.toInt(), player.z.toInt()))
                wpm.setActiveWaypoint("Death")
                return@register
            }

            if (isDead) {
                isDead = false
            }
        }
    }
}