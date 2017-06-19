package net.xalcon.technomage.client;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.technomage.Technomage;
import net.xalcon.technomage.client.renderer.block.TileEntityAlchemicalCauldronRenderer;
import net.xalcon.technomage.client.renderer.block.TileEntityAmalgamationAltarRenderer;
import net.xalcon.technomage.client.renderer.block.TileEntityPedestalRenderer;
import net.xalcon.technomage.common.CommonProxy;
import net.xalcon.technomage.common.tileentities.TileEntityAlchemicalCauldron;
import net.xalcon.technomage.common.tileentities.TileEntityAmalgamationAltar;
import net.xalcon.technomage.common.tileentities.TileEntityPedestal;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(Technomage.MOD_ID);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemicalCauldron.class, new TileEntityAlchemicalCauldronRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal.class, new TileEntityPedestalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmalgamationAltar.class, new TileEntityAmalgamationAltarRenderer());
    }
}
