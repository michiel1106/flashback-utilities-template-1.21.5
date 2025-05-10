package bikerboys.flashbackutils;

import bikerboys.flashbackutils.keyframetypes.TestKeyFrameType;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.flashback.state.EditorScene;
import com.moulberry.flashback.state.EditorState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FlashbackUtilities implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "flashback-utilities";

	public static ModKeyframeHandler modKeyframeHandler = new ModKeyframeHandler();

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


		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("d").executes((context -> {

			FlashbackUtilities.modKeyframeHandler.filteredEntities.forEach(((string, aBoolean) -> {
				context.getSource().sendMessage(Text.literal(string));
				context.getSource().sendMessage(Text.literal(context.getSource().getWorld().getEntity(UUID.fromString(string)).toString()));
				context.getSource().sendMessage(Text.literal(aBoolean.toString()));


			}));



			return 1;
		})))));



		LOGGER.info("Hello Fabric world!");
	}

	@Override
	public void onInitializeClient() {
		KeyframeRegistry.register(TestKeyFrameType.INSTANCE);




		System.out.println(KeyframeRegistry.getTypes());
	}
}