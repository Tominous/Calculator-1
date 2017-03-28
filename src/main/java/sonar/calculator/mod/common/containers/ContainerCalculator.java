package sonar.calculator.mod.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.common.recipes.CalculatorRecipes;
import sonar.calculator.mod.utils.SlotPortableCrafting;
import sonar.calculator.mod.utils.SlotPortableResult;
import sonar.core.api.SonarAPI;
import sonar.core.api.utils.ActionType;
import sonar.core.common.item.InventoryItem;
import sonar.core.recipes.RecipeHelperV2;

public class ContainerCalculator extends Container implements ICalculatorCrafter {
	private final InventoryItem inventory;
	private boolean isRemote;

	private static final int INV_START = 3, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

	private EntityPlayer player;

	public ContainerCalculator(EntityPlayer player, InventoryItem inventoryItem) {
		this.player = player;
		this.inventory = inventoryItem;
		isRemote = player.getEntityWorld().isRemote;
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 0, 25, 35, isRemote, Calculator.itemCalculator));
		addSlotToContainer(new SlotPortableCrafting(this, inventory, 1, 79, 35, isRemote, Calculator.itemCalculator));
		addSlotToContainer(new SlotPortableResult(player, inventory, this, new int[] { 0, 1 }, 2, 134, 35, isRemote));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

		onItemCrafted();
	}

	@Override
	public void onItemCrafted() {
		inventory.setInventorySlotContents(2, RecipeHelperV2.getItemStackFromList(CalculatorRecipes.instance().getOutputs(player, inventory.getStackInSlot(0), inventory.getStackInSlot(1)), 0), isRemote);
	}

	public void removeEnergy(int remove) {
		// if (!this.isRemote) {
		if (player.capabilities.isCreativeMode) {
			return;
		}
		SonarAPI.getEnergyHelper().extractEnergy(player.getHeldItemMainhand(), remove, ActionType.PERFORM);

		// }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotID < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				if (slotID >= INV_START) {
					if (!this.mergeItemStack(itemstack1, 0, INV_START - 1, false)) {
						return null;
					}
				} else if (slotID >= INV_START && slotID < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				} else if (slotID >= HOTBAR_START && slotID < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}
			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return null;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int drag, ClickType click, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
			return null;
		}
		return super.slotClick(slot, drag, click, player);
	}
}
