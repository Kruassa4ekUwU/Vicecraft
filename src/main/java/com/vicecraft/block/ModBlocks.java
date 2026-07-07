package com.vicecraft.block;

import com.vicecraft.VicecraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(VicecraftMod.MOD_ID);

    public static final DeferredBlock<Block> TOBACCO_CROP = registerCrop("tobacco_crop");
    public static final DeferredBlock<Block> HOPS_CROP = registerCrop("hops_crop");
    public static final DeferredBlock<Block> COFFEE_CROP = registerCrop("coffee_crop");
    public static final DeferredBlock<Block> TEA_CROP = registerCrop("tea_crop");

    private static DeferredBlock<Block> registerCrop(String name) {
        return BLOCKS.register(name, () -> new CropBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .noCollission()
                        .instabreak()
                        .sound(net.minecraft.world.level.block.SoundType.CROP)
                        .pushReaction(net.minecraft.world.level.material.PushReaction.DESTROY)
        ) {});
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
