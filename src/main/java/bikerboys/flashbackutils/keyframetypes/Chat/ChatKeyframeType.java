package bikerboys.flashbackutils.keyframetypes.Chat;

import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframes.chat.ChatKeyframe;
import bikerboys.flashbackutils.recordstuff.Chat.ChatKeyframeChange;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.playback.ReplayServer;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.message.ChatVisibility;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class ChatKeyframeType implements KeyframeType<ChatKeyframe> {

    public static ChatKeyframeType INSTANCE = new ChatKeyframeType();



    @Override
    public String name() {
        return "Chat";
    }

    @Override
    public String id() {
        return "CHAT";
    }




    @Override
    public @Nullable ChatKeyframe createDirect() {
        return null;
    }

    @Override
    public @Nullable KeyframeCreatePopup<ChatKeyframe> createPopup() {

        ReplayServer replayServer = Flashback.getReplayServer();
        GameOptions settings = MinecraftClient.getInstance().options;


        ImBoolean hidden = new ImBoolean();
        float[] opacityInput = new float[]{1.0F};
        float[] sizeInput = new float[]{1.0F};
        float[] delayInput = new float[]{1.0F};
        float[] focusedheightInput = new float[]{1.0F};
        float[] unfocusedHeightInput = new float[]{1.0F};
        float[] widthInput = new float[]{1.0F};
        float[] lineSpacingInput = new float[]{1.0F};
        float[] textBackgroundOpacityInput = new float[]{1.0F};
        ImBoolean colors = new ImBoolean();


            if (settings.getChatVisibility().getValue().equals(ChatVisibility.FULL) || settings.getChatVisibility().getValue().equals(ChatVisibility.SYSTEM)) {
                hidden.set(false);
            }
            if (settings.getChatVisibility().getValue().equals(ChatVisibility.HIDDEN)) {
                hidden.set(true);
            }
            opacityInput[0] = (settings.getChatOpacity().getValue().floatValue());
            sizeInput[0] = (settings.getChatScale().getValue().floatValue());
            delayInput[0] = settings.getChatDelay().getValue().floatValue();

            focusedheightInput[0] = (settings.getChatHeightFocused().getValue().floatValue());
            unfocusedHeightInput[0] = (settings.getChatHeightUnfocused().getValue().floatValue());
            widthInput[0] = (settings.getChatWidth().getValue().floatValue());
            lineSpacingInput[0] = (settings.getChatLineSpacing().getValue().floatValue());
            textBackgroundOpacityInput[0] = (settings.getTextBackgroundOpacity().getValue().floatValue());


        System.out.println("settings {ChatVisibility} = " + settings.getChatVisibility().getValue());
        System.out.println("settings {ChatOpacity} = " + settings.getChatOpacity().getValue());
        System.out.println("settings {ChatScale} = " + settings.getChatScale().getValue());
        System.out.println("settings {ChatDelay} = " + settings.getChatDelay().getValue());
        System.out.println("settings {ChatHeightFocused} = " + settings.getChatHeightFocused().getValue());
        System.out.println("settings {ChatHeightUnfocused} = " + settings.getChatHeightUnfocused().getValue());
        System.out.println("settings {ChatWidth} = " + settings.getChatWidth().getValue());
        System.out.println("settings {ChatLineSpacing} = " + settings.getChatLineSpacing().getValue());
        System.out.println("settings {TextBackgroundOpacity} = " + settings.getTextBackgroundOpacity().getValue());
        System.out.println("settings {ChatColors} = " + settings.getChatColors().getValue());






        return () -> {






            ImGui.checkbox("Hidden", hidden);
            ImGui.sliderFloat("Text Opacity", opacityInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Text Size", sizeInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Chat Delay ", delayInput, 0.0F, 6.0F);
            ImGui.sliderFloat("Focused Height", focusedheightInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Unfocused Height", unfocusedHeightInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Width", widthInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Line Spacing", lineSpacingInput, 0.0F, 1.0F);
            ImGui.sliderFloat("Text Background Opacity", textBackgroundOpacityInput, 0.0F, 1.0F);
            ImGui.checkbox("Colors", colors);



            if (ImGui.button("Add")) {
                return new ChatKeyframe(hidden.get(),
                        opacityInput[0],
                        sizeInput[0],
                        round(delayInput[0], 1),
                        focusedheightInput[0],
                        unfocusedHeightInput[0],
                        widthInput[0],
                        lineSpacingInput[0],
                        textBackgroundOpacityInput[0],
                        colors.get());
            }
            ImGui.sameLine();
            if (ImGui.button("Cancel")) {
                ImGui.closeCurrentPopup();
            }
            return null;

        };
    }

    public static float round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }


    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return ChatKeyframeChange.class;
    }

    @Override
    public boolean supportsHandler(KeyframeHandler handler) {
        return true;
    }
}
