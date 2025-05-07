package bikerboys.flashbackutils.mixin;

import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.impl.*;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Debug(export = true, print = true)
@Mixin(value = com.moulberry.flashback.keyframe.Keyframe.TypeAdapter.class, priority = 0)
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/moulberry/flashback/keyframe/Keyframe;", remap = false)
	public void deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<Keyframe> cir) {

		System.out.println(cir.getReturnValue());

		JsonObject jsonObject = json.getAsJsonObject();

		if (jsonObject.has("type")) {
			Keyframe var10000;


			switch (jsonObject.get("type").getAsString()) {
				case "int1" -> var10000 = (Keyframe)context.deserialize(json, TestKeyFrame.class);

			}

		}

	}

	@Inject(at = @At("HEAD"), method = "serialize(Lcom/moulberry/flashback/keyframe/Keyframe;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", remap = false)
	public void serialize(Keyframe src, Type typeOfSrc, JsonSerializationContext context, CallbackInfoReturnable<JsonElement> cir) {
		System.out.println("AAAAAAAAA" + cir.getReturnValue());

		ClientTickEvents.END_CLIENT_TICK.register((minecraftClient -> {
			System.out.println("AAAAAAAA" + cir.getReturnValue());
		}));

		JsonObject jsonObject;
		switch (src) {
			case TestKeyFrame testKeyFrame:
				jsonObject = (JsonObject)context.serialize(testKeyFrame);
				jsonObject.addProperty("type", "int1");
				break;

			default:
				throw new IllegalStateException("Unexpected value: " + src);
		}

	}

}
