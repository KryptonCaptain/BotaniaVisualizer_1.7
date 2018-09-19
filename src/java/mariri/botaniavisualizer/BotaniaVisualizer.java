package mariri.botaniavisualizer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vazkii.botania.common.lib.LibOreDict;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
		modid = "BotaniaVisualizer", 
		name = "Botania Visualizer",
		version = "${version}", 
		dependencies = "required-after:Botania" 
	)

public class BotaniaVisualizer {

    
    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new RenderEventExt());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new TooltipHandler());

    }
}
