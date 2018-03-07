package fr.poudlardrp.citizens.trait.waypoint.triggers;

import fr.poudlardrp.citizens.util.Messages;
import fr.poudlardrp.citizens.api.util.Messaging;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class SpeedTriggerPrompt extends NumericPrompt implements WaypointTriggerPrompt {
    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        float speed = (float) Math.max(input.doubleValue(), 0);
        context.setSessionData(WaypointTriggerPrompt.CREATED_TRIGGER_KEY, new SpeedTrigger(speed));
        return (Prompt) context.getSessionData(WaypointTriggerPrompt.RETURN_PROMPT_KEY);
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return Messaging.tr(Messages.SPEED_TRIGGER_PROMPT);
    }
}
