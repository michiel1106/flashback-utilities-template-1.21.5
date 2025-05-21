package bikerboys.flashbackutils.mixin;


import bikerboys.flashbackutils.recordstuff.HideEntity.HideEntityKeyframeChange;
import bikerboys.flashbackutils.recordstuff.command.ExecuteCommandChange;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.state.EditorState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(value = EditorState.class, remap = false)
public class EditorStateMixin {

    @Redirect(
            method = "applyKeyframes",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"
            )
    )
    private boolean skipIfAlreadyAppliedUnlessHide(Set<Class<? extends KeyframeChange>> applied, Object keyframeChangeType) {
        if (keyframeChangeType == HideEntityKeyframeChange.class || keyframeChangeType == ExecuteCommandChange.class) {

            return false; // Don't skip — allow multiple
        }

        return applied.contains(keyframeChangeType); // Original logic
    }

}
