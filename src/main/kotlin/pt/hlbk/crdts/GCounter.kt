package pt.hlbk.crdts

import pt.hlbk.crdts.protobuf.Crdts
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class GCounter(private val myId: String,
               private val counts: ConcurrentMap<String, Long> = ConcurrentHashMap()) {

    fun increment() {
        counts.computeIfPresent(myId) { _, l -> l + 1 }
        counts.putIfAbsent(myId, 1L)
    }

    fun value(): Long {
        return counts.values.fold(0L) { a, b -> a + b }
    }

    fun compare(other: GCounter): Boolean {
        return counts.entries.all { entry -> entry.value <= other.counts.getOrZero(entry.key) }
    }

    fun merge(other: GCounter): GCounter {
        return GCounter(myId,
                counts.apply { other.counts.forEach { merge(it.key, it.value) { a, b -> Math.max(a, b) } } })
    }

    fun serialize(): ByteArray {
        return Crdts.GCounter
                .newBuilder()
                .setSenderId(myId)
                .putAllIncreases(counts)
                .build()
                .toByteArray()
    }

    fun getCounts(): ConcurrentMap<String, Long> {
        return ConcurrentHashMap<String, Long>(counts)
    }
}

fun ConcurrentMap<String, Long>.getOrZero(key: String): Long {
    return this.getOrDefault(key, 0)
}
