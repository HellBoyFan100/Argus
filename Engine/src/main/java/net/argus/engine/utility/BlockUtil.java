package net.argus.engine.utility;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Predicate;

public class BlockUtil {

    private BlockUtil() {
    }

    public static ItemStack toItemStack(Block block) {
        return new ItemStack(block.getType(), 1, block.getData());
    }

    public static MaterialData toMaterialData(Block block) {
        return new MaterialData(block.getType(), block.getData());
    }

    public static Block directSearch(Location location, BlockFace direction, Predicate<Block> target) {
        return directSearch(location.getBlock(), direction, target);
    }

    public static Block directSearch(Block block, BlockFace direction, Predicate<Block> target) {
        do
            if (target.test(block)) {
                return block;
            }
        while (!(block = block.getRelative(direction)).getType().isSolid());
        return null;
    }

}
