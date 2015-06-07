package sonar.calculator.mod.common.item.modules;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sonar.calculator.mod.common.item.CalcItem;
import sonar.core.utils.helpers.FontHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LocatorModule extends CalcItem {
	public LocatorModule() {
		setTextureName("Calculator:locator_module_on");
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		NBTTagCompound nbtData = stack.stackTagCompound;
		if (nbtData == null) {
			nbtData = new NBTTagCompound();
			nbtData.setString("Player", "None");
			stack.setTagCompound(nbtData);
		}
		String name = player.getGameProfile().getName();
		if (name != null) {
			if (world.getPlayerEntityByName(name) != null) {

				stack.stackTagCompound.setString("Player", name);
				
				FontHelper.sendMessage(StatCollector.translateToLocal("locator.owner")+": "+ player.getGameProfile().getName(), world, player);

			}
		}

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {

		super.addInformation(stack, player, list, par4);
		if(stack.hasTagCompound()){
		NBTTagCompound nbtData = stack.stackTagCompound;
		if(stack.stackTagCompound.getString("Player") != "None"){
		list.add(StatCollector.translateToLocal("locator.owner")+": "+ stack.stackTagCompound.getString("Player"));
		}else{
			list.add(StatCollector.translateToLocal("locator.owner")+": "+ StatCollector.translateToLocal("locator.none"));
		}
		}
	}

}
