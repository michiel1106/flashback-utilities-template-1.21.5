package bikerboys.flashbackutils;

import bikerboys.flashbackutils.interfaces.FlashbackConfigAccess;
import bikerboys.flashbackutils.keyframetypes.Chat.ChatKeyframeType;
import bikerboys.flashbackutils.keyframetypes.HideEntity.HideEntityKeyframeType;
import bikerboys.flashbackutils.keyframetypes.command.ExecuteCommandKeyframeType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.keyframe.KeyframeRegistry;
import com.moulberry.flashback.packet.FlashbackClearEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FlashbackUtilities implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "flashback-utilities";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static List<TextRenderObject> textlist = new ArrayList<>();
	public static boolean resetchat;
	public static final Set<Object> executedKeyframes = new HashSet<>();
	Identifier MY_HUD_LAYER = Identifier.of(MOD_ID, "custom_layer");
	Identifier IMAGE_TEXTURE = Identifier.of(MOD_ID, "textures/gui/recordingicon.png");
	FlashbackConfigAccess cfg = (FlashbackConfigAccess) Flashback.getConfig();



	@Override
	public void onInitialize() {

	}




	@Override
	public void onInitializeClient() {



		HudLayerRegistrationCallback.EVENT.register((drawer) -> {
			drawer.attachLayerAfter(IdentifiedLayer.CROSSHAIR, Identifier.of(MOD_ID, "textrenderer"), ((context, tickCounter) -> {

				textlist.forEach((textRenderObject -> {
					int color = textRenderObject.color;
					Text text = textRenderObject.text;
					int size = textRenderObject.size;
					int x = textRenderObject.x;
					int y = textRenderObject.y;

					context.drawText(MinecraftClient.getInstance().textRenderer, text, context.getScaledWindowWidth()/2 + x , context.getScaledWindowHeight()/2 + y, color, false);


				}));

			}));

		});

		ClientCommandRegistrationCallback.EVENT.register((dispactcher, commandregistry) -> dispactcher.register(ClientCommandManager.literal("text").then(ClientCommandManager.argument("text", StringArgumentType.string()).then(ClientCommandManager.argument("x", IntegerArgumentType.integer()).then(ClientCommandManager.argument("y", IntegerArgumentType.integer()).executes(context -> {

			String string = StringArgumentType.getString(context, "text");
			int x = IntegerArgumentType.getInteger(context, "x");
			int y = IntegerArgumentType.getInteger(context, "y");

			TextRenderObject what = new TextRenderObject(Text.literal(string), 1, 32232, x, y);
			textlist.add(what);

			return 1;
		}))))));






		KeyframeRegistry.register(HideEntityKeyframeType.INSTANCE);
		KeyframeRegistry.register(ChatKeyframeType.INSTANCE);
		KeyframeRegistry.register(ExecuteCommandKeyframeType.INSTANCE);





		HudLayerRegistrationCallback.EVENT.register((drawer) -> {
			drawer.attachLayerAfter(IdentifiedLayer.CROSSHAIR, MY_HUD_LAYER, ((context, tickCounter) -> {
				if (cfg != null) {
					boolean isrecordingiconenabled = cfg.getRecordingIcon();

					if (Flashback.RECORDER != null && isrecordingiconenabled) {
						int x = context.getScaledWindowWidth() / 2 + 390;
						int y = context.getScaledWindowHeight() / 2 - 240;
						int u = 0;
						int v = 0;
						int numbs = 28;

						context.drawTexture(RenderLayer::getGuiTextured, IMAGE_TEXTURE, x, y, u, v, numbs, numbs, numbs, numbs);
					}
				}
			}));
		});



	}
}
