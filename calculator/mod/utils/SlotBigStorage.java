package sonar.calculator.mod.utils;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.api.IBigInventory;
import sonar.calculator.mod.common.tileentity.machines.TileEntityStorageChamber;

public class SlotBigStorage extends Slot {

	public IBigInventory tile;

	public SlotBigStorage(IBigInventory tile, int slot, int x, int y) {
		super(tile, slot, x, y);
		this.tile = tile;
	}
	@Override
	public boolean isItemValid(ItemStack stack)
    {
        return tile.isItemValid(slotNumber, stack);
    }
	public ItemStack getStack()
    {
        return tile.getFullStack(this.getSlotIndex());
    }
	
}
