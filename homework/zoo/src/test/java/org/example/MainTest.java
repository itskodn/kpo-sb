package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainCoverageTest {

    @Test
    void testMainWithTestArgumentOnly() {
        assertDoesNotThrow(() -> Main.main(new String[]{"test"}));
    }
}