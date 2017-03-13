package com.tford.threestacks;

/**
 * Created by tford on 3/9/17.
 */
public class ThreeStacks {
    private int[][] stacks = new int[3][3];
    private int[] ps = {-1, -1, -1};

    public Integer getStackTop(int i) {
        if (ps[i] < 0) {
            return null;
        }
        return stacks[i][ps[i]];
    }

    public void push(int value, int i) {
        if (ps[i] >= stacks[i].length - 1) {
            throw new StackOverflowError("Stack " + (i+ 1) + " is full; cannot push!");
        }
        ps[i] = ps[i] + 1;
        stacks[i][ps[i]] = value;
    }

    public Integer pop(int i) {
        Integer stackTop = getStackTop(i);
        if (stackTop != null) {
            ps[i] = ps[i] - 1;
        }
        return stackTop;
    }

    public void iterate(ThreeStacksProcessor processor) {
        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for (j = ps[i]; j >= 0; j--) {
                processor.process(i, ps[i] - j, stacks[i][j]);
            }
        }
    }
}
