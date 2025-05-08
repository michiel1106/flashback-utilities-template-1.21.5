package bikerboys.flashbackutils;

import bikerboys.flashbackutils.keyframetypes.TestKeyFrameType;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.keyframe.KeyframeType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlashbackUtilities implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "flashback-utilities";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		KeyframeRegistry.register(TestKeyFrameType.INSTANCE);




		LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void onInitializeClient() {
		//KeyframeRegistry.register(TestKeyFrameType.INSTANCE);

		System.out.println(KeyframeRegistry.getTypes());
	}
}