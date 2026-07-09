package com.vicecraft.effect;

import com.vicecraft.VicecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, VicecraftMod.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> NICOTINE_RUSH = EFFECTS.register("nicotine_rush",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xC2A878)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "nicotine_rush_speed"),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    // Опьянение: замедление движения + снижение урона
    public static final DeferredHolder<MobEffect, MobEffect> TIPSY = EFFECTS.register("tipsy",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x8B5A2B)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "tipsy_speed"),
                            -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "tipsy_damage"),
                            -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> DRUNK = EFFECTS.register("drunk",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x5C3A1E)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "drunk_speed"),
                            -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "drunk_damage"),
                            -0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> CAFFEINE_BOOST = EFFECTS.register("caffeine_boost",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x6F4E37)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "caffeine_speed"),
                            0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final DeferredHolder<MobEffect, MobEffect> OVERCAFFEINATED = EFFECTS.register("overcaffeinated",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x3B2412)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(VicecraftMod.MOD_ID, "overcaffeinated_speed"),
                            0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
