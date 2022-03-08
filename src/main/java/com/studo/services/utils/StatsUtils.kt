package com.studo.services.utils

import com.studo.services.attendance.dto.StatsDto
import java.lang.management.ManagementFactory
import java.lang.management.ThreadInfo

object StatsUtils {
    private val garbageCollectorsYoungRegion = listOf("PS Scavenge", "ParNew", "G1 Young Generation", "Copy", "ZGC")
    private val garbageCollectorsOldRegion = listOf("PS MarkSweep", "ConcurrentMarkSweep", "G1 Old Generation", "MarkSweepCompact")
    private var previousGarbageCollectorStats = GarbageCollectorStats()

    data class GarbageCollectorStats(
        var minorGcCount: Long = 0,
        var minorGcMs: Long = 0,
        var majorGcCount: Long = 0,
        var majorGcMs: Long = 0
    ) {
        operator fun minus(other: GarbageCollectorStats): GarbageCollectorStats {
            return GarbageCollectorStats(
                minorGcCount = this.minorGcCount - other.minorGcCount,
                minorGcMs = this.minorGcMs - other.minorGcMs,
                majorGcCount = this.majorGcCount - other.majorGcCount,
                majorGcMs = this.majorGcMs - other.majorGcMs
            )
        }
    }

    fun threadCount(): Int = ManagementFactory.getThreadMXBean().threadCount

    fun heapMemoryMB(): Int = (ManagementFactory.getMemoryMXBean().heapMemoryUsage.used / 1024 / 1024).toInt()

    fun nonHeapMemoryMB(): Int = (ManagementFactory.getMemoryMXBean().nonHeapMemoryUsage.used / 1024 / 1024).toInt()

    fun systemLoadAverage(): Double = ManagementFactory.getOperatingSystemMXBean().systemLoadAverage

    fun availableProcessors(): Int = ManagementFactory.getOperatingSystemMXBean().availableProcessors

    // Returns garbage collections after last call of getGarbageCollectorStats()
    fun getGarbageCollectorStats(): GarbageCollectorStats {
        val mxBeans = ManagementFactory.getGarbageCollectorMXBeans()

        val currentGarbageCollectorStats = GarbageCollectorStats()

        mxBeans.forEach { gc ->
            val collectionCount = gc.collectionCount
            if (collectionCount >= 0) {
                when (gc.name) {
                    in garbageCollectorsYoungRegion -> {
                        currentGarbageCollectorStats.minorGcCount += collectionCount
                        currentGarbageCollectorStats.minorGcMs += gc.collectionTime
                    }
                    in garbageCollectorsOldRegion -> {
                        currentGarbageCollectorStats.majorGcCount += collectionCount
                        currentGarbageCollectorStats.majorGcMs += gc.collectionTime
                    }
                    else -> throw IllegalStateException("Unknown garbage collector: ${gc.name}")
                }
            }
        }

        val garbageCollectionDifference = currentGarbageCollectorStats - previousGarbageCollectorStats

        previousGarbageCollectorStats = currentGarbageCollectorStats

        return garbageCollectionDifference
    }

    fun getContainerStats(): StatsDto {
        // Call heapMemoryMB() as soon as possible after getGarbageCollectorStats() to have associated values
        val garbageCollectionStats = getGarbageCollectorStats()

        // Only use here the peakThreadCount
        val threadCount = ManagementFactory.getThreadMXBean().peakThreadCount
        ManagementFactory.getThreadMXBean().resetPeakThreadCount()

        return StatsDto(
            threadCount = threadCount,
            heapMB = heapMemoryMB(),
            nonHeapMB = nonHeapMemoryMB(),
            systemLoadAverage = systemLoadAverage(),
            availableProcessors = availableProcessors(),
            minorGcCount = garbageCollectionStats.minorGcCount,
            minorGcMs = garbageCollectionStats.minorGcMs,
            majorGcCount = garbageCollectionStats.majorGcCount,
            majorGcMs = garbageCollectionStats.majorGcMs,
        )
    }

    // E.g. 1.8 or 11
    fun getJvmVersion(): Double {
        val version = System.getProperty("java.version")
        var pos = version.indexOf('.')
        pos = version.indexOf('.', pos + 1)
        return java.lang.Double.parseDouble(version.substring(0, pos))
    }
}