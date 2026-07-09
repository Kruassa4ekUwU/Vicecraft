package com.vicecraft.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class WorkaholismHandler {
    private static final Map<UUID, Integer> TICKS_SINCE_WORK = new HashMap<>();
    private static final Map<UUID, Integer> WORK_STREAK = new HashMap<>();

    private static final int REST_THRESHOLD = 600;       // 30 сек без работы = отдых засчитан
    private static final int STREAK_UNIT = 1200;         // каждая минута непрерывной работы = +1 уровень
    private static final int MAX_AMPLIFIER = 3;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            UUID id = player.getUUID();
            int ticksSince = TICKS_SINCE_WORK.getOrDefault(id, Integer.MAX_VALUE);

            if (ticksSince < REST_THRESHOLD) {
                int streak = WORK_STREAK.getOrDefault(id, 0) + ticksSince;
                WORK_STREAK.put(id, streak);
            } else {
                WORK_STREAK.put(id, 0);
            }
            TICKS_SINCE_WORK.put(id, 0);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();

        int ticksSince = TICKS_SINCE_WORK.getOrDefault(id, Integer.MAX_VALUE);
        if (ticksSince != Integer.MAX_VALUE) {
            TICKS_SINCE_WORK.put(id, ticksSince + 1);
        }

        if (ticksSince > REST_THRESHOLD) {
            WORK_STREAK.put(id, 0);
            player.removeEffect(MobEffects.DIG_SPEED);
            player.removeEffect(MobEffects.WEAKNESS);
            return;
        }

        int streak = WORK_STREAK.getOrDefault(id, 0);
        int amplifier = Math.min(streak / STREAK_UNIT, MAX_AMPLIFIER);

        if (amplifier > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, amplifier, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, Math.min(amplifier - 1, 1), false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60, 0, false, true, true));
        }
    }
}
