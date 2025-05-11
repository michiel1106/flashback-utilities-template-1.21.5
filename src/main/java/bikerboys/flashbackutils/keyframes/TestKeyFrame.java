package bikerboys.flashbackutils.keyframes;


import bikerboys.flashbackutils.keyframetypes.TestKeyFrameType;
import bikerboys.flashbackutils.recordstuff.TestChangeKeyFrame;
import com.google.gson.*;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.impl.FreezeKeyframe;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import imgui.ImGui;
import imgui.type.ImString;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

public class TestKeyFrame extends Keyframe {
    public ImString uuid;
    public boolean hidden;

    public TestKeyFrame(ImString uuid, Boolean hidden) {
        this(uuid, hidden, InterpolationType.getDefault());
    }

    public TestKeyFrame(ImString uuid, boolean hidden, InterpolationType interpolationType) {
        this.uuid = uuid;
        this.hidden = hidden;
        this.interpolationType(interpolationType);
    }

    public KeyframeType<?> keyframeType() {
        return TestKeyFrameType.INSTANCE;
    }

    public TestKeyFrame copy() {
        return new TestKeyFrame(this.uuid, this.hidden, this.interpolationType());
    }






    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {




        ImGui.setNextItemWidth(160.0F);
        if (ImGui.checkbox("Hidden", this.hidden)) {
            boolean newHidden = !this.hidden;
            update.accept((keyframe) -> {
                ((TestKeyFrame)keyframe).hidden = newHidden;
            });
        }

        ImGui.setNextItemWidth(160.0F);
        if (ImGui.inputText("Target UUID", this.uuid)) {
            String newVal = this.uuid.get();

            if (!this.uuid.get().equals(newVal)) {
                update.accept((keyframe) -> {
                    ((TestKeyFrame) keyframe).uuid = new ImString(newVal);
                });
            }
        }


    }






    public KeyframeChange createChange() {
        return new TestChangeKeyFrame(this.uuid.get(), this.hidden);
    }

    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {


        return this.createChange();
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Integer, Keyframe> map, float v) {
        return this.createChange();
    }


    public static class TypeAdapter implements JsonSerializer<TestKeyFrame>, JsonDeserializer<TestKeyFrame> {
        public TypeAdapter() {
        }


        public TestKeyFrame deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            // Defensive check for required field "int1"
            if (!jsonObject.has("uuid")) {
                throw new JsonParseException("Required field 'int1' is missing in JSON: " + jsonObject);
            }

            String text = jsonObject.get("uuid").getAsString();
            boolean hidden1 = jsonObject.get("hidden").getAsBoolean();

            // Defensive check for interpolation_type
            if (!jsonObject.has("interpolation_type")) {
                throw new JsonParseException("Required field 'interpolation_type' is missing in JSON: " + jsonObject);
            }

            InterpolationType interpolationType = context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new TestKeyFrame(new ImString(text), hidden1, interpolationType);
        }


        public JsonElement serialize(TestKeyFrame src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("uuid", src.uuid.get()); // Ensure this value is never null
            jsonObject.addProperty("hidden", src.hidden); // Ensure this value is never null
            jsonObject.addProperty("type", "hideentity");
            jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));

            return jsonObject;
        }
    }
}
