package bikerboys.flashbackutils.recordstuff.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import imgui.type.ImString;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;

import static bikerboys.flashbackutils.FlashbackUtilities.executedKeyframes;

public record ExecuteCommandChange(ImString command, boolean applied) implements KeyframeChange {
    public ExecuteCommandChange(ImString command, boolean applied) {
        this.command = command;
        this.applied = applied;
    }



    @Override
    public void apply(KeyframeHandler keyframeHandler) {
      
        MinecraftClient instance = MinecraftClient.getInstance();
        ClientPlayerEntity player = instance.player;


        Flashback.getReplayServer().getReplayTick();



        if (player != null && applied) {


            instance.getNetworkHandler().sendPacket(new CommandExecutionC2SPacket(command.get()));

        }
    }

    @Override
    public KeyframeChange interpolate(KeyframeChange to, double v) {

        return this;
    }
}
