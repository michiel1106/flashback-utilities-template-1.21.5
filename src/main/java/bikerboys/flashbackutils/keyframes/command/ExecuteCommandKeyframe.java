package bikerboys.flashbackutils.keyframes.command;

import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframetypes.command.ExecuteCommandKeyframeType;
import bikerboys.flashbackutils.recordstuff.HideEntity.HideEntityKeyframeChange;
import bikerboys.flashbackutils.recordstuff.command.ExecuteCommandChange;
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

public class ExecuteCommandKeyframe extends Keyframe {

    private ImString command;
    private boolean applied;

    public ExecuteCommandKeyframe(ImString command, Boolean applied) {
        this(command, applied, InterpolationType.getDefault());
    }

    public ExecuteCommandKeyframe(ImString command, boolean applied, InterpolationType interpolationType) {
        this.command = command;
        this.applied = applied;
        this.interpolationType(interpolationType);
    }

    @Override
    public KeyframeType<?> keyframeType() {
        return ExecuteCommandKeyframeType.INSTANCE;
    }

    @Override
    public void renderEditKeyframe(Consumer<Consumer<Keyframe>> update) {

        ImGui.setNextItemWidth(160.0F);
        if (ImGui.checkbox("Applied", this.applied)) {
            boolean newHidden = !this.applied;
            update.accept((keyframe) -> {
                ((ExecuteCommandKeyframe)keyframe).applied = newHidden;
            });
        }
        ImGui.setNextItemWidth(160.0F);
        if (ImGui.inputText("Command to execute", this.command)) {

            String newVal = this.command.get();
            if (!this.command.get().equals(newVal)) {
                update.accept((keyframe) -> {
                    ImString imString = new ImString();
                    imString.inputData.isResizable = true;
                    imString.inputData.resizeFactor = 400;





                    ((ExecuteCommandKeyframe)keyframe).command = imString;
                });
            }
        }
        super.renderEditKeyframe(update);
    }



    @Override
    public Keyframe copy() {
        return new ExecuteCommandKeyframe(this.command, this.applied, this.interpolationType());
    }

    @Override
    public KeyframeChange createChange() {
        return new ExecuteCommandChange(this.command, this.applied);
    }

    @Override
    public KeyframeChange createSmoothInterpolatedChange(Keyframe p1, Keyframe keyframe1, Keyframe keyframe2, float v, float v1, float v2, float v3, float v4) {
        ExecuteCommandKeyframe s1 = ((ExecuteCommandKeyframe)p1);

        return new ExecuteCommandChange(s1.command, s1.applied);
    }

    @Override
    public KeyframeChange createHermiteInterpolatedChange(Map<Integer, Keyframe> map, float v) {
        return createChange();
    }



    public static class TypeAdapter implements JsonSerializer<ExecuteCommandKeyframe>, JsonDeserializer<ExecuteCommandKeyframe> {
        public TypeAdapter() {
        }


        public ExecuteCommandKeyframe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            // Defensive check for required field "int1"
            if (!jsonObject.has("command")) {
                throw new JsonParseException("Required field 'uuid' is missing in JSON: " + jsonObject);
            }

            String text = jsonObject.get("command").getAsString();
            boolean applied1 = jsonObject.get("applied").getAsBoolean();

            // Defensive check for interpolation_type
            if (!jsonObject.has("interpolation_type")) {
                throw new JsonParseException("Required field 'interpolation_type' is missing in JSON: " + jsonObject);
            }

            InterpolationType interpolationType = context.deserialize(jsonObject.get("interpolation_type"), InterpolationType.class);
            return new ExecuteCommandKeyframe(new ImString(text), applied1, interpolationType);
        }


        public JsonElement serialize(ExecuteCommandKeyframe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("command", src.command.get()); // Ensure this value is never null
            jsonObject.addProperty("applied", src.applied); // Ensure this value is never null
            jsonObject.addProperty("type", "executecommand");
            jsonObject.add("interpolation_type", context.serialize(src.interpolationType()));

            return jsonObject;
        }
    }

}
