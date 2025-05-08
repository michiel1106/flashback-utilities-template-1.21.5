package bikerboys.flashbackutils.keyframetypes;

import bikerboys.flashbackutils.keyframes.TestKeyFrame;
import bikerboys.flashbackutils.recordstuff.TestChangeKeyFrame;
import com.moulberry.flashback.editor.ui.ImGuiHelper;
import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.impl.TimeOfDayKeyframe;
import com.moulberry.flashback.keyframe.types.TimeOfDayKeyframeType;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.ImGui;
import imgui.type.ImString;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

public class TestKeyFrameType implements KeyframeType<TestKeyFrame> {

    public static TestKeyFrameType INSTANCE = new TestKeyFrameType();

    @Override
    public String name() {
        return "Test Keyframe";
    }

    @Override
    public String id() {
        return "TEST_KEYFRAME";
    }

    @Override
    public @Nullable TestKeyFrame createDirect() {
        return null;
    }

    @Override
    public KeyframeCreatePopup<TestKeyFrame> createPopup() {
        ImString timeOfDayKeyframeInput = ImGuiHelper.createResizableImString("1");
        EditorState editorState = EditorStateManager.getCurrent();


        return () -> {
            ImGui.inputText("whatever", timeOfDayKeyframeInput);
            if (ImGui.button("Add")) {
                return new TestKeyFrame(timeOfDayKeyframeInput);
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
}
