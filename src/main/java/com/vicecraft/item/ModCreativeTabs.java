package com.vicecraft.item;

import com.vicecraft.VicecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VicecraftMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VICECRAFT_TAB = CREATIVE_MODE_TABS.register("vicecraft_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vicecraft"))
                    .icon(() -> new ItemStack(ModItems.CIGARETTE.get()))
                    .displayItems((parameters, output) -> {
                        // Табак
                        output.accept(ModItems.TOBACCO_LEAF.get());
                        output.accept(ModItems.DRIED_TOBACCO.get());
                        output.accept(ModItems.CIGARETTE.get());
                        output.accept(ModItems.CIGAR.get());
                        output.accept(ModItems.VAPE.get());
                        output.accept(ModItems.HOOKAH.get());
                        output.accept(ModItems.SNUS.get());
                        output.accept(ModItems.NICOTINE_PATCH.get());
                        // Алкоголь
                        output.accept(ModItems.HOPS.get());
                        output.accept(ModItems.BEER.get());
                        output.accept(ModItems.WINE.get());
                        output.accept(ModItems.KVASS.get());
                        output.accept(ModItems.MEDOVUKHA.get());
                        output.accept(ModItems.CHEKUSHKA.get());
                        output.accept(ModItems.MOONSHINE.get());
                        output.accept(ModItems.PURE_SPIRIT.get());
                        // Кофе
                        output.accept(ModItems.COFFEE_BEANS.get());
                        output.accept(ModItems.ROASTED_COFFEE.get());
                        output.accept(ModItems.COFFEE_CUP.get());
                        output.accept(ModItems.ENERGY_DRINK.get());
                        // Чай
                        output.accept(ModItems.TEA_LEAF.get());
                        output.accept(ModItems.TEA.get());
                        output.accept(ModItems.MATE.get());
                        output.accept(ModItems.KOMBUCHA.get());
                        output.accept(ModItems.CHIFIR.get());
                        // Конопля
                        output.accept(ModItems.HEMP_FIBER.get());
                        output.accept(ModItems.DRIED_HEMP_FIBER.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
