package com.vicecraft.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import com.vicecraft.VicecraftMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = VicecraftMod.MOD_ID)
public class GoldRushHandler {
    private static final Map<UUID, Integer> TICKS_WITHOUT_SHINY = new HashMap<>();

    private static final int SHINY_THRESHOLD = 16;      // сколько "блестяшек" нужно для баффа
    private static final int PARANOIA_THRESHOLD = 6000; // 5 минут без блестяшек

    private static int countShiny(ServerPlayer player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (isShiny(stack)) count += stack.getCount();
        }
        return count;
    }

    private static boolean isShiny(ItemStack stack) {
        if (stack.is(Items.ANDESITE)) return true;
        if (stack.is(Items.IRON_INGOT) || stack.is(Items.IRON_BLOCK) || stack.is(Items.RAW_IRON)) return true;
        if (stack.is(Items.GOLD_INGOT) || stack.is(Items.GOLD_BLOCK) || stack.is(Items.GOLD_NUGGET) || stack.is(Items.RAW_GOLD)) return true;
        if (stack.is(Items.DIAMOND) || stack.is(Items.DIAMOND_BLOCK)) return true;
        // Латунь из Create — проверяем по имени регистрации, чтобы не тянуть жёсткую зависимость в код
        String id = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        return id.equals("create:brass_ingot") || id.equals("create:brass_block") || id.equals("create:brass_nugget");
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        UUID id = player.getUUID();

        int shinyCount = countShiny(player);

        if (shinyCount >= SHINY_THRESHOLD) {
            TICKS_WITHOUT_SHINY.put(id, 0);
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 60, 0, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, 0, false, true, true));
            return;
        }

        int ticks = TICKS_WITHOUT_SHINY.getOrDefault(id, 0) + 1;
        TICKS_WITHOUT_SHINY.put(id, ticks);

        if (ticks >= PARANOIA_THRESHOLD && ticks % 200 == 0) {
            // Кратковременная "паранойя" — мерцающая слепота
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, true, true));
        }
    }
}
