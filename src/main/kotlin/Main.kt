fun main(args: Array<String>) {
    println(
        PhoneBook(
            listOf(
                Contact("User1", "1788972359876"),
                Contact("User2", "1771242151435")
            )
        ).search("17")
    )
}
