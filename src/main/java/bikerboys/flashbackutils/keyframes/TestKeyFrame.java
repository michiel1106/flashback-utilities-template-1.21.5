package bikerboys.flashbackutils.keyframes;


import bikerboys.flashbackutils.keyframetypes.TestKeyFrameType;
import bikerboys.flashbackutils.recordstuff.TestChangeKeyFrame;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTimeOfDay;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import com.moulberry.flashback.spline.CatmullRom;
import com.moulberry.flashback.spline.Hermite;
import imgui.ImGui;
import imgui.type.ImString;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

public class TestKeyFrame extends Keyframe {
    public ImString int1;

    public TestKeyFrame(ImString int1) {
        this(int1, InterpolationType.getDefault());
    }

    public TestKeyFrame(ImString int1, InterpolationType interpolationType) {
        this.int1 = int1;
        this.interpolationType(interpolationType);
    }

    public KeyframeType<?> keyframeType() {
        return TestKeyFrameType.INSTANCE;
    }

    public TestKeyFrame copy() {
        return new TestKeyFrame(this.int1, this.interpolationType());
    }

    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
        ImGui.setNextItemWidth(160.0F);
        ImString input = new ImString();
        if (ImGui.inputText("int1", input) && this.int1 != input) {
            update.accept((keyframe) -> {
                ((TestKeyFrame)keyframe).int1 = input;
            });
        }


    }

    public KeyframeChange createChange() {
        return new TestChangeKeyFrame(this.int1);
    }

    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {
        float time1 = t1 - t0;
        float time2 = t2 - t0;
        float time3 = t3 - t0;
        //int timeOfDay = (ImString) CatmullRom.value((ImString)this.int1, (ImString)((TestKeyFrame)p1).int1, (ImString)((TestKeyFrame)p2).int1, (ImString)((TestKeyFrame)p3).int1, time1, time2, time3, amount);

        ImString timeOfDay = (ImString) this.int1;
        return new TestChangeKeyFrame((ImString) this.int1);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Integer, Keyframe> map, float v) {
        return null;
    }


    public static class TypeAdapter implements JsonSerializer<TestKeyFrame>, JsonDeserializer<TestKeyFrame> {
        public TypeAdapter() {
        }

        public TestKeyFrame deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String text = jsonObject.get("int1").getAsString();
            InterpolationType interpolationType = (InterpolationType)context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new TestKeyFrame(new ImString(text), interpolationType);
        }

        public JsonElement serialize(TestKeyFrame src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("int1", String.valueOf(src.int1));
            jsonObject.addProperty("type", "int1");
            jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));
            return jsonObject;
        }
    }
}
