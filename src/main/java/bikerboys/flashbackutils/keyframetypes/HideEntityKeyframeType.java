package bikerboys.flashbackutils.keyframetypes;


import bikerboys.flashbackutils.keyframes.HideEntityKeyframe;
import bikerboys.flashbackutils.recordstuff.HideEntityKeyframeChange;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.jetbrains.annotations.Nullable;

public class HideEntityKeyframeType implements KeyframeType<HideEntityKeyframe> {

    public static HideEntityKeyframeType INSTANCE = new HideEntityKeyframeType();

    @Override
    public String name() {
        return "Hide Entity";
    }

    @Override
    public String id() {
        return "HIDE_ENTITY";
    }

    @Override
    public @Nullable HideEntityKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeCreatePopup<HideEntityKeyframe> createPopup() {
        ImString targetuuid = new ImString(128);
        ImBoolean hidden = new ImBoolean();


        return () -> {
            ImGui.inputText("Target Entity UUID", targetuuid);
            ImGui.checkbox("Hidden", hidden);
            if (ImGui.button("Add")) {
                return new HideEntityKeyframe(targetuuid, hidden.get());
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
        return HideEntityKeyframeChange.class;
    }

    @Override
    public boolean supportsHandler(KeyframeHandler handler) {
        return true;
    }
}
