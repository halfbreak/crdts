package pt.hlbk.crdts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GCounterTest {
    @Test
    fun shouldAdd() {
        val counter = GCounter("a")
        assertThat(counter.value()).isEqualTo(0)

        counter.increment()
        counter.increment()
        assertThat(counter.value()).isEqualTo(2)
    }

    @Test
    fun shouldMerge() {
        val counterA = GCounter("a")
        val counterB = GCounter("b")

        counterA.increment()
        counterB.increment()
        assertThat(counterA.value()).isEqualTo(1)
        assertThat(counterB.value()).isEqualTo(1)

        val merged = counterA.merge(counterB)
        assertThat(merged.value()).isEqualTo(2)
    }
}