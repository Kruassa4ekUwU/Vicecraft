package com.vicecraft.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class AdrenalineHandler {
    private static final Map<UUID, Integer> TICKS_SINCE_COMBAT = new HashMap<>();

    private static final int BOREDOM_THRESHOLD = 12000; // 10 минут без боя
    private static final int RUSH_DURATION = 200;        // 10 секунд прилива

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Post event) {
        // Игрок получил урон
        if (event.getEntity() instanceof ServerPlayer player) {
            triggerRush(player);
        }
        // Игрок нанёс урон мобу
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            triggerRush(player);
        }
    }

    private static void triggerRush(ServerPlayer player) {
        UUID id = player.getUUID();
        TICKS_SINCE_COMBAT.put(id, 0);
        player.removeEffect(MobEffects.DIG_SLOWDOWN);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, RUSH_DURATION, 1, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, RUSH_DURATION, 0, false, true, true));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();
        int ticks = TICKS_SINCE_COMBAT.getOrDefault(id, 0) + 1;
        TICKS_SINCE_COMBAT.put(id, ticks);

        if (ticks >= BOREDOM_THRESHOLD) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 0, false, true, true));
        }
    }
}
