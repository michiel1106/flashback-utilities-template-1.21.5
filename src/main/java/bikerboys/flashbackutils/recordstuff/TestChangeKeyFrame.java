package bikerboys.flashbackutils.recordstuff;

import com.moulberry.flashback.Interpolation;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeTimeOfDay;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import imgui.type.ImString;

public record TestChangeKeyFrame(ImString timeOfDay) implements KeyframeChange {

    public TestChangeKeyFrame(ImString timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void apply(KeyframeHandler keyframeHandler) {
       // keyframeHandler.applyTimeOfDay(this.timeOfDay);

        System.out.println(timeOfDay);
    }

    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        TestChangeKeyFrame other = (TestChangeKeyFrame) to;
        return new TestChangeKeyFrame(timeOfDay);
    }

    public ImString timeOfDay() {
        return this.timeOfDay;
    }
}
