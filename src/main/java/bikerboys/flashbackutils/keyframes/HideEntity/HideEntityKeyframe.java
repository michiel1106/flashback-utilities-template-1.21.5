package bikerboys.flashbackutils.keyframes.HideEntity;


import bikerboys.flashbackutils.keyframetypes.HideEntity.HideEntityKeyframeType;
import bikerboys.flashbackutils.recordstuff.HideEntity.HideEntityKeyframeChange;
import com.google.gson.*;
import com.moulberry.flashback.keyframe.Keyframe;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.interpolation.InterpolationType;
import imgui.ImGui;
import imgui.type.ImString;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

public class HideEntityKeyframe extends Keyframe {
    public ImString uuid;
    public boolean hidden;

    public HideEntityKeyframe(ImString uuid, Boolean hidden) {
        this(uuid, hidden, InterpolationType.getDefault());
    }

    public HideEntityKeyframe(ImString uuid, boolean hidden, InterpolationType interpolationType) {
        this.uuid = uuid;
        this.hidden = hidden;
        this.interpolationType(interpolationType);
    }

    public KeyframeType<?> keyframeType() {
        return HideEntityKeyframeType.INSTANCE;
    }

    public HideEntityKeyframe copy() {
        return new HideEntityKeyframe(this.uuid, this.hidden, this.interpolationType());
    }






    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {
        ImGui.setNextItemWidth(160.0F);
        if (ImGui.checkbox("Hidden", this.hidden)) {
            boolean newHidden = !this.hidden;
            update.accept((keyframe) -> {
                ((HideEntityKeyframe)keyframe).hidden = newHidden;
            });
        }
        ImGui.setNextItemWidth(160.0F);
        if (ImGui.inputText("Target UUID", this.uuid)) {

            String newVal = this.uuid.get();
            if (!this.uuid.get().equals(newVal)) {
                update.accept((keyframe) -> {
                    ImString imString = new ImString();
                    imString.set(newVal);
                    imString.inputData.resizeFactor = 100;
                    imString.inputData.isResizable = true;


                    ((HideEntityKeyframe) keyframe).uuid = imString;
                });
            }
        }
    }






    public KeyframeChange createChange() {

        return new HideEntityKeyframeChange(this.uuid.get(), this.hidden);
    }

    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe p2, Keyframe p3, float t0, float t1, float t2, float t3, float amount) {

        HideEntityKeyframe s1 = ((HideEntityKeyframe)p1);

        return new HideEntityKeyframeChange(s1.uuid.get(), s1.hidden);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Integer, Keyframe> map, float v) {


        return new HideEntityKeyframeChange(this.uuid.get(), this.hidden);
    }


    public static class TypeAdapter implements JsonSerializer<HideEntityKeyframe>, JsonDeserializer<HideEntityKeyframe> {
        public TypeAdapter() {
        }


        public HideEntityKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            // Defensive check for required field "int1"
            if (!jsonObject.has("uuid")) {
                throw new JsonParseException("Required field 'uuid' is missing in JSON: " + jsonObject);
            }

            String text = jsonObject.get("uuid").getAsString();
            boolean hidden1 = jsonObject.get("hidden").getAsBoolean();

            // Defensive check for interpolation_type
            if (!jsonObject.has("interpolation_type")) {
                throw new JsonParseException("Required field 'interpolation_type' is missing in JSON: " + jsonObject);
            }

            InterpolationType interpolationType = context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new HideEntityKeyframe(new ImString(text), hidden1, interpolationType);
        }


        public JsonElement serialize(HideEntityKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("uuid", src.uuid.get()); // Ensure this value is never null
            jsonObject.addProperty("hidden", src.hidden); // Ensure this value is never null
            jsonObject.addProperty("type", "hideentity");
            jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));

            return jsonObject;
        }
    }
}
