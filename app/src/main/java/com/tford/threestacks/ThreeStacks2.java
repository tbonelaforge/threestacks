package com.tford.threestacks;

/**
 * Created by tford on 3/11/17.
 */
public class ThreeStacks2 {
    int[] buffer = new int[6];
    int[] stacks = new int[3];
    int nextFree ;

    public ThreeStacks2() {
        initializeBuffer();
    }

    private void initializeBuffer() {
        int bufferEnd = buffer.length - 1;
        for (int i = buffer.length - 1; i > 0; i -= 2) {
            free(i);
        }
    }

    public Integer pop(int i) {
        int stack = stacks[i];
        if (stack == 0) {
            return null;
        }
        Integer returnValue = buffer[stack];
        int next = buffer[stack - 1];
        stacks[i] = next;
        free(stack);
        return returnValue;
    }

    public void push(int i, int value) throws StackOverflowError {
        int stack = stacks[i];
        int next = alloc();
        buffer[next] = value;
        buffer[next - 1] = stack;
        stacks[i] = next;
    }

    public Integer getStackTop(int i) {
        int p = stacks[i];
        if (p < 1) {
            return null;
        }
        return buffer[p];
    }

    public void iterate(ThreeStacksProcessor processor) {
        for (int i = 0; i < stacks.length; i++) {
            iterate(i, processor);
        }
    }

    private void iterate(int i, ThreeStacksProcessor processor) {
        int p = stacks[i];
        int j = 0;
        while (p >= 1) {
            processor.process(i, j, buffer[p]);
            j = j + 1;
            p = buffer[p - 1];
        }
    }

    private void free(int stackHead) {
        buffer[stackHead - 1] = nextFree;
        nextFree = stackHead;
    }

    private int alloc() {
        if (nextFree == 0) {
            throw new StackOverflowError("Cannot alloc: no more buffer space!");
        }
        int returnValue = nextFree;
        nextFree = buffer[nextFree - 1];
        return returnValue;
    }
}
