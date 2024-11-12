package org.ghosttrio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutexTest {
    static class Mutex {
        private int counter = 0;

        public void increment() {
            counter++;
        }

        public int getCounter() {
            return counter;
        }
    }
    @Test
    void mutexCounterTest() throws InterruptedException {
        Mutex mutex = new Mutex();

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                mutex.increment();
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                mutex.increment();
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        assertEquals(20000, mutex.getCounter());
    }
}
