package fr.poudlardrp.citizens.api.persistence;

import fr.poudlardrp.citizens.api.util.DataKey;
import org.bukkit.util.EulerAngle;

public class EulerAnglePersister implements Persister<EulerAngle> {
    @Override
    public EulerAngle create(DataKey root) {
        double x = root.getDouble("x");
        double y = root.getDouble("y");
        double z = root.getDouble("z");
        return new EulerAngle(x, y, z);
    }

    @Override
    public void save(EulerAngle angle, DataKey root) {
        root.setDouble("x", angle.getX());
        root.setDouble("y", angle.getY());
        root.setDouble("z", angle.getZ());
    }
}
