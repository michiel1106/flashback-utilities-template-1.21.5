package bikerboys.flashbackutils.recordstuff;

import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;



public record TestChangeKeyFrame(String timeOfDay) implements KeyframeChange {
    public TestChangeKeyFrame(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void apply(KeyframeHandler keyframeHandler) {
    }

    public KeyframeChange interpolate(KeyframeChange to, double amount) {
        TestChangeKeyFrame other = (TestChangeKeyFrame) to;
        return new TestChangeKeyFrame(timeOfDay);
    }

    public String timeOfDay() {
        return this.timeOfDay;
    }
}
