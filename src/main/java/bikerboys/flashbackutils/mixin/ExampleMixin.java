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



}
