package fr.poudlardrp.citizens.trait.waypoint.triggers;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import fr.poudlardrp.citizens.api.persistence.PersistenceLoader;
import fr.poudlardrp.citizens.api.persistence.Persister;
import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.conversations.Prompt;

import java.util.Map;

public class WaypointTriggerRegistry implements Persister<WaypointTrigger> {
    private static final Map<String, Class<? extends Prompt>> triggerPrompts = Maps.newHashMap();
    private static final Map<String, Class<? extends WaypointTrigger>> triggers = Maps.newHashMap();

    static {
        addTrigger("animation", AnimationTrigger.class, AnimationTriggerPrompt.class);
        addTrigger("chat", ChatTrigger.class, ChatTriggerPrompt.class);
        addTrigger("delay", DelayTrigger.class, DelayTriggerPrompt.class);
        addTrigger("teleport", TeleportTrigger.class, TeleportTriggerPrompt.class);
        addTrigger("speed", SpeedTrigger.class, SpeedTriggerPrompt.class);
    }

    public static void addTrigger(String name, Class<? extends WaypointTrigger> triggerClass,
                                  Class<? extends WaypointTriggerPrompt> promptClass) {
        triggers.put(name, triggerClass);
        triggerPrompts.put(name, promptClass);
    }

    public static String describeValidTriggerNames() {
        return Joiner.on(", ").join(triggerPrompts.keySet());
    }

    public static Prompt getTriggerPromptFrom(String input) {
        Class<? extends Prompt> promptClass = triggerPrompts.get(input);
        if (promptClass == null)
            return null;
        try {
            return promptClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public WaypointTrigger create(DataKey root) {
        String type = root.getString("type");
        Class<? extends WaypointTrigger> clazz = triggers.get(type);
        return clazz == null ? null : PersistenceLoader.load(clazz, root);
    }

    @Override
    public void save(WaypointTrigger instance, DataKey root) {
        PersistenceLoader.save(instance, root);
        for (Map.Entry<String, Class<? extends WaypointTrigger>> entry : triggers.entrySet()) {
            if (entry.getValue() == instance.getClass()) {
                root.setString("type", entry.getKey());
                break;
            }
        }
    }
}
