


class Result (
    var playerName: String = "none",
    var points: Int = 0,
    var time: Long = 0 ) {
    override fun toString(): String {
        return "[player: ${this.playerName}, points: ${this.points}, time: ${this.time}]"
    }
}



