package fr.poudlardrp.citizens.api.ai.tree;

public class TimerDecorator extends BehaviorGoalAdapter {
    private final int limit;
    private final Behavior wrapping;
    private int ticks;

    private TimerDecorator(Behavior wrapping, int tickLimit) {
        this.limit = tickLimit;
        this.wrapping = wrapping;
    }

    public static TimerDecorator tickLimiter(Behavior wrapping, int tickLimit) {
        return new TimerDecorator(wrapping, tickLimit);
    }

    @Override
    public void reset() {
        ticks = 0;
        wrapping.reset();
    }

    @Override
    public BehaviorStatus run() {
        ticks++;
        if (ticks >= limit)
            return BehaviorStatus.FAILURE;
        return wrapping.run();
    }

    @Override
    public boolean shouldExecute() {
        return wrapping.shouldExecute();
    }
}
