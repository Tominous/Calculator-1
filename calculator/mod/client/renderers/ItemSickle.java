package sonar.calculator.mod.client.renderers;

import org.lwjgl.opengl.GL11;

import sonar.calculator.mod.client.models.ModelAxe;
import sonar.calculator.mod.client.models.ModelHoe;
import sonar.calculator.mod.client.models.ModelShovel;
import sonar.calculator.mod.client.models.ModelSickle;
import sonar.calculator.mod.client.models.ModelSword;
import sonar.calculator.mod.client.models.ModelWrench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemSickle implements net.minecraftforge.client.IItemRenderer
{
  public String texture;
  public ModelSickle sickle;
  public ItemSickle(String texture)
  {
	  this.texture=texture;
	  this.sickle = new ModelSickle();
	  }
  

  @Override
public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
  {
    return true;
  }
  
  @Override
public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
  {
    return true;
  }
  
  @Override
public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
  {
    if (type == IItemRenderer.ItemRenderType.EQUIPPED){
    	
        GL11.glScaled(3.0F, 3.0F, 3.0F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glRotated(45, 0, 1, 0);
        GL11.glRotated(-60, 0, 0, 1);
        GL11.glTranslated(0.35, -1.00, 0.0);
    }
    if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON){
    	
        GL11.glScaled(1.75F, 1.75F, 1.75F);
        GL11.glRotated(190, 1, 0, 0);
        GL11.glRotated(110, 0, 1, 0);
        //GL11.glRotated(-60, 0, 0, 1);
        GL11.glTranslated(0.2, -1.7, 0.3);
    }
    if (type == IItemRenderer.ItemRenderType.ENTITY){
    	GL11.glScaled(0.7F, 0.7F, 0.7F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glTranslated(0.0, -0.9, 0.0);
        GL11.glRotated(90, 0, 1, 0);
    }
    if (type == IItemRenderer.ItemRenderType.INVENTORY){
        GL11.glScaled(1.5F, 1.5F, 1.5F);
        GL11.glRotated(180, 1, 0, 0);
        GL11.glTranslated(0.0, -1.0, 0.0);
        GL11.glRotated(-45, 0, 1, 0);
        GL11.glRotated(40, 0, 0, 1);
        GL11.glTranslated(0.6, -0.12, 0.0);
    }
    Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(texture));
    sickle.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
  }
}
