package bikerboys.flashbackutils.mixin;


import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import bikerboys.flashbackutils.recordstuff.TestChangeKeyFrame;
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
        builder.registerTypeAdapter(TestKeyFrame.class, new TestKeyFrame.TypeAdapter());

        cir.setReturnValue(builder);

    }
}
