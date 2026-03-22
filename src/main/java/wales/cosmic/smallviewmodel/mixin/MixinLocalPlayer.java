package wales.cosmic.smallviewmodel.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wales.cosmic.smallviewmodel.config.SmallViewModelConfig;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer extends AbstractClientPlayer {

    @Unique
    private int smallviewmodel$animTicks;

    public MixinLocalPlayer(ClientLevel $0, GameProfile $1) {
        super($0, $1);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void smallviewmodel$animTicks$aiStep(CallbackInfo callbackInfo) {

        final var config = SmallViewModelConfig.get();

        if (this.smallviewmodel$animTicks > config.swing.swingDuration) {
            this.smallviewmodel$animTicks = 0;
        }
        if (this.smallviewmodel$animTicks == 0) {
            this.attackAnim = 1f;
        } else {
            this.attackAnim = (this.smallviewmodel$animTicks - 1f) / config.swing.swingDuration;
            this.smallviewmodel$animTicks++;
        }

    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;)V", at = @At("HEAD"))
    public void smallviewmodel$swing(
            InteractionHand hand,
            CallbackInfo callbackInfo
    ) {
        final var config = SmallViewModelConfig.get();
        if (!config.swing.enabled) {
            return;
        }
        if (this.smallviewmodel$animTicks == 0) {
            this.smallviewmodel$animTicks = 1;
        }
    }

}
