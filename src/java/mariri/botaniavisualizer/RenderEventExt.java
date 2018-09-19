package mariri.botaniavisualizer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.common.block.tile.TileBrewery;
import vazkii.botania.common.block.tile.TileRuneAltar;
import vazkii.botania.common.block.tile.TileTerraPlate;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RenderEventExt {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void blockHighlight(DrawBlockHighlightEvent event) {
		
		int ticks = event.player.ticksExisted;
		MovingObjectPosition target = event.target;
	    
		//if ((event.player.inventory.armorItemInSlot(3) != null) && ((event.player.inventory.armorItemInSlot(3).getItem() instanceof IGoggles)) && (((IGoggles)event.player.inventory.armorItemInSlot(3).getItem()).showIngamePopups(event.player.inventory.armorItemInSlot(3), event.player)))
	    //{
			boolean spaceAbove = event.player.worldObj.isAirBlock(target.blockX, target.blockY + 1, target.blockZ);
			TileEntity te = event.player.worldObj.getTileEntity(target.blockX, target.blockY, target.blockZ);
			if (te != null) {
				/*				
				if ((te instanceof TileInfusionMatrix)) {
					int inst = Math.min(5, ((TileInfusionMatrix)te).instability / 2);
					if (inst > 5)
						inst = 5;
					String s = StatCollector.translateToLocal("stability." + inst);
		            GL11.glDisable(2929);
		            drawTextInAir(target.blockX, target.blockY + 0.1D, target.blockZ, event.partialTicks, s);
		            GL11.glEnable(2929);
				}
				*/
				int currentMana = 0;
				int maxMana = 0;
				
				double offset = 0.5D;
				
				if (te instanceof IManaBlock) {
					currentMana = ((IManaBlock)te).getCurrentMana();
					
					if (te instanceof TilePool) {
						maxMana = ((TilePool)te).manaCap;
					}
					if (te instanceof TileSpreader) {
						maxMana = ((TileSpreader)te).getMaxMana();
						offset = 0.8;
					}
					if (te instanceof TileTerraPlate) {
						maxMana = ((TileTerraPlate)te).MAX_MANA;
					}
					if (te instanceof TileRuneAltar) {
						maxMana = ((TileRuneAltar)te).manaToGet;
						offset = 1.0;
					}
					if (te instanceof TileBrewery) {
						offset = 1.0;
					}
					//Magic Bees
					/*
					if (Loader.isModLoaded("MagicBees") ) {
						if (te instanceof TileEntityManaAuraProvider) {
							maxMana = ((TileEntityManaAuraProvider)te).MAX_MANA;
							offset = 1.0;
						}
					}*/
					
					//System.out.println("current:"+currentMana+" / "+"max:"+maxMana); //it works for vanilla. this is good
					String currentMax = String.format("%,d / %,d", currentMana, maxMana);
					drawTextInAir(target.blockX, target.blockY + offset, target.blockZ, event.partialTicks, currentMax);
						
				}
			}
	    //}
	    
	}
	 
	public void drawTextInAir(double x, double y, double z, float partialTicks, String text) {
		if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
			double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
			
			GL11.glPushMatrix();
			
			GL11.glTranslated(-iPX + x + 0.5D, -iPY + y + 0.5D, -iPZ + z + 0.5D);
			
			float xd = (float)(iPX - (x + 0.5D));
			float zd = (float)(iPZ - (z + 0.5D));
			float rotYaw = (float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D);
			
			GL11.glRotatef(rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
			
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glScalef(0.015F, 0.015F, 0.015F); //orig 0.2F
			int sw = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
			GL11.glEnable(3042);
			//Minecraft.getMinecraft().fontRenderer.drawString(text, 1 - sw / 2, 1, 1118481); //this is the shadow, I think
			GL11.glTranslated(0.0D, 0.0D, -0.1D);
			Minecraft.getMinecraft().fontRenderer.drawString(text, -sw / 2, 0, 16777215);
			
			GL11.glPopMatrix();
		}
	}
}
