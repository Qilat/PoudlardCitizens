package fr.poudlardrp.citizens.api.ai.goals;

import fr.poudlardrp.citizens.api.ai.event.CancelReason;
import fr.poudlardrp.citizens.api.ai.event.NavigatorCallback;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorGoalAdapter;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorStatus;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class TargetNearbyEntityGoal extends BehaviorGoalAdapter {
    private final boolean aggressive;
    private final NPC npc;
    private final double radius;
    private final Set<EntityType> targets;
    private boolean finished;
    private CancelReason reason;
    private Entity target;

    private TargetNearbyEntityGoal(NPC npc, Set<EntityType> targets, boolean aggressive, double radius) {
        this.npc = npc;
        this.targets = targets;
        this.aggressive = aggressive;
        this.radius = radius;
    }

    public static Builder builder(NPC npc) {
        return new Builder(npc);
    }

    @Override
    public void reset() {
        npc.getNavigator().cancelNavigation();
        target = null;
        finished = false;
        reason = null;
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
        if (targets.size() == 0 || !npc.isSpawned())
            return false;
        Collection<Entity> nearby = npc.getEntity().getNearbyEntities(radius, radius, radius);
        this.target = null;
        for (Entity entity : nearby) {
            if (targets.contains(entity.getType())) {
                target = entity;
                break;
            }
        }
        if (target != null) {
            npc.getNavigator().setTarget(target, aggressive);
            npc.getNavigator().getLocalParameters().addSingleUseCallback(new NavigatorCallback() {
                @Override
                public void onCompletion(CancelReason cancelReason) {
                    reason = cancelReason;
                    finished = true;
                }
            });
            return true;
        }
        return false;
    }

    public static class Builder {
        private final NPC npc;
        private boolean aggressive;
        private double radius = 10D;
        private Set<EntityType> targetTypes = EnumSet.noneOf(EntityType.class);

        public Builder(NPC npc) {
            this.npc = npc;
        }

        public Builder aggressive(boolean aggressive) {
            this.aggressive = aggressive;
            return this;
        }

        public TargetNearbyEntityGoal build() {
            return new TargetNearbyEntityGoal(npc, targetTypes, aggressive, radius);
        }

        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder targets(Set<EntityType> targetTypes) {
            this.targetTypes = targetTypes;
            return this;
        }
    }
}
