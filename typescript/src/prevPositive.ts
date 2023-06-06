#!/usr/bin/node


/*
Write a function:

  function solution(A: number[]): number;

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

  For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

Given A = [1, 2, 3], the function should return 4.

Given A = [−1, −3], the function should return 1.

Write an efficient algorithm for the following assumptions:

  N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].


[1, 3, 6, 4, 1, 2]

[ 1, 2, 3 ]

[ -1, -3 ]


* */

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
