package bikerboys.flashbackutils.mixin;

import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import com.google.gson.*;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.impl.*;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.reflect.Type;

@Debug(export = true)
@Mixin(value = com.moulberry.flashback.keyframe.Keyframe.TypeAdapter.class, remap = false)
public class RegisterMixin {




	/**
	 * @author me
	 * @reason im not proud of it but this way it actually saves
	 */
	@Overwrite
	public JsonElement serialize(Keyframe src, Type typeOfSrc, JsonSerializationContext context) {
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


			case TestKeyFrame testKeyFrame:
				jsonObject = (JsonObject)context.serialize(testKeyFrame);
				jsonObject.addProperty("type", "hideentity");
				break;
			default:
				throw new IllegalStateException("Unknown keyframe type: " + String.valueOf(src.getClass()));
		}

		jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));
		return jsonObject;
	}



	/**
	 * @author me
	 * @reason im not proud of it but this way it actually saves
	 */
	@Overwrite
	public Keyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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


				case "hideentity" -> var10000 = (Keyframe)context.deserialize(json, TestKeyFrame.class);
				default -> throw new IllegalStateException("Unknown keyframe type: ");
			}

			Keyframe keyframe = var10000;
			keyframe.interpolationType((InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class));
			return keyframe;
		} else {
			throw new RuntimeException("Unable to determine type of keyframe for: " + String.valueOf(jsonObject));
		}
	}





}
