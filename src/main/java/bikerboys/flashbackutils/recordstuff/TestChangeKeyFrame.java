package bikerboys.flashbackutils.recordstuff;


import bikerboys.flashbackutils.FlashbackUtilities;
import bikerboys.flashbackutils.ModKeyframeHandler;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.keyframe.impl.TickrateKeyframe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;

import java.util.Map;


public record TestChangeKeyFrame(String uuid, boolean hidden) implements KeyframeChange {
    public TestChangeKeyFrame(String uuid, boolean hidden) {
        this.uuid = uuid;
        this.hidden = hidden;
    }



    @Override
    public void apply(KeyframeHandler keyframeHandler) {



        FlashbackUtilities.modKeyframeHandler.filteredEntities.put(uuid, hidden);


    }

    public boolean isHidden() {
        return this.hidden;
    }



    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {



     //   FlashbackUtilities.modKeyframeHandler.filteredEntities.put(uuid, this.hidden);
        return this;  // Return the current keyframe (or you could return the target keyframe).
    }





}
