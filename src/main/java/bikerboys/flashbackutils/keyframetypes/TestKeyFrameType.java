package bikerboys.flashbackutils.keyframetypes;


import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import bikerboys.flashbackutils.recordstuff.TestChangeKeyFrame;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.jetbrains.annotations.Nullable;

public class TestKeyFrameType implements KeyframeType<TestKeyFrame> {

    public static TestKeyFrameType INSTANCE = new TestKeyFrameType();

    @Override
    public String name() {
        return "Hide Entity";
    }

    @Override
    public String id() {
        return "HIDE_ENTITY";
    }

    @Override
    public @Nullable TestKeyFrame createDirect() {
        return null;
    }

    @Override
    public KeyframeCreatePopup<TestKeyFrame> createPopup() {
        ImString targetuuid = ImGuiHelper.createResizableImString("");
        ImBoolean hidden = new ImBoolean();


        return () -> {
            ImGui.inputText("Target Entity UUID", targetuuid);
            ImGui.checkbox("Hidden", hidden);
            if (ImGui.button("Add")) {
                return new TestKeyFrame(targetuuid, hidden.get());
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
        return TestChangeKeyFrame.class;
    }

    @Override
    public boolean supportsHandler(KeyframeHandler handler) {
        return true;
    }
}
