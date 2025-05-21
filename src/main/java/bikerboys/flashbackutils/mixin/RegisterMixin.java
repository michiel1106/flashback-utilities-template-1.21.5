package bikerboys.flashbackutils.mixin;

import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframes.chat.ChatKeyframe;
import bikerboys.flashbackutils.keyframes.command.ExecuteCommandKeyframe;
import com.google.gson.*;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
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
		if (src instanceof ChatKeyframe chatKeyframe) {
			JsonObject jsonObject;

			jsonObject = (JsonObject)context.serialize(chatKeyframe);
			jsonObject.addProperty("type", "chat");
			jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));
			cir.setReturnValue(jsonObject);
			cir.cancel();
		}
		if (src instanceof ExecuteCommandKeyframe executeCommandKeyframe) {
			JsonObject jsonObject;

			jsonObject = (JsonObject)context.serialize(executeCommandKeyframe);
			jsonObject.addProperty("type", "executecommand");
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
				case "chat" -> {
					tempkeyframe = (Keyframe) context.deserialize(json, ChatKeyframe.class);
					tempkeyframe.interpolationType((InterpolationType) context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
					cir.setReturnValue(tempkeyframe);
					cir.cancel();
				}
				case "executecommand" -> {
					tempkeyframe = (Keyframe) context.deserialize(json, ExecuteCommandKeyframe.class);
					tempkeyframe.interpolationType((InterpolationType) context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
					cir.setReturnValue(tempkeyframe);
					cir.cancel();
				}


			}
		}

	}













}
