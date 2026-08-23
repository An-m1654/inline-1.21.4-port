package com.samsthenerd.inline.mixin.core;

import com.google.common.util.concurrent.AtomicDouble;
import com.samsthenerd.inline.impl.InlineRenderCore;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Style;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( targets = "net.minecraft.client.font.TextRenderer$Drawer")
public class MixinInlineRendering {
    @Shadow
	float x;
	@Shadow
	float y;
    @Shadow
    private Matrix4f matrix;

    @Shadow
    @Final
    private int light;

    @Shadow
    @Final
    private boolean shadow;
//    @Shadow
//    @Final
//    private float brightnessMultiplier;
    @Shadow
    @Final
    private int color;

    @Shadow
    @Final
    private TextLayerType layerType;

    @Shadow
    @Final
    VertexConsumerProvider vertexConsumers;


    @Inject(method = "accept(ILnet/minecraft/text/Style;I)Z", at = @At("HEAD"), cancellable = true)
	private void InlineRenderDrawerAccept(int index, Style style, int codepoint, CallbackInfoReturnable<Boolean> cir) {
        AtomicDouble xUpdater = new AtomicDouble(x);

        float brightnessMultiplier = 1f; //? This might need to be "shadow ? 0.25f : 1.0f" instead of static 1f, but idk
        InlineRenderCore.RenderArgs args = new InlineRenderCore.RenderArgs(x, y, matrix, light, shadow, brightnessMultiplier,
                ColorHelper.getRedFloat(color), ColorHelper.getGreenFloat(color), ColorHelper.getBlueFloat(color), ColorHelper.getAlphaFloat(color), layerType, vertexConsumers, xUpdater);
        if(InlineRenderCore.textDrawerAcceptHandler(index, style, codepoint, args)){
            this.x = xUpdater.floatValue();
            cir.setReturnValue(true);
        }
    }
}

