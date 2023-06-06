// https://leetcode.com/problems/number-of-corner-rectangles/
class Rectangles {
    fun countCornerRectangles(grid: Array<IntArray>): Int {
        if (grid.size == 0 || grid[0].size == 0) return 0
        val height = grid.size
        val length = grid[0].size
        val bars = mutableListOf<Int>()

        for (distance in 1 until height - 1) {
            for (i in distance until height) {
                val top = i - distance
                val bottom = i
                var count = 0
                for (j in 0 until length) {
                    if (grid[top][j] == 1 && grid[bottom][j] == 1) {
                        count++
                    }
                }
                bars.add(count)
            }
        }

        return bars.sumOf { n: Int -> n * (n - 1) / 2 }
    }
}
