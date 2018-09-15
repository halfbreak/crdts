package pt.hlbk.crdts

import pt.hlbk.crdts.protobuf.Crdts

class PNCounter(private val myId: String,
                private val increases: GCounter = GCounter(myId),
                private val decreases: GCounter = GCounter(myId)) {

    fun increment() {
        increases.increment()
    }

    fun decrement() {
        decreases.increment()
    }

    fun value(): Long {
        return increases.value() - decreases.value()
    }

    fun compare(other: PNCounter): Boolean {
        return increases.compare(other.increases) && decreases.compare(other.decreases)
    }

    fun merge(other: PNCounter): PNCounter {
        return PNCounter(myId, increases.merge(other.increases), decreases.merge(other.decreases))
    }

    fun serialize(): Crdts.PNCounter {
        return Crdts.PNCounter
                .newBuilder()
                .setSenderId(myId)
                .putAllIncreases(increases.getCounts())
                .putAllDecreases(decreases.getCounts())
                .build()
    }
}