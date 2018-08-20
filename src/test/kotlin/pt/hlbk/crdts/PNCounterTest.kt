package pt.hlbk.crdts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PNCounterTest {
    @Test
    fun shouldIncreaseAndDecrease() {
        val counter = PNCounter("a")
        Assertions.assertThat(counter.value()).isEqualTo(0)

        counter.increment()
        counter.increment()
        Assertions.assertThat(counter.value()).isEqualTo(2)

        counter.decrement()
        Assertions.assertThat(counter.value()).isEqualTo(1)
    }

    @Test
    fun shouldMerge() {
        val counterA = PNCounter("a")
        val counterB = PNCounter("b")

        counterA.increment()
        counterB.increment()
        counterB.decrement()
        Assertions.assertThat(counterA.value()).isEqualTo(1)
        Assertions.assertThat(counterB.value()).isEqualTo(0)

        val merged = counterA.merge(counterB)
        Assertions.assertThat(merged.value()).isEqualTo(1)
    }
}