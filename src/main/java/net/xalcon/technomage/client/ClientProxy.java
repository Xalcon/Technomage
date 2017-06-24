package net.xalcon.technomage.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.client.renderer.block.TileEntityAlchemicalCauldronRenderer;
import net.xalcon.technomage.client.renderer.block.TileEntityAmalgamationAltarRenderer;
import net.xalcon.technomage.client.renderer.block.TileEntityPedestalRenderer;
import net.xalcon.technomage.common.IProxy;
import net.xalcon.technomage.common.blocks.properties.TMImbuedOreType;
import net.xalcon.technomage.common.blocks.world.BlockImbuedOre;
import net.xalcon.technomage.common.init.TMBlocks;
import net.xalcon.technomage.common.init.TMItems;
import net.xalcon.technomage.common.tileentities.TileEntityAlchemicalCauldron;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;
import net.xalcon.technomage.common.tileentities.TileEntityPedestal;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        OBJLoader.INSTANCE.addDomain(Technomage.MOD_ID);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemicalCauldron.class, new TileEntityAlchemicalCauldronRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmalgamationAltar.class, new TileEntityAmalgamationAltarRenderer());

        ItemColors itemColors = FMLClientHandler.instance().getClient().getItemColors();
        itemColors.registerItemColorHandler((stack, tint) -> TMImbuedOreType.getFromMeta(stack.getMetadata()).getColor(), TMItems.imbuedShard);
        itemColors.registerItemColorHandler((stack, tint) -> TMImbuedOreType.getFromMeta(stack.getMetadata()).getColor(), TMBlocks.imbuedOre);

        BlockColors blockColors = FMLClientHandler.instance().getClient().getBlockColors();
        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) -> state.getValue(BlockImbuedOre.ORE_TYPE).getColor(), TMBlocks.imbuedOre);

        for(Block block : TMBlocks.getBlocks())
        {
            if(block instanceof IBlockColor)
                blockColors.registerBlockColorHandler((IBlockColor)block, block);
            if(block instanceof IItemColor)
                itemColors.registerItemColorHandler((IItemColor)block, block);
        }
    }
}
