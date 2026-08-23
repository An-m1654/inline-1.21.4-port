package com.samsthenerd.inline.mixin.core;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DrawContext.class)
public interface MixinDrawContextAccessor {
    @Accessor("vertexConsumers")
    VertexConsumerProvider.Immediate inline$getVertexConsumers();
}
