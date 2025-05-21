package bikerboys.flashbackutils.recordstuff.HideEntity;


import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.UUID;


public record HideEntityKeyframeChange(String uuid, boolean hidden) implements KeyframeChange {
    public HideEntityKeyframeChange(String uuid, boolean hidden) {
        this.uuid = uuid;
        this.hidden = hidden;
    }



    @Override
    public void apply(KeyframeHandler keyframeHandler) {


       // System.out.println("help " + this.uuid + " " + this.hidden);


        if (MinecraftClient.getInstance().world != null) {

            Entity entity = null;

            try {
                entity = MinecraftClient.getInstance().world.getEntity(UUID.fromString(this.uuid));
            } catch (Exception e) {

            }


            if (MinecraftClient.getInstance() != null && entity != null) {



                if (entity.getUuid().toString().equals(uuid) && entity != null) {

                    entity.setInvisible(this.hidden);
                }

            }

        }
    }

    public boolean isHidden() {

        return this.hidden;
    }



    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {



        return this;



     //   FlashbackUtilities.modKeyframeHandler.filteredEntities.put(uuid, this.hidden);
        // Return the current keyframe (or you could return the target keyframe).
    }





}
