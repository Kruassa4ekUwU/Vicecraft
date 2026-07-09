package com.vicecraft.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class ArtManiaHandler {
    private static final Map<UUID, Integer> TICKS_WITHOUT_ART = new HashMap<>();
    private static final Map<UUID, Boolean> LAST_ART_STATE = new HashMap<>();

    private static final int ART_STARVATION_THRESHOLD = 96000; // 4 игровых дня
    private static final int RADIUS = 5;
    private static final int CHECK_INTERVAL = 200; // проверяем раз в 10 секунд

    private static boolean hasArtNearby(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos center = player.blockPosition();

        AABB box = new AABB(
                center.getX() - RADIUS, center.getY() - RADIUS, center.getZ() - RADIUS,
                center.getX() + RADIUS, center.getY() + RADIUS, center.getZ() + RADIUS
        );
        boolean hasPainting = !level.getEntitiesOfClass(Painting.class, box).isEmpty();
        if (hasPainting) return true;

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-RADIUS, -RADIUS, -RADIUS),
                center.offset(RADIUS, RADIUS, RADIUS))) {
            var state = level.getBlockState(pos);
            if (state.getBlock() instanceof JukeboxBlock && state.hasProperty(JukeboxBlock.HAS_RECORD) && state.getValue(JukeboxBlock.HAS_RECORD)) {
                return true;
            }
            if (state.getBlock() instanceof NoteBlock) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();

        int ticks = TICKS_WITHOUT_ART.getOrDefault(id, 0) + 1;

        if (ticks % CHECK_INTERVAL == 0 || !LAST_ART_STATE.containsKey(id)) {
            boolean nearArt = hasArtNearby(player);
            LAST_ART_STATE.put(id, nearArt);
            if (nearArt) {
                ticks = 0;
                player.removeEffect(MobEffects.CONFUSION);
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
                player.removeEffect(MobEffects.WEAKNESS);
            }
        }

        TICKS_WITHOUT_ART.put(id, ticks);

        if (ticks >= ART_STARVATION_THRESHOLD) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 0, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, true, true));
        }
    }
}
