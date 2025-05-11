package bikerboys.flashbackutils.recordstuff;


import bikerboys.flashbackutils.FlashbackUtilities;
import bikerboys.flashbackutils.ModKeyframeHandler;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.keyframe.impl.TickrateKeyframe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.apache.commons.math3.analysis.function.Min;

import java.util.Map;
import java.util.UUID;


public record TestChangeKeyFrame(String uuid, boolean hidden) implements KeyframeChange {
    public TestChangeKeyFrame(String uuid, boolean hidden) {
        this.uuid = uuid;
        this.hidden = hidden;
    }



    @Override
    public void apply(KeyframeHandler keyframeHandler) {

        if (MinecraftClient.getInstance().world != null) {

            if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().world.getEntity(UUID.fromString(this.uuid)) != null) {

                Entity targetedEntity = MinecraftClient.getInstance().world.getEntity(UUID.fromString(this.uuid));

                if (targetedEntity.getUuid().toString().equals(uuid) && targetedEntity != null) {

                    targetedEntity.setInvisible(this.hidden);
                }

            }

        }
    }

    public boolean isHidden() {
        return this.hidden;
    }



    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {



        boolean tohidden = ((TestChangeKeyFrame) to).hidden;
        String touuid = ((TestChangeKeyFrame) to).uuid;

        boolean fromhidden = this.hidden;
        String fromuuid = this.uuid;


        KeyframeChange from = new TestChangeKeyFrame(fromuuid, fromhidden);
        KeyframeChange to1 = new TestChangeKeyFrame(touuid, fromhidden);

        if (from == to1) {
            return from;
        } else if (from == null) {
            return to1;
        } else if (to1 == null) {
            return from;
        } else {
            return from;
        }


     //   FlashbackUtilities.modKeyframeHandler.filteredEntities.put(uuid, this.hidden);
        // Return the current keyframe (or you could return the target keyframe).
    }





}
