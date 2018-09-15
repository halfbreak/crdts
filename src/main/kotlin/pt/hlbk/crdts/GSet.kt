package pt.hlbk.crdts

import pt.hlbk.crdts.protobuf.Crdts
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class GSet(private val myId: String,
           private val adds: MutableSet<String> = Collections.newSetFromMap(ConcurrentHashMap<String, Boolean>())) {

    fun add(element: String) {
        adds.add(element)
    }

    fun lookup(element: String): Boolean {
        return adds.contains(element)
    }

    fun compare(other: GSet): Boolean {
        return adds.containsAll(other.adds)
    }

    fun merge(other: GSet): GSet {
        return GSet(myId, adds.plus(other.adds).toMutableSet())
    }

    fun serialize(): Crdts.GSet {
        return Crdts.GSet
                .newBuilder()
                .setSenderId(myId)
                .addAllAdds(adds)
                .build()
    }
}