package sonar.calculator.mod.common.tileentity.misc;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.calculator.mod.Calculator;
import sonar.calculator.mod.network.packets.PacketCalculatorScreen;
import sonar.core.common.tileentity.TileEntitySonar;
import sonar.core.utils.helpers.SonarHelper;

public class TileEntityCalculatorScreen extends TileEntitySonar {

	public int latestMax, latestEnergy;
	public int lastMax, lastEnergy;

	public void updateEntity() {
	
		if (this.worldObj != null && !this.worldObj.isRemote) {
			TileEntity target = SonarHelper.getAdjacentTileEntity(this, ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite());
			if (target == null) {
				return;
			} else {
				if (target instanceof IEnergyReceiver) {
					IEnergyReceiver energy = (IEnergyReceiver) target;
					int max = energy.getMaxEnergyStored(ForgeDirection.UNKNOWN);
					int current = energy.getEnergyStored(ForgeDirection.UNKNOWN);

					if (max != this.lastMax) {
						this.sendMax(max);
					}
					if (current != this.lastEnergy) {
						this.sendEnergy(current);
						this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}else if (target instanceof IEnergyHandler) {
					IEnergyHandler energy = (IEnergyHandler) target;
					int max = energy.getMaxEnergyStored(ForgeDirection.UNKNOWN);
					int current = energy.getEnergyStored(ForgeDirection.UNKNOWN);

					if (max != this.lastMax) {
						this.sendMax(max);
					}
					if (current != this.lastEnergy) {
						this.sendEnergy(current);
						this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
			}

		}
	}

	public void sendMax(int max) {
		markDirty();
		this.lastMax = this.latestMax;
		this.latestMax = max;
		if (!this.worldObj.isRemote)
			Calculator.network.sendToAllAround(new PacketCalculatorScreen(xCoord, yCoord, zCoord, 0, max), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));

	}

	public void sendEnergy(int energy) {
		markDirty();
		this.lastEnergy = this.latestEnergy;
		this.latestEnergy = energy;
		if (!this.worldObj.isRemote)
			Calculator.network.sendToAllAround(new PacketCalculatorScreen(xCoord, yCoord, zCoord, 1, energy), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 64));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.latestMax = nbt.getInteger("latestMax");
		this.latestEnergy = nbt.getInteger("latestEnergy");
		this.lastMax = nbt.getInteger("lastMax");
		this.lastEnergy = nbt.getInteger("lastEnergy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("latestMax", this.latestMax);
		nbt.setInteger("latestEnergy", this.latestEnergy);
		nbt.setInteger("lastMax", this.lastMax);
		nbt.setInteger("lastEnergy", this.lastEnergy);
	}

}