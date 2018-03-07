package fr.poudlardrp.citizens.api.trait.trait;

import fr.poudlardrp.citizens.api.exception.NPCLoadException;
import fr.poudlardrp.citizens.api.trait.Trait;
import fr.poudlardrp.citizens.api.trait.TraitName;
import fr.poudlardrp.citizens.api.util.DataKey;

/**
 * Represents the spawn state of an NPC. This only determines whether an NPC should spawn onEnable. For checking if an
 * NPC's entity is spawned, use NPC.isSpawned().
 */
@TraitName("spawned")
public class Spawned extends Trait {
    private boolean shouldSpawn = true;

    public Spawned() {
        super("spawned");
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        shouldSpawn = key.getBoolean("");
    }

    @Override
    public void save(DataKey key) {
        key.setBoolean("", shouldSpawn);
    }

    /**
     * Sets whether an NPC should spawn during server starts or reloads.
     *
     * @param shouldSpawn Whether an NPC should spawn
     */
    public void setSpawned(boolean shouldSpawn) {
        this.shouldSpawn = shouldSpawn;
    }

    /**
     * Gets whether an NPC should spawn during server starts or reloads.
     *
     * @return Whether an NPC should spawn
     */
    public boolean shouldSpawn() {
        return shouldSpawn;
    }

    @Override
    public String toString() {
        return "Spawned{" + shouldSpawn + "}";
    }
}