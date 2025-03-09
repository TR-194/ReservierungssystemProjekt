package com.kino.reservierungssystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.locks.ReentrantLock;

class SitzplatzTest {

    @Test
    void testLockExistsAndWorks() {
        Sitzplatz platz = new Sitzplatz();
        ReentrantLock lock = platz.getLock();
        assertNotNull(lock);

        // Versuche, den Lock zu erwerben.
        assertTrue(lock.tryLock());
        // Nach dem Erwerb den Lock wieder freigeben.
        lock.unlock();
    }
}
