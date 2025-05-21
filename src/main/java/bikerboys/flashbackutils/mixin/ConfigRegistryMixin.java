package bikerboys.flashbackutils.mixin;


import bikerboys.flashbackutils.FlashbackUtilities;
import bikerboys.flashbackutils.interfaces.FlashbackConfigAccess;
import com.moulberry.flashback.configuration.FlashbackConfig;
import com.moulberry.flashback.configuration.OptionCaption;
import com.moulberry.flashback.configuration.OptionDescription;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FlashbackConfig.class)
public class ConfigRegistryMixin implements FlashbackConfigAccess {
    
    

    @Unique
    @OptionCaption("flashback-utilities.option.recording_icon")
    @OptionDescription("flashback-utilities.option.recording_icon.description")
    private boolean recordingIcon = false;



    @Override
    public boolean getRecordingIcon() {
        return this.recordingIcon;
    }

    @Override
    public void setRecordingIcon(boolean value) {

        this.setRecordingIcon(value);
    }
}
