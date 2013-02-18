package us.crast.bukkitstuff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockSearcher {
	private static int CONSTRAINTS_Y_MAX = -1;
    private static final int CONSTRAINTS_Y_MIN = 0;
    private final int radiusX;
	private final int radiusY;
	private final int radiusZ;
	
	public BlockSearcher(int radiusX, int radiusY, int radiusZ) {
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;
	}
	
	public java.util.Vector<Block> findBlocks(Block contextBlock) {
		Material targetMaterial = contextBlock.getType();
		return findBlocks(contextBlock, targetMaterial, true);
	}

	public java.util.Vector<Block> findBlocks(Block contextBlock, boolean includeContext) {
		Material targetMaterial = contextBlock.getType();
		return findBlocks(contextBlock, targetMaterial, includeContext);
	}
	
	public java.util.Vector<Block> findBlocks(Block contextBlock, Material targetMaterial) {
		return findBlocks(contextBlock, targetMaterial,true);
	}
	
	public java.util.Vector<Block> findBlocks(Block contextBlock, Material targetMaterial, boolean includeContext) {
		java.util.Vector<Block> blocksFound = new java.util.Vector<Block>();
		//java.util.logging.Logger log = Logger.getLogger("Minecraft");
		Location loc = contextBlock.getLocation().clone();
		World world = loc.getWorld();
		// Maximum possible height can be overridden.
		int maxHeight = (CONSTRAINTS_Y_MAX == -1)? (loc.getWorld().getMaxHeight() -1): CONSTRAINTS_Y_MAX;
		
		int maxX = loc.getBlockX() + radiusX;
		int maxY = Math.min(loc.getBlockY() + radiusY, maxHeight);
		int maxZ = loc.getBlockZ() + radiusZ;
		
		int minY = Math.max(loc.getBlockY() - radiusY, CONSTRAINTS_Y_MIN);
		
		//log.info(String.format("maxX: %d, minY: %d, maxY: %d, maxZ: %d", maxX, minY, maxY, maxZ));
		for (int x = loc.getBlockX() - radiusX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = loc.getBlockZ() - radiusZ; z <= maxZ; z++) {
					Block block = world.getBlockAt(x, y, z);
					//log.info(String.format("Block at %d,%d,%d: %s", x, y, z, block.getType().toString()));
					if (block.getType() == targetMaterial) {
						if (!includeContext && block.getLocation().equals(loc)) continue;
						blocksFound.add(block);
					}
				}
			}
		}
		return blocksFound;
	}
}