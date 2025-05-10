package bikerboys.flashbackutils.mixin;


import bikerboys.flashbackutils.FlashbackUtilities;
import bikerboys.flashbackutils.ModKeyframeHandler;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(value = EntityRenderDispatcher.class)
public abstract class RenderKeyFrameMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    public void shouldirender (Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        EditorState editorState = EditorStateManager.getCurrent();
        Map<String, Boolean> filteredEntities = FlashbackUtilities.modKeyframeHandler.filteredEntities;
        if (filteredEntities != null && editorState != null) {




            if (filteredEntities.containsKey(entity.getUuid().toString()) && filteredEntities.get(entity.getUuid().toString())) {

                cir.setReturnValue(false);

            }

        }

    }


}
