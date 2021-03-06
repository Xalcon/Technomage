package net.xalcon.technomage.client;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.client.renderer.block.*;
import net.xalcon.technomage.common.IProxy;
import net.xalcon.technomage.common.tileentities.*;
import net.xalcon.technomage.lib.client.events.ColorRegistrationEvent;

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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOrbMount.class, new TileEntityDisplayNameRenderer<>(true));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLeylightBore.class, new TileEntityLeylightBoreRenderer());

        ItemColors itemColors = FMLClientHandler.instance().getClient().getItemColors();
        BlockColors blockColors = FMLClientHandler.instance().getClient().getBlockColors();
        MinecraftForge.EVENT_BUS.post(new ColorRegistrationEvent(itemColors, blockColors));
    }
}
