package fr.poudlardrp.citizens.trait.waypoint.triggers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import fr.poudlardrp.citizens.api.npc.NPC;
import fr.poudlardrp.citizens.api.persistence.Persist;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class ChatTrigger implements WaypointTrigger {
    @Persist(required = true)
    private List<String> lines;
    @Persist
    private double radius = -1;

    public ChatTrigger() {
    }

    public ChatTrigger(double radius, Collection<String> chatLines) {
        this.radius = radius;
        lines = Lists.newArrayList(chatLines);
    }

    @Override
    public String description() {
        return String.format("Chat Trigger [radius %d, %s]", radius, Joiner.on(", ").join(lines));
    }

    @Override
    public void onWaypointReached(NPC npc, Location waypoint) {
        if (radius < 0) {
            for (Player player : npc.getEntity().getWorld().getPlayers()) {
                for (String line : lines)
                    Messaging.send(player, line);
            }
        } else {
            for (Entity entity : npc.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (!(entity instanceof Player))
                    continue;
                for (String line : lines)
                    Messaging.send((Player) entity, line);
            }
        }
    }
}
