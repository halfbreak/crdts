package pt.hlbk.crdts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GSetTest {

    @Test
    fun shouldAdd() {
        val set = GSet("a")
        Assertions.assertThat(set.lookup("a")).isFalse()

        set.add("a")
        Assertions.assertThat(set.lookup("a")).isTrue()
    }

    @Test
    fun shouldMerge() {
        val setA = GSet("a")
        val setB = GSet("b")

        setA.add("a")
        setB.add("a")
        setB.add("b")
        Assertions.assertThat(setA.lookup("a")).isTrue()
        Assertions.assertThat(setB.lookup("b")).isTrue()

        val merged = setA.merge(setB)
        Assertions.assertThat(merged.lookup("a")).isTrue()
        Assertions.assertThat(merged.lookup("b")).isTrue()
    }
}