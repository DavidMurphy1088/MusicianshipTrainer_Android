package com.musicmaster.musicianshiptrainer.models

enum class StaffType {
    Treble,
    Bass
}
class Staff(val score: Score, staffType: StaffType, staffNum:Int, linesInStaff:Int) {
    //val type: StaffType
    val staffType = staffType
    val staffNum = staffNum
}
