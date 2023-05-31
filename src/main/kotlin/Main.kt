fun main(args: Array<String>) {
    Game(
        players = listOf(Player("Dealer"), Player("Dmytro")),
        deck = Deck()
    ).play()
}
