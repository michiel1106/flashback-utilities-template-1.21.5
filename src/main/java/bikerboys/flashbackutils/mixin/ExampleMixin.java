package bikerboys.flashbackutils.mixin;

import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.impl.*;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;
import java.util.Objects;

@Debug(export = true)
@Mixin(value = com.moulberry.flashback.keyframe.Keyframe.TypeAdapter.class, remap = false)
public class ExampleMixin {

	/*

	@Inject(at = @At("HEAD"), method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/moulberry/flashback/keyframe/Keyframe;", remap = false, cancellable = true)
	public void deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<Keyframe> cir) {


		JsonObject jsonObject = json.getAsJsonObject();

		System.out.println(jsonObject.get("type").getAsString());

		if (jsonObject.has("type")) {
			Keyframe var10000;

			if (jsonObject.get("type").getAsString().equals("test_keyframe")) {
				System.out.println("it should be registered deserialize");
			}





			switch (jsonObject.get("type").getAsString()) {
				case "testtype" -> var10000 = (Keyframe)context.deserialize(json, TestKeyFrame.class);
				case "test_keyframe" -> var10000 = (Keyframe)context.deserialize(json, TestKeyFrame.class);


			}

		}


	}
	*/

	@Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/moulberry/flashback/keyframe/Keyframe;", at = @At("HEAD"), cancellable = true)
	public void whatever(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<Keyframe> cir) {
		JsonObject jsonObject = json.getAsJsonObject();
		if (jsonObject.has("type")) {
			Keyframe var10000;
			switch (jsonObject.get("type").getAsString()) {
				case "camera" -> var10000 = (Keyframe)context.deserialize(json, CameraKeyframe.class);
				case "camera_orbit" -> var10000 = (Keyframe)context.deserialize(json, CameraOrbitKeyframe.class);
				case "track_entity" -> var10000 = (Keyframe)context.deserialize(json, TrackEntityKeyframe.class);
				case "fov" -> var10000 = (Keyframe)context.deserialize(json, FOVKeyframe.class);
				case "tickrate" -> var10000 = (Keyframe)context.deserialize(json, TickrateKeyframe.class);
				case "freeze" -> var10000 = (Keyframe)context.deserialize(json, FreezeKeyframe.class);
				case "timelapse" -> var10000 = (Keyframe)context.deserialize(json, TimelapseKeyframe.class);
				case "time" -> var10000 = (Keyframe)context.deserialize(json, TimeOfDayKeyframe.class);
				case "camera_shake" -> var10000 = (Keyframe)context.deserialize(json, CameraShakeKeyframe.class);
				default -> var10000 = (Keyframe)context.deserialize(json, typeOfT);
			}

			Keyframe keyframe = var10000;
			keyframe.interpolationType((InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
			cir.setReturnValue(keyframe);
		} else {
			throw new RuntimeException("Unable to determine type of keyframe for: " + String.valueOf(jsonObject));
		}

		cir.cancel();
	}


	@Inject(at = @At("HEAD"), method = "serialize(Lcom/moulberry/flashback/keyframe/Keyframe;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", remap = false, cancellable = true)
	public void serialiezing(Keyframe src, Type typeOfSrc, JsonSerializationContext context, CallbackInfoReturnable<JsonElement> cir) {
		JsonObject jsonObject;
		switch (src) {
			case CameraKeyframe cameraKeyframe:
				jsonObject = (JsonObject)context.serialize(cameraKeyframe);
				jsonObject.addProperty("type", "camera");
				break;
			case CameraOrbitKeyframe cameraOrbitKeyframe:
				jsonObject = (JsonObject)context.serialize(cameraOrbitKeyframe);
				jsonObject.addProperty("type", "camera_orbit");
				break;
			case FOVKeyframe fovKeyframe:
				jsonObject = (JsonObject)context.serialize(fovKeyframe);
				jsonObject.addProperty("type", "fov");
				break;
			case TickrateKeyframe tickrateKeyframe:
				jsonObject = (JsonObject)context.serialize(tickrateKeyframe);
				jsonObject.addProperty("type", "tickrate");
				break;
			case FreezeKeyframe freezeKeyframe:
				jsonObject = (JsonObject)context.serialize(freezeKeyframe);
				jsonObject.addProperty("type", "freeze");
				break;
			case TimeOfDayKeyframe timeOfDayKeyframe:
				jsonObject = (JsonObject)context.serialize(timeOfDayKeyframe);
				jsonObject.addProperty("type", "time");
				break;
			case CameraShakeKeyframe cameraShakeKeyframe:
				jsonObject = (JsonObject)context.serialize(cameraShakeKeyframe);
				jsonObject.addProperty("type", "camera_shake");
				break;
			default:
				jsonObject = (JsonObject)context.serialize(src);
				jsonObject.addProperty("type", "testtype");

		}

		jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));
		cir.setReturnValue(jsonObject);

		cir.cancel();


	}





	/*



	@Inject(at = @At("HEAD"), method = "serialize(Lcom/moulberry/flashback/keyframe/Keyframe;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", remap = false, cancellable = true)
	public void serialize(Keyframe src, Type typeOfSrc, JsonSerializationContext context, CallbackInfoReturnable<JsonElement> cir) {






		if (src instanceof TestKeyFrame) {
			System.out.println("it should be registered deserialize");
		}

		JsonObject jsonObject;
        if (Objects.requireNonNull(src) instanceof TestKeyFrame testKeyFrame) {
            jsonObject = (JsonObject) context.serialize(testKeyFrame);
            jsonObject.addProperty("type", "testtype");
        } else {
            throw new IllegalStateException("Unexpected value: " + src);
        }



	}

	 */

}
