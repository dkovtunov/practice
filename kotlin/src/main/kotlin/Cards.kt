const val TARGET = 21

enum class Rank(val value: Int) {
    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ;

    override fun toString(): String = name
}

enum class Suit {
    HEARTS, DIAMONDS, SPADES, CLUBS,
    ;

    override fun toString(): String = name
}

data class Card(val rank: Rank, val suit: Suit) {
    override fun toString(): String = "$rank of $suit"
}

class Deck {
    private var cards = ArrayDeque<Card>()

    init {
        for (rank in Rank.values()) {
            for (suit in Suit.values()) {
                cards.add(Card(rank, suit))
            }
        }

        cards.shuffle()
    }

    fun draw() = cards.removeFirst()
}

class Player(val name: String) {
    private val cards = mutableListOf<Card>()

    override fun toString() = name
    
    fun take(deck: Deck) {
        cards.add(deck.draw())
    }

    val shouldSkipTaking: Boolean
        get() = points >= 17

    val points: Int
        get() {
            var total = cards.sumOf { it.rank.value }

            if (total > TARGET) {
                repeat(cards.count { it.rank == Rank.ACE }) {
                    total -= 10
                }
            }

            return total
        }
}

class Game(private val players: List<Player>, private val deck: Deck) {
    fun play() {
        repeat(2) {
            players.forEach {
                it.take(deck)
            }
        }

        while (getBusted() == null && !players.all(Player::shouldSkipTaking)) {
            players.forEach { player ->
                if (!player.shouldSkipTaking) {
                    player.take(deck)
                }
            }
        }

        println("Winner is ${getWinner()}")
    }

    private fun getBusted() = players.find { it.points > 21 }

    private fun getWinner(): Player {
        return getBusted()?.let { looser -> players.filter { winner -> winner != looser }[0] }
            ?: players.maxBy(Player::points)
    }
}
