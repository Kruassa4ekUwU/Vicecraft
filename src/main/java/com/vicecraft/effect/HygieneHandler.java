package com.vicecraft.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class HygieneHandler {
    private static final Map<UUID, Integer> TICKS_SINCE_BATH = new HashMap<>();
    private static final Map<UUID, Integer> WATER_TIME_THIS_CYCLE = new HashMap<>();

    private static final int CYCLE_LENGTH = 48000;   // 2 игровых дня
    private static final int REQUIRED_WATER_TICKS = 1200; // 1 минута в воде

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();

        int cycleTicks = TICKS_SINCE_BATH.getOrDefault(id, 0) + 1;
        int waterTicks = WATER_TIME_THIS_CYCLE.getOrDefault(id, 0);

        if (player.isInWater()) {
            waterTicks++;
            WATER_TIME_THIS_CYCLE.put(id, waterTicks);
        }

        if (waterTicks >= REQUIRED_WATER_TICKS) {
            // Помылся вовремя — сброс цикла
            TICKS_SINCE_BATH.put(id, 0);
            WATER_TIME_THIS_CYCLE.put(id, 0);
            player.removeEffect(MobEffects.CONFUSION);
            return;
        }

        if (cycleTicks >= CYCLE_LENGTH) {
            // Не помылся вовремя — тошнота, держится пока не помоется
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, true, true));
        }

        TICKS_SINCE_BATH.put(id, cycleTicks);
    }
}
