package net.xalcon.technomage.api.gui.technonomicon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.xalcon.technolib.client.GuiHelper;

public class ResourceLocationGuiIcon implements IGuiIcon
{
    private TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();

    public ResourceLocationGuiIcon(ResourceLocation location)
    {
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(location.toString());
        if(sprite != null)
            this.sprite = sprite;
    }

    @Override
    public void renderAt(int x, int y)
    {
        GuiHelper.INSTANCE.drawTexturedModalRect(x, y, this.sprite, 16, 16, 0);
    }
}
