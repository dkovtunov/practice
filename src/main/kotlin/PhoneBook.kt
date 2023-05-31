data class Contact(val name: String, val number: String)

class PhoneBook(contacts: List<Contact>) {
    class Node(val links: MutableMap<Char, Node>, val contacts: MutableList<Contact>)

    private val root = Node(mutableMapOf(), mutableListOf())

    private val nodeRefs = mutableMapOf<Char, MutableSet<Node>>()

    init {
        contacts.forEach { contact ->
            contact.number.takeIf { it.isNotEmpty() }?.let { number ->
                var localRoot = root

                for (char in number) {
                    localRoot = addNode(localRoot, char, contact)
                    if (char !in nodeRefs) {
                        nodeRefs[char] = mutableSetOf()
                    }
                    nodeRefs[char]!!.add(localRoot)
                }
            }
        }
    }

    private fun addNode(root: Node, char: Char, contact: Contact): Node {
        root.links[char]?.contacts?.add(contact) ?: run {
            root.links[char] = Node(mutableMapOf(), mutableListOf(contact))
        }

        return root.links[char]!!
    }

    fun search(query: String): Contact? {
        if (query.isEmpty()) return null

        val firstChar = query[0]

        return nodeRefs[firstChar]?.let { set ->
            set
                .toList()
                .map { root ->
                    traverse(
                        query = query,
                        position = 0,
                        node = Node(mutableMapOf(firstChar to root), mutableListOf())
                    )
                }
                .flatten()
                .takeIf { it.isNotEmpty() }
                ?.minBy { it.name }
        }
    }

    private fun traverse(query: String, position: Int, node: Node): List<Contact> {
        if (position == query.length) {
            return node.contacts
        }
        if (query[position] !in node.links) {
            return emptyList()
        }

        return traverse(query, position + 1, node.links[query[position]]!!)
    }
}
