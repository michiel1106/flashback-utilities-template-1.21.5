package bikerboys.flashbackutils.mixin;

import bikerboys.flashbackutils.keyframes.HideEntityKeyframe;
import com.google.gson.*;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.impl.*;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Debug(export = true)
@Mixin(value = Keyframe.TypeAdapter.class, remap = false)
public class RegisterMixin {



	@Inject(method = "serialize(Lcom/moulberry/flashback/keyframe/Keyframe;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", at = @At("HEAD"), cancellable = true)
	private void serialize(Keyframe src, Type typeOfSrc, JsonSerializationContext context, CallbackInfoReturnable<JsonElement> cir) {
		if (src instanceof HideEntityKeyframe hideEntityKeyframe) {
			JsonObject jsonObject;

			jsonObject = (JsonObject)context.serialize(hideEntityKeyframe);
			jsonObject.addProperty("type", "hideentity");
			jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));
			cir.setReturnValue(jsonObject);
			cir.cancel();
		}

	}



	@Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/moulberry/flashback/keyframe/Keyframe;", at = @At("HEAD"), cancellable = true)
	private void deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<Keyframe> cir) {
		JsonObject jsonObject = json.getAsJsonObject();
		if (jsonObject.has("type")) {
			Keyframe tempkeyframe;
			switch (jsonObject.get("type").getAsString()) {
				case "hideentity" -> {
					tempkeyframe = (Keyframe) context.deserialize(json, HideEntityKeyframe.class);
					tempkeyframe.interpolationType((InterpolationType) context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
					cir.setReturnValue(tempkeyframe);
					cir.cancel();
				}

			}
		}

	}













}
