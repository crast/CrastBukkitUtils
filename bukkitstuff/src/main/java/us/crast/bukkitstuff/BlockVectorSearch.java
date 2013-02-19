package us.crast.bukkitstuff;

import org.bukkit.util.BlockVector;

public class BlockVectorSearch {
    public BlockVector closestVector(BlockVector v, java.util.Collection<BlockVector> candidates) {
        BlockVector other = null;
        double otherdistance = 0;
        for (BlockVector candidate: candidates) {
            if (other == null) {
                other = candidate;
                otherdistance = v.distance(candidate);
            } else {
                double curdistance = v.distance(candidate);
                if (curdistance < otherdistance) {
                    otherdistance = curdistance;
                    other = candidate;
                }
            }
        }
        return other;
    }
}
