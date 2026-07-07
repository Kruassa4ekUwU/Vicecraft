package com.vicecraft.effect;

import com.vicecraft.VicecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, VicecraftMod.MOD_ID);

    // Табак: бодрость короткая, потом лёгкая тошнота
    public static final DeferredHolder<MobEffect, MobEffect> NICOTINE_RUSH = EFFECTS.register("nicotine_rush",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xC2A878) {});

    // Алкоголь: опьянение (шейдер + замедление реакции)
    public static final DeferredHolder<MobEffect, MobEffect> TIPSY = EFFECTS.register("tipsy",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x8B5A2B) {});

    public static final DeferredHolder<MobEffect, MobEffect> DRUNK = EFFECTS.register("drunk",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x5C3A1E) {});

    // Кофеин: бодрость сильная
    public static final DeferredHolder<MobEffect, MobEffect> CAFFEINE_BOOST = EFFECTS.register("caffeine_boost",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x6F4E37) {});

    // Чифирь: пиковая бодрость + бессонница/тошнота после
    public static final DeferredHolder<MobEffect, MobEffect> OVERCAFFEINATED = EFFECTS.register("overcaffeinated",
            () -> new MobEffect(MobEffectCategory.HARMFUL, 0x3B2412) {});

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
