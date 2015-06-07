package sonar.calculator.mod.common.item.calculators;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.CalculatorConfig;
import sonar.calculator.mod.network.CalculatorGui;
import sonar.core.common.item.InventoryItem;
import sonar.core.utils.IItemInventory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CraftingCalc extends SonarCalculator implements IItemInventory {

	public static class CraftingInventory extends InventoryItem {
		public CraftingInventory(ItemStack stack) {
			super(stack, 10);
		}
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new CraftingInventory(stack);
	}

	public CraftingCalc() {
		capacity = CalculatorConfig.craftingEnergy;
		maxReceive = CalculatorConfig.craftingEnergy;
		maxExtract = CalculatorConfig.craftingEnergy;
		maxTransfer = CalculatorConfig.craftingEnergy;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		return onCalculatorRightClick(itemstack, world, player, CalculatorGui.CraftingCalculator);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		this.itemIcon = iconregister.registerIcon("Calculator:craftingcalculator");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		int storedItems = getInventory(stack).getItemsStored(stack);
		if (storedItems != 0) {
			list.add(StatCollector.translateToLocal("calc.storedstacks") + ": " + storedItems);
		}
	}
}
