package net.xalcon.technomage.client.renderer.block;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Optional;

public class SingleGroupModelState implements IModelState
{
    private String group;

    public SingleGroupModelState(String group)
    {
        this.group = group;
    }

    @Override
    public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> part)
    {
        if(part.isPresent())
        {
            for (UnmodifiableIterator<String> it = Models.getParts(part.get()); it.hasNext(); )
            {
                if(this.group.equals(it.next()))
                {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(TRSRTransformation.identity());
    }
}
