package net.threeDom.simpleWaypoints.waypoint

import net.threeDom.simpleWaypoints.util.FileUtil

object WayPointManager
{
	private val fileUtil: FileUtil = FileUtil
	private var waypoints: MutableMap<String, WayPoint> = mutableMapOf()
	private var activeWaypoint: WayPoint? = null


	fun clearWaypoints()
	{
		waypoints = mutableMapOf()
	}

	fun clearActiveWaypoint()
	{
		activeWaypoint = null
	}

	fun setActiveWaypoint(key: String)
	{
		activeWaypoint = getWaypoint(key)
	}

	fun getActiveWaypoint(): WayPoint?
	{
		return activeWaypoint
	}

	fun getWaypoints(): MutableMap<String, WayPoint>
	{
		return waypoints
	}

	fun getWaypoint(key: String): WayPoint?
	{
		val wp = waypoints[key] ?: return null

		return wp
	}

	fun addWaypoint(key: String, coOrds: List<Int>)
	{
		if(waypoints.containsKey(key))
		{
			return
		}

		waypoints[key] = WayPoint(key, coOrds)

		updateMapFile()
	}

	fun delWaypoint(key: String)
	{
		if(! waypoints.containsKey(key))
		{
			return
		}

		waypoints.remove(key)

		if(getActiveWaypoint()?.name == key)
		{
			clearActiveWaypoint()
		}

		updateMapFile()
	}

	fun updateWaypoint(key: String, coOrds: List<Int>)
	{
		waypoints[key] = WayPoint(key, coOrds)
		updateMapFile()
	}

	private fun updateMapFile()
	{
		val nMap: MutableMap<String, String> = mutableMapOf()

		waypoints.keys.forEach { key ->
			val value = waypoints[key] ?: return
			nMap[key] = value.coOrds.joinToString(",")
		}

		fileUtil.writeFileFromMap(nMap, "waypoints", "mods/SimpleWayPoints")
	}

	fun loadMapFile()
	{
		val tMap: MutableMap<String, String>
		val nMap: MutableMap<String, WayPoint> = mutableMapOf()

		tMap = fileUtil.readMapFromFile("waypoints", "mods/SimpleWayPoints")

		tMap.keys.forEach { key ->
			val value = tMap[key] ?: return
			val valSplit = value
				.trim()
				.split(",")

			if(valSplit.size != 3) return

			nMap[key] = WayPoint(key, listOf(valSplit[0].toInt(), valSplit[1].toInt(), valSplit[2].toInt()))
		}

		waypoints = nMap
	}
}
