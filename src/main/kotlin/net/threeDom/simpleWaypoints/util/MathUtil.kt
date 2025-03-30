package net.threeDom.simpleWaypoints.util

object MathUtil
{
	fun q_rsqrt(num: Float): Float
	{
		var i: Int
		val x2 = num * 0.5f
		var y = num
		val threehalfs = 1.5f

		i = java.lang.Float.floatToIntBits(y)   //evil floating point bit level hacking
		i = 0x5f3759df - (i shr 1)              // what the fuck?
		y = java.lang.Float.intBitsToFloat(i)

		y *= (threehalfs - (x2 * y * y))        // 1st iteration
		// y = y * (threehalfs - (x2 * y * y)); // 2nd iteration, this can be removed

		return y
	}
}