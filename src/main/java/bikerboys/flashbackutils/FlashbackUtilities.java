package bikerboys.flashbackutils;

import bikerboys.flashbackutils.keyframetypes.HideEntityKeyframeType;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlashbackUtilities implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "flashback-utilities";

	public static SimpleOption<Boolean> EXTRA_TOGGLE;

	public static boolean RecordingHotbar;

	public ModKeyframeHandler modKeyframeHandler = new ModKeyframeHandler();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//KeyframeRegistry.register(HideEntityKeyframeType.INSTANCE);





		LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void onInitializeClient() {
		Identifier MY_HUD_LAYER = Identifier.of(MOD_ID, "custom_layer");
		Identifier IMAGE_TEXTURE = Identifier.of(MOD_ID, "textures/gui/recordingicon.png");

		KeyframeRegistry.register(HideEntityKeyframeType.INSTANCE);

		HudLayerRegistrationCallback.EVENT.register((drawer) -> {



			drawer.attachLayerAfter(IdentifiedLayer.CROSSHAIR, MY_HUD_LAYER, ((context, tickCounter) -> {
				if (Flashback.RECORDER != null) {

//public void drawTexture(Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {


					int x = context.getScaledWindowWidth() / 2 + 390;
					int y = context.getScaledWindowHeight() / 2 - 240;
					int u = 0;
					int v = 0;
					int numbs = 28;

					context.drawTexture(RenderLayer::getGuiTextured, IMAGE_TEXTURE, x, y, u, v, numbs, numbs, numbs, numbs);
				}
			}));
		});
	}
}
