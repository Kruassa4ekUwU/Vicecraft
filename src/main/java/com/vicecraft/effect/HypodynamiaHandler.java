package com.vicecraft.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class HypodynamiaHandler {
    private static final Map<UUID, Vec3> LAST_POS = new HashMap<>();
    private static final Map<UUID, Integer> STILL_TICKS = new HashMap<>();
    private static final Map<UUID, Integer> RECOVERY_TICKS = new HashMap<>();
    private static final Map<UUID, Integer> CURRENT_AMPLIFIER = new HashMap<>();

    private static final int STILL_THRESHOLD = 6000;   // 5 минут без движения
    private static final int STACK_INTERVAL = 1200;    // +1 уровень за каждую доп. минуту стояния
    private static final int RECOVERY_TIME = 1200;     // 1 минута движения, чтобы полностью отойти
    private static final int MAX_AMPLIFIER = 3;
    private static final double MOVE_EPSILON = 0.05;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();
        Vec3 current = player.position();
        Vec3 last = LAST_POS.get(id);

        boolean moved = last == null || current.distanceTo(last) > MOVE_EPSILON;
        LAST_POS.put(id, current);

        int amplifier = CURRENT_AMPLIFIER.getOrDefault(id, 0);

        if (moved) {
            STILL_TICKS.put(id, 0);

            if (amplifier > 0) {
                int recovery = RECOVERY_TICKS.getOrDefault(id, RECOVERY_TIME) - 1;
                RECOVERY_TICKS.put(id, recovery);

                if (recovery <= 0) {
                    CURRENT_AMPLIFIER.put(id, 0);
                    RECOVERY_TICKS.remove(id);
                    player.removeEffect(MobEffects.SLOWNESS);
                    player.removeEffect(MobEffects.WEAKNESS);
                } else {
                    // Держим текущий уровень дебаффа, пока не закончится минута движения
                    player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, amplifier, false, true, true));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, Math.min(amplifier, 1), false, true, true));
                }
            }
            return;
        }

        // Стоит на месте — копим счётчик и сбрасываем восстановление
        RECOVERY_TICKS.remove(id);
        int ticks = STILL_TICKS.getOrDefault(id, 0) + 1;
        STILL_TICKS.put(id, ticks);

        if (ticks >= STILL_THRESHOLD) {
            int extraMinutes = (ticks - STILL_THRESHOLD) / STACK_INTERVAL;
            int newAmplifier = Math.min(extraMinutes, MAX_AMPLIFIER);
            CURRENT_AMPLIFIER.put(id, newAmplifier);
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, newAmplifier, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, Math.min(newAmplifier, 1), false, true, true));
        }
    }
}
