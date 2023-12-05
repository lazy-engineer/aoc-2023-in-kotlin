package util

fun <R> List<String>.parts(map: (List<String>) -> R): List<R> = buildList {
    var currentPart = mutableListOf<String>()
    for (line in this@parts) {
        if (line.isEmpty()) {
            add(map(currentPart))
            currentPart = mutableListOf()
        } else {
            currentPart.add(line)
        }
    }
    if (currentPart.isNotEmpty()) {
        add(map(currentPart))
    }
}
