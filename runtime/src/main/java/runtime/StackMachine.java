package runtime;

import java.util.Stack;
import runtime.StackElement.Type;

public class StackMachine {
    private Stack<StackElement> stack;
    private StackElement[] programmMemory;

    private void eql() {
        StackElement arg1 = stack.pop();
        StackElement arg2 = stack.pop();

        if(arg1.getType() == Type.) {
            arg1 = programmMemory[(int) arg1.getValue()];
        }

        if(arg2.getType() == Type.ADDRESS) {
            arg2 = programmMemory[(int) arg2.getValue()];
        }

        if(arg1.equals(arg2)) {
            pushBoolean(true);
        }else {
            pushBoolean(false);
        }
    }

    private void pushBoolean(boolean booleanArg) {
        StackElement element = new StackElement(Type.BOOLEAN, booleanArg);
        stack.push(element);
    }
}
