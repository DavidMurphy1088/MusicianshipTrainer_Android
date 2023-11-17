package com.musicmaster.musicianshiptrainer.models

class Rest(
    timeSlice: TimeSlice, // Now passing TimeSlice to the constructor
    value: Double,
    staffNum: Int = 0, // Providing a default value similar to TimeSliceEntry, which can be overridden
) : TimeSliceEntry(timeSlice, value, staffNum) {

}