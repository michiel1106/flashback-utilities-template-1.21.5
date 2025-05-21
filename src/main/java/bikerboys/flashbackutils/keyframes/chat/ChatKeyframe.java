package bikerboys.flashbackutils.keyframes.chat;

import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframetypes.Chat.ChatKeyframeType;
import bikerboys.flashbackutils.recordstuff.Chat.ChatKeyframeChange;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.spline.CatmullRom;
import com.moulberry.flashback.spline.Hermite;
import imgui.ImGui;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

import static bikerboys.flashbackutils.keyframetypes.Chat.ChatKeyframeType.round;

public class ChatKeyframe extends Keyframe {


    public boolean hidden;
    public float opacity;
    public float size;
    public float delay;
    public float focusedHeight;
    public float unfocusedHeight;
    public float width;
    public float lineSpacing;
    public float textBackgroundOpacity;
    public boolean colors;

    public ChatKeyframe(boolean hidden, float opacity, float size, float delay, float focusedHeight, float unfocusedHeight, float width, float lineSpacing, float textBackgroundOpacity, boolean colors) {
        this(hidden, opacity, size, delay, focusedHeight, unfocusedHeight, width, lineSpacing, textBackgroundOpacity, colors, InterpolationType.getDefault());
    }

    public ChatKeyframe(boolean hidden, float opacity, float size, float delay, float focusedHeight, float unfocusedHeight, float width, float lineSpacing, float textBackgroundOpacity, boolean colors, InterpolationType interpolationType) {
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
        this.interpolationType(interpolationType);
    }




    @Override
    public KeyframeType<?> keyframeType() {
        return ChatKeyframeType.INSTANCE;
    }

    @Override
    public Keyframe copy() {
        return new ChatKeyframe(
                this.hidden,
                this.opacity,
                this.size,
                this.delay,
                this.focusedHeight,
                this.unfocusedHeight,
                this.width,
                this.lineSpacing,
                this.textBackgroundOpacity,
                this.colors,
                this.interpolationType()
        );
    }

    @Override
    public KeyframeChange createChange() {

        return new ChatKeyframeChange(
                this.hidden,
                this.opacity,
                this.size,
                this.delay,
                this.focusedHeight,
                this.unfocusedHeight,
                this.width,
                this.lineSpacing,
                this.textBackgroundOpacity,
                this.colors
        );


    }

    private float clamp01(float value) {
        return Math.max(0.0F, Math.min(1.0F, value));
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        float time1 = t1 - t0;
        float time2 = t2 - t0;
        float time3 = t3 - t0;

        ChatKeyframe k1 = (ChatKeyframe)p1;
        ChatKeyframe k2 = (ChatKeyframe)p2;
        ChatKeyframe k3 = (ChatKeyframe)p3;

        float opacity = clamp01(CatmullRom.value(this.opacity, k1.opacity, k2.opacity, k3.opacity, time1, time2, time3, amount));
        float size = clamp01(CatmullRom.value(this.size, k1.size, k2.size, k3.size, time1, time2, time3, amount));
        float delay = clamp01(CatmullRom.value(this.delay, k1.delay, k2.delay, k3.delay, time1, time2, time3, amount));
        float focusedHeight = clamp01(CatmullRom.value(this.focusedHeight, k1.focusedHeight, k2.focusedHeight, k3.focusedHeight, time1, time2, time3, amount));
        float unfocusedHeight = clamp01(CatmullRom.value(this.unfocusedHeight, k1.unfocusedHeight, k2.unfocusedHeight, k3.unfocusedHeight, time1, time2, time3, amount));
        float width = clamp01(CatmullRom.value(this.width, k1.width, k2.width, k3.width, time1, time2, time3, amount));
        float lineSpacing = clamp01(CatmullRom.value(this.lineSpacing, k1.lineSpacing, k2.lineSpacing, k3.lineSpacing, time1, time2, time3, amount));
        float textBackgroundOpacity = clamp01(CatmullRom.value(this.textBackgroundOpacity, k1.textBackgroundOpacity, k2.textBackgroundOpacity, k3.textBackgroundOpacity, time1, time2, time3, amount));

        boolean hidden = this.hidden;
        boolean colors = this.colors;

        return new ChatKeyframeChange(hidden, opacity, size, delay, focusedHeight, unfocusedHeight, width, lineSpacing, textBackgroundOpacity, colors);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Integer, Keyframe> keyframes, float amount) {


        float delay = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).delay), amount);

        float size = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).size), amount);
        float opacity = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).opacity), amount);
        float focusedHeight = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).focusedHeight), amount);
        float unfocusedHeight = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).unfocusedHeight), amount);
        float width = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).width), amount);
        float lineSpacing = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).lineSpacing), amount);
        float textBackgroundOpacity = (float) Hermite.value(Maps.transformValues(keyframes, k -> (double) ((ChatKeyframe)k).textBackgroundOpacity), amount);

        // Take non-interpolated values from the current keyframe
        boolean hidden = this.hidden;
        boolean colors = this.colors;

        return new ChatKeyframeChange(hidden, opacity, size, delay, focusedHeight, unfocusedHeight, width, lineSpacing, textBackgroundOpacity, colors);




    }

    public static class TypeAdapter implements JsonSerializer<ChatKeyframe>, JsonDeserializer<ChatKeyframe> {
        public TypeAdapter() {
        }


        public ChatKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();


            if (!jsonObject.has("hidden")) {
                throw new JsonParseException("Required field 'hidden' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("opacity")) {
                throw new JsonParseException("Required field 'opacity' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("size")) {
                throw new JsonParseException("Required field 'size' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("delay")) {
                throw new JsonParseException("Required field 'delay' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("focusedHeight")) {
                throw new JsonParseException("Required field 'focusedHeight' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("unfocusedHeight")) {
                throw new JsonParseException("Required field 'unfocusedHeight' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("width")) {
                throw new JsonParseException("Required field 'width' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("lineSpacing")) {
                throw new JsonParseException("Required field 'lineSpacing' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("textbackgroundopacity")) {
                throw new JsonParseException("Required field 'textBackgroundOpacity' is missing in JSON: " + jsonObject);
            }
            if (!jsonObject.has("colors")) {
                throw new JsonParseException("Required field 'colors' is missing in JSON: " + jsonObject);
            }


            boolean hidden1 = jsonObject.get("hidden").getAsBoolean();
            float opacity1 = jsonObject.get("opacity").getAsFloat();
            float size1 = jsonObject.get("size").getAsFloat();
            float delay1 = jsonObject.get("delay").getAsFloat();
            float focusedHeight1 = jsonObject.get("focusedHeight").getAsFloat();
            float unfocusedHeight1 = jsonObject.get("unfocusedHeight").getAsFloat();
            float width1 = jsonObject.get("width").getAsFloat();
            float lineSpacing1 = jsonObject.get("lineSpacing").getAsFloat();
            float textBackgroundOpacity1 = jsonObject.get("textbackgroundopacity").getAsFloat();
            boolean colors1 = jsonObject.get("colors").getAsBoolean();
            // Defensive check for interpolation_type
            if (!jsonObject.has("interpolation_type")) {
                throw new JsonParseException("Required field 'interpolation_type' is missing in JSON: " + jsonObject);
            }

            InterpolationType interpolationType = context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new ChatKeyframe(hidden1, opacity1, size1, delay1, focusedHeight1, unfocusedHeight1, width1, lineSpacing1, textBackgroundOpacity1, colors1, interpolationType);
        }


        public JsonElement serialize(ChatKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("hidden", src.hidden);
            jsonObject.addProperty("opacity", src.opacity);
            jsonObject.addProperty("size", src.size);
            jsonObject.addProperty("delay", src.delay);
            jsonObject.addProperty("focusedHeight", src.focusedHeight);
            jsonObject.addProperty("unfocusedHeight", src.unfocusedHeight);
            jsonObject.addProperty("width", src.width);
            jsonObject.addProperty("lineSpacing", src.lineSpacing);
            jsonObject.addProperty("textbackgroundopacity", src.textBackgroundOpacity);
            jsonObject.addProperty("colors", src.colors);



            jsonObject.addProperty("type", "chat");
            jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));

            return jsonObject;
        }
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {

        ImGui.setNextItemWidth(160.0F);
        if (ImGui.checkbox("Hidden", this.hidden)) {
            boolean newHidden = !this.hidden;
            update.accept((keyframe) -> {
                ((ChatKeyframe)keyframe).hidden = newHidden;
            });
        }


        ImGui.setNextItemWidth(160.0F);
        float[] opacityInput = new float[]{this.opacity};
        if (ImGui.sliderFloat("Text Opacity", opacityInput, 0, 1)) {
            float newValue = opacityInput[0];
            if (this.opacity != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).opacity = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] sizeInput = new float[]{this.size};
        if (ImGui.sliderFloat("Text Size", sizeInput, 0, 1)) {
            float newValue = sizeInput[0];
            if (this.size != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).size = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] delayInput = new float[]{this.delay};
        if (ImGui.sliderFloat("Chat Delay", delayInput, 0.0F, 6.0F)) {
            float newValue = round(delayInput[0], 1); // Round to 1 decimal place
            if (this.delay != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).delay = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] focusedHeightInput = new float[]{this.focusedHeight};
        if (ImGui.sliderFloat("Focused Height", focusedHeightInput, 0, 1)) {
            float newValue = focusedHeightInput[0];
            if (this.focusedHeight != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).focusedHeight = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] unfocusedHeightInput = new float[]{this.unfocusedHeight};
        if (ImGui.sliderFloat("Unfocused Height", unfocusedHeightInput, 0, 1)) {
            float newValue = unfocusedHeightInput[0];
            if (this.unfocusedHeight != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).unfocusedHeight = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] widthInput = new float[]{this.width};
        if (ImGui.sliderFloat("Width", widthInput, 0, 1)) {
            float newValue = widthInput[0];
            if (this.width != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).width = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] lineSpacingInput = new float[]{this.lineSpacing};
        if (ImGui.sliderFloat("Line Spacing", lineSpacingInput, 0, 1)) {
            float newValue = lineSpacingInput[0];
            if (this.lineSpacing != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).lineSpacing = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        float[] textBackgroundOpacityInput = new float[]{this.textBackgroundOpacity};
        if (ImGui.sliderFloat("Text Background Opacity", textBackgroundOpacityInput, 0, 1)) {
            float newValue = textBackgroundOpacityInput[0];
            if (this.textBackgroundOpacity != newValue) {
                update.accept((keyframe) -> {
                    ((ChatKeyframe)keyframe).textBackgroundOpacity = newValue;
                });
            }
        }


        ImGui.setNextItemWidth(160.0F);
        if (ImGui.checkbox("Colors", this.colors)) {
            boolean newColors = !this.colors;
            update.accept((keyframe) -> {
                ((ChatKeyframe)keyframe).colors = newColors;
            });
        }




        super.renderEditKeyframe(update);
    }
}



