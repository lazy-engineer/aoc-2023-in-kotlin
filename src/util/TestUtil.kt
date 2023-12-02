package util

fun checkResult(expected: Any, result: Any) {
    check(result == expected) {
        println("Expected $expected, but was $result")
    }
}
