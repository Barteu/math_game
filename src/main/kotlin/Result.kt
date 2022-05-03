


class Result (
    var playerName: String = "none",
    var points: Int = 0,
    var time: Long = 0 ) {
    override fun toString(): String {
        return "[player: ${this.playerName}, points: ${this.points}, time: ${this.time}]"
    }
}
//
//    constructor() {}
//
//    constructor(id: Int, playerName: String, points: Int, time: Long) {
//        this.id = id
//        this.playerName = playerName
//        this.points = points
//        this.time = time
//    }
//
//    constructor(playerName: String, points: Int, time: Long) {
//        this.points = points
//        this.playerName = playerName
//        this.time = time
//    }


