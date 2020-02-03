package runtime;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackMachineTest {

    @Test
    public void stackMachineIsCreated() {
        assertNotNull(new StackMachine());
    }
}