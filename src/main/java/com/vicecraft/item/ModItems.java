package com.vicecraft.item;

import com.vicecraft.VicecraftMod;
import com.vicecraft.effect.ModEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.bus.api.IEventBus;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(VicecraftMod.MOD_ID);

    // ---------- Табак ----------
    public static final DeferredItem<Item> TOBACCO_LEAF = ITEMS.register("tobacco_leaf",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRIED_TOBACCO = ITEMS.register("dried_tobacco",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.HUNGER, 400, 0), 0.3f)
                            .build()
            )));

    public static final DeferredItem<Item> CIGAR = ITEMS.register("cigar",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 2400, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.HUNGER, 600, 0), 0.5f)
                            .build()
            )));

    // Кальян/вейп — тот же никотин, без дебаффа
    public static final DeferredItem<Item> VAPE = ITEMS.register("vape",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 1000, 0), 1.0f)
                            .build()
            )));

    public static final DeferredItem<Item> HOOKAH = ITEMS.register("hookah",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 1800, 0), 1.0f)
                            .build()
            )));

    // Снюс — компактный, без дыма, короче по времени, без дебаффа
    public static final DeferredItem<Item> SNUS = ITEMS.register("snus",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 600, 0), 1.0f)
                            .build()
            )));

    // Никотиновый пластырь — терапевтическое средство, лёгкий долгий эффект без дебаффов
    public static final DeferredItem<Item> NICOTINE_PATCH = ITEMS.register("nicotine_patch",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.NICOTINE_RUSH.get(), 6000, 0), 1.0f)
                            .build()
            )));

    // ---------- Алкоголь ----------
    public static final DeferredItem<Item> HOPS = ITEMS.register("hops",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BEER = ITEMS.register("beer",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.2f)
                            .effect(() -> new MobEffectInstance(ModEffects.TIPSY.get(), 1200, 0), 1.0f)
                            .build()
            )));

    public static final DeferredItem<Item> WINE = ITEMS.register("wine",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.2f)
                            .effect(() -> new MobEffectInstance(ModEffects.TIPSY.get(), 1600, 1), 1.0f)
                            .build()
            )));

    // Квас — почти безалкогольный, лёгкая сатурация, эффекта опьянения нет
    public static final DeferredItem<Item> KVASS = ITEMS.register("kvass",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(2).saturationModifier(0.4f)
                            .build()
            )));

    // Медовуха — по тиру примерно как вино
    public static final DeferredItem<Item> MEDOVUKHA = ITEMS.register("medovukha",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.3f)
                            .effect(() -> new MobEffectInstance(ModEffects.TIPSY.get(), 1600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 100, 0), 0.3f)
                            .build()
            )));

    // Чекушка — 0.25л водки
    public static final DeferredItem<Item> CHEKUSHKA = ITEMS.register("chekushka",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.DRUNK.get(), 1800, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.NAUSEA, 600, 0), 0.6f)
                            .build()
            )));

    public static final DeferredItem<Item> MOONSHINE = ITEMS.register("moonshine",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.DRUNK.get(), 2400, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.NAUSEA, 800, 1), 0.7f)
                            .build()
            )));

    // Чистый спирт — 96%, пить неразбавленным опасно
    public static final DeferredItem<Item> PURE_SPIRIT = ITEMS.register("pure_spirit",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.DRUNK.get(), 3600, 3), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.NAUSEA, 1200, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 200, 0), 0.5f)
                            .build()
            )));

    // ---------- Кофе / энергетик ----------
    public static final DeferredItem<Item> COFFEE_BEANS = ITEMS.register("coffee_beans",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ROASTED_COFFEE = ITEMS.register("roasted_coffee",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COFFEE_CUP = ITEMS.register("coffee_cup",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.3f)
                            .effect(() -> new MobEffectInstance(ModEffects.CAFFEINE_BOOST.get(), 1200, 0), 1.0f)
                            .build()
            )));

    public static final DeferredItem<Item> ENERGY_DRINK = ITEMS.register("energy_drink",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.1f)
                            .effect(() -> new MobEffectInstance(ModEffects.CAFFEINE_BOOST.get(), 1800, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.HUNGER, 400, 0), 0.4f)
                            .build()
            )));

    // ---------- Чай / чифирь ----------
    public static final DeferredItem<Item> TEA_LEAF = ITEMS.register("tea_leaf",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> TEA = ITEMS.register("tea",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.4f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 100, 0), 1.0f)
                            .build()
            )));

    // Матэ — кофеиновый баф послабее чифиря, без дебаффов
    public static final DeferredItem<Item> MATE = ITEMS.register("mate",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(1).saturationModifier(0.3f)
                            .effect(() -> new MobEffectInstance(ModEffects.CAFFEINE_BOOST.get(), 900, 0), 1.0f)
                            .build()
            )));

    // Комбуча — ферментированный чай, лёгкий бафф почти без минусов
    public static final DeferredItem<Item> KOMBUCHA = ITEMS.register("kombucha",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(2).saturationModifier(0.5f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, 140, 0), 1.0f)
                            .build()
            )));

    // Чифирь — многократно уваренный чай, легальный, но очень крепкий
    public static final DeferredItem<Item> CHIFIR = ITEMS.register("chifir",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(0).saturationModifier(0f)
                            .effect(() -> new MobEffectInstance(ModEffects.OVERCAFFEINATED.get(), 1200, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.NAUSEA, 600, 1), 0.6f)
                            .effect(() -> new MobEffectInstance(net.minecraft.world.effect.MobEffects.HUNGER, 600, 0), 0.5f)
                            .build()
            )));
public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // ---------- Конопля (техническое сырьё, без эффектов) ----------
    public static final DeferredItem<Item> HEMP_FIBER = ITEMS.register("hemp_fiber",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRIED_HEMP_FIBER = ITEMS.register("dried_hemp_fiber",
            () -> new Item(new Item.Properties()));
}
 
