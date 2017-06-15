package net.xalcon.technomage.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.technomage.common.blocks.BlockTMTileProvider;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event) { }
    public void init(FMLInitializationEvent event) { }
    public void postInit(FMLPostInitializationEvent event) { }

    public <T extends Block> T register(T block)
    {
        GameRegistry.register(block);
        if(block instanceof IItemBlockProvider)
        {
            IItemBlockProvider provider = (IItemBlockProvider) block;
            if(provider.hasItemBlock())
            {
                ItemBlock itemBlock = provider.createItemBlock();
                itemBlock.setRegistryName(block.getRegistryName());
                this.register(itemBlock);
            }
        }

        if(block instanceof BlockTMTileProvider)
        {
            ((BlockTMTileProvider)block).getTileEntityClasses()
                    .forEach(p -> GameRegistry.registerTileEntity(p.component2(), p.component1()));
        }
        return block;
    }

    public <T extends Item> T register(T item)
    {
        GameRegistry.register(item);
        return item;
    }
}
