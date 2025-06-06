package bikerboys.flashbackutils.mixin;


import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframes.chat.ChatKeyframe;
import bikerboys.flashbackutils.keyframes.command.ExecuteCommandKeyframe;
import com.google.gson.GsonBuilder;
import com.moulberry.flashback.FlashbackGson;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = FlashbackGson.class, remap = false)
public class typeadaptermixin {


    @Debug(export = true)
    @Inject(method = "build", at = @At("RETURN"), cancellable = true)
    private static void injectadapater(CallbackInfoReturnable<GsonBuilder> cir){
        GsonBuilder builder = cir.getReturnValue();

        // Inject your custom keyframe adapter
        builder.registerTypeAdapter(HideEntityKeyframe.class, new HideEntityKeyframe.TypeAdapter());
        builder.registerTypeAdapter(ChatKeyframe.class, new ChatKeyframe.TypeAdapter());
        builder.registerTypeAdapter(ExecuteCommandKeyframe.class, new ExecuteCommandKeyframe.TypeAdapter());

        cir.setReturnValue(builder);

    }
}
