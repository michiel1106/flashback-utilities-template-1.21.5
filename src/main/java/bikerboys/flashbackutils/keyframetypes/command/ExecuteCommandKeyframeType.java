package bikerboys.flashbackutils.keyframetypes.command;

import bikerboys.flashbackutils.keyframes.HideEntity.HideEntityKeyframe;
import bikerboys.flashbackutils.keyframes.command.ExecuteCommandKeyframe;
import bikerboys.flashbackutils.recordstuff.command.ExecuteCommandChange;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.jetbrains.annotations.Nullable;

public class ExecuteCommandKeyframeType implements KeyframeType<ExecuteCommandKeyframe> {

    public static ExecuteCommandKeyframeType INSTANCE = new ExecuteCommandKeyframeType();

    @Override
    public String name() {
        return "Execute Command (WIP)";
    }

    @Override
    public String id() {
        return "EXECUTE_COMMAND";
    }

    @Override
    public @Nullable ExecuteCommandKeyframe createDirect() {
        return null;
    }

    @Override
    public @Nullable KeyframeCreatePopup createPopup() {

        ImString command = new ImString();
        command.inputData.isResizable = true;
        command.inputData.resizeFactor = 400;

        ImBoolean applied = new ImBoolean();


        return () -> {
            ImGui.inputText("Command to execute", command);
            ImGui.checkbox("Applied", applied);

            if (ImGui.button("Add")) {
                return new ExecuteCommandKeyframe(command, applied.get());
            }
            ImGui.sameLine();
            if (ImGui.button("Cancel")) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return ExecuteCommandChange.class;
    }

    @Override
    public boolean supportsHandler(KeyframeHandler handler) {
        return true;
    }
}
