// https://leetcode.com/problems/first-missing-positive/

const MAX_INT_BITS = 53

export function solution(nums: number[]): number {
  const masks: Map<number, number> = new Map()

  nums.forEach(v => {
    if (v < 1) return

    const cell = Math.floor(v / MAX_INT_BITS)
    const bitPosition = v % MAX_INT_BITS - 1
    const prev = masks.get(cell)

    masks.set(cell, 1 << bitPosition | (prev == undefined ? 0 : prev))
  })

  const keys = Array.from(masks.keys())
  keys.sort()

  if (keys[0] != 0) return 1

  const key = keys.find(k => masks.get(k) != Number.MAX_SAFE_INTEGER)

  if (key != undefined) {
    let result = 1
    let value = masks.get(key)!!
    while ((value & 1) !== 0) {
      result++
      value >>= 1
    }

    return result + (key * MAX_INT_BITS)
  }

  return keys.length * MAX_INT_BITS
}
