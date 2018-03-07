package fr.poudlardrp.citizens.api.astar.pathfinder;

import java.util.List;

public interface NeighbourGeneratorBlockExaminer extends BlockExaminer {
    public List<PathPoint> getNeighbours(BlockSource source, PathPoint point);
}
