package com.origogi.lollogs

val Pair<Int, Int>.winsRate: Int
    get() =
        if (first + second == 0) {
            0
        } else {
            (first * 100) / (first + second)
        }
