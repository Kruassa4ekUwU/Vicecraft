package com.vicecraft;

import com.vicecraft.item.ModItems;
import com.vicecraft.item.ModCreativeTabs;
import com.vicecraft.block.ModBlocks;
import com.vicecraft.effect.ModEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(VicecraftMod.MOD_ID)
public class VicecraftMod {
    public static final String MOD_ID = "vicecraft";

    public VicecraftMod(IEventBus modEventBus, ModContainer modContainer) {
        ModEffects.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) {
        // место для общей инициализации (например регистрация compat-хуков Create/Farmer's Delight)
    }
}
