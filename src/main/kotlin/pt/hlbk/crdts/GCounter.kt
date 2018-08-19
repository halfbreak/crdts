package pt.hlbk.crdts

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class GCounter(private val myId: String,
               private val counts: ConcurrentMap<String, Long> = ConcurrentHashMap()) {

    fun increment() {
        counts.computeIfPresent(myId) { _, l -> l + 1 }
        counts.putIfAbsent(myId, 1L)
    }

    fun value(): Long {
        return counts.values.stream().reduce(0L) { a, b -> a!! + b!! }
    }

    fun compare(other: ConcurrentMap<String, Long>): Boolean {
        return counts.entries.all { entry -> entry.value <= other.getOrZero(entry.key) }
    }

    fun merge(other: GCounter): GCounter {
        return GCounter(myId,
                counts.apply { other.counts.forEach { merge(it.key, it.value) { a, b -> Math.max(a, b) } } })
    }
}

fun ConcurrentMap<String, Long>.getOrZero(key: String): Long {
    return this.getOrDefault(key, 0)
}
