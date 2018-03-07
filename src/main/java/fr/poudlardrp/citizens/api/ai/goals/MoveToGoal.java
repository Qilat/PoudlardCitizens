package fr.poudlardrp.citizens.api.ai.goals;

import net.citizensnpcs.api.ai.event.CancelReason;
import net.citizensnpcs.api.ai.event.NavigatorCallback;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

public class MoveToGoal extends BehaviorGoalAdapter {
    private final NPC npc;
    private final Location target;
    private boolean finished;
    private CancelReason reason;

    public MoveToGoal(NPC npc, Location target) {
        this.npc = npc;
        this.target = target;
    }

    @Override
    public void reset() {
        npc.getNavigator().cancelNavigation();
        reason = null;
        finished = false;
    }

    @Override
    public BehaviorStatus run() {
        if (finished) {
            return reason == null ? BehaviorStatus.SUCCESS : BehaviorStatus.FAILURE;
        }
        return BehaviorStatus.RUNNING;
    }

    @Override
    public boolean shouldExecute() {
        boolean executing = !npc.getNavigator().isNavigating() && target != null;
        if (executing) {
            npc.getNavigator().setTarget(target);
            npc.getNavigator().getLocalParameters().addSingleUseCallback(new NavigatorCallback() {
                @Override
                public void onCompletion(CancelReason cancelReason) {
                    finished = true;
                    reason = cancelReason;
                }
            });
        }
        return executing;
    }
}
