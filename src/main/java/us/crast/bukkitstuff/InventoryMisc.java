package us.crast.bukkitstuff;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryMisc {
    java.util.logging.Logger log;
    
    @SuppressWarnings("unused")
    private void listInventory(Inventory inv) {
        for(ItemStack stack: inv.getContents()) {
            if (stack == null) continue;
            int amount = stack.getAmount();
            String itype = stack.getType().toString();
            log.info("->" + amount + " of " + itype);
        }
    }
}
