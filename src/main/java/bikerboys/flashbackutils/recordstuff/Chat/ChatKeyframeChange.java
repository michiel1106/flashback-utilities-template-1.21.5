package bikerboys.flashbackutils.recordstuff.Chat;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.message.ChatVisibility;

public record ChatKeyframeChange(
        Boolean hidden,
        float opacity,
        float size,
        float delay,
        float focusedHeight,
        float unfocusedHeight,
        float width,
        float lineSpacing,
        float textBackgroundOpacity,
        Boolean colors) implements KeyframeChange {


    public ChatKeyframeChange(Boolean hidden, float opacity, float size, float delay, float focusedHeight, float unfocusedHeight, float width, float lineSpacing, float textBackgroundOpacity, Boolean colors) {
        this.hidden = hidden;
        this.opacity = opacity;
        this.size = size;
        this.delay = delay;
        this.focusedHeight = focusedHeight;
        this.unfocusedHeight = unfocusedHeight;
        this.width = width;
        this.lineSpacing = lineSpacing;
        this.textBackgroundOpacity = textBackgroundOpacity;
        this.colors = colors;

    }

    @Override
    public void apply(KeyframeHandler keyframeHandler) {



        MinecraftClient mc = keyframeHandler.getMinecraft();


        if(mc != null) {
            GameOptions settings = mc.options;


                settings.getChatVisibility().setValue(this.hidden ? ChatVisibility.HIDDEN : ChatVisibility.FULL);




            settings.getChatOpacity().setValue((double) opacity);

                settings.getChatScale().setValue((double) size);

                settings.getChatDelay().setValue((double) delay);

                settings.getChatHeightFocused().setValue((double) focusedHeight);

                settings.getChatHeightUnfocused().setValue((double) unfocusedHeight);

                settings.getChatWidth().setValue((double) width);

                settings.getChatLineSpacing().setValue((double) lineSpacing);

                settings.getTextBackgroundOpacity().setValue((double) textBackgroundOpacity);

                settings.getChatColors().setValue(colors);

                Double value1 = settings.getChatWidth().getValue();
                Double value = settings.getTextBackgroundOpacity().getValue();

                System.out.println(value1);
                System.out.println(value);





        }


    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        ChatKeyframeChange other = (ChatKeyframeChange) to;

        Boolean hidden = this.hidden; // Booleans: no interpolation, retain this
        Boolean colors = this.colors;

        float opacity = (float) Interpolation.linear(this.opacity, other.opacity, amount);
        float size = (float) Interpolation.linear(this.size, other.size, amount);
        float delay = (float) Interpolation.linear(this.delay, other.delay, amount);
        float focusedHeight = (float) Interpolation.linear(this.focusedHeight, other.focusedHeight, amount);
        float unfocusedHeight = (float) Interpolation.linear(this.unfocusedHeight, other.unfocusedHeight, amount);
        float width = (float) Interpolation.linear(this.width, other.width, amount);
        float lineSpacing = (float) Interpolation.linear(this.lineSpacing, other.lineSpacing, amount);
        float textBackgroundOpacity = (float) Interpolation.linear(this.textBackgroundOpacity, other.textBackgroundOpacity, amount);

        return new ChatKeyframeChange(
                hidden,
                opacity,
                size,
                delay,
                focusedHeight,
                unfocusedHeight,
                width,
                lineSpacing,
                textBackgroundOpacity,
                colors
        );
    }
}
