package wales.cosmic.smallviewmodel.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wales.cosmic.smallviewmodel.config.SmallViewModelConfig;

@Mixin(LivingEntity.class)
public class MixinLocalPlayer {

    @Inject(
            method = "getCurrentSwingDuration",
            at = @At("HEAD"),
            cancellable = true
    )
    private void smallviewmodel$getCurrentSwingDuration(CallbackInfoReturnable<Integer> cir) {
        final var config = SmallViewModelConfig.get();
        if (config.swing.enabled) {
            cir.setReturnValue(config.swing.swingDuration);
        }
    }

}
