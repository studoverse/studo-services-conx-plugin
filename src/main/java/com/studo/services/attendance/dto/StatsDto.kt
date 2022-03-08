package com.studo.services.attendance.dto

class StatsDto(
    val threadCount: Int,
    val heapMB: Int,
    val nonHeapMB: Int,
    val systemLoadAverage: Double,
    val availableProcessors: Int,
    val minorGcCount: Long,
    val minorGcMs: Long,
    val majorGcCount: Long,
    val majorGcMs: Long,
)