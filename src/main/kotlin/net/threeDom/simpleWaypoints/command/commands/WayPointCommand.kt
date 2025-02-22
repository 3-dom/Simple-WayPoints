package net.threeDom.simpleWaypoints.command.commands

import net.threeDom.simpleWaypoints.client.SimpleWaypointsClient
import net.threeDom.simpleWaypoints.command.Command
import net.threeDom.simpleWaypoints.util.ChatUtil
import net.threeDom.simpleWaypoints.waypoint.WayPoint
import net.threeDom.simpleWaypoints.waypoint.WayPointManager

object WayPointCommand : Command() {
	override val cmd: String = "wp"
	override val desc: String = "Handles waypoints loaded into the client."
	val args: List<String> = listOf("del", "get", "set", "find", "list")
	val cu: ChatUtil = SimpleWaypointsClient.cu

	override fun process(input: List<String>) {

		if (input.isEmpty()) {
			cu.printError("No arguments specified")
			return
		}

		val base = input[0]

		if (!args.contains(base)) {
			cu.printError("Invalid argument")
			return
		}

		val wpm = SimpleWaypointsClient.wpm

		when (base) {
			"get" -> {
				if (input.size < 2) {
					cu.printError("No key was provided")
					return
				}
				val wp = wpm.getWaypoint(input[1]) ?: return cu.printError("Key was not found")
				printWaypoint(wp)
			}

			"list" -> {
				for (wp in wpm.getWaypoints()) {
					printWaypoint(wp.value)
				}
			}

			"del" -> {
				if (input.size < 2) {
					return cu.printError("No key was provided")
				}
				wpm.getWaypoint(input[1]) ?: return cu.printError("Key was not found")

				wpm.delWaypoint(input[1])
				cu.printInfo("Deleted waypoint: " + input[1])
			}

			"set" -> {
				if (input.size < 2) {
					return cu.printError("No key was provided")
				}

				setWayPoint(wpm, input)
			}

			"find" -> {
				if (input.size < 2) {
					return cu.printError("No key was provided")
				}

				cu.printInfo("Tracking waypoint: " + input[1])
				wpm.setActiveWaypoint(input[1])
			}
		}
	}

	private fun printWaypoint(wp: WayPoint) {
		val name = wp.name
		val coOrds = wp.coOrds
		cu.printInfo("$name is located at: $coOrds")
	}

	private fun setWayPoint(wpm: WayPointManager, args: List<String>) {
		val coOrds: List<Int> = getCoOrds(args)
		if (coOrds.isEmpty()) {
			return
		}

		if (wpm.getWaypoint(args[1]) == null) {
			wpm.addWaypoint(args[1], listOf(coOrds[0], coOrds[1], coOrds[2]))
			cu.printInfo("Created waypoint: " + args[1])
			return
		}

		wpm.updateWaypoint(args[1], listOf(coOrds[0], coOrds[1], coOrds[2]))
		cu.printInfo("Updated waypoint: " + args[1])
	}

	private fun getCoOrds(args: List<String>): List<Int> {
		val player = SimpleWaypointsClient.mc.player ?: return listOf()

		val x: Int
		val y: Int
		val z: Int

		when (args.size) {
			2 -> {
				x = player.x.toInt()
				y = player.y.toInt()
				z = player.z.toInt()
			}

			5 -> {
				x = args[2].toInt()
				y = args[3].toInt()
				z = args[4].toInt()
			}

			else -> return listOf()
		}

		return listOf(x, y, z)
	}
}