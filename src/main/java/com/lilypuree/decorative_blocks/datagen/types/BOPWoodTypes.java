package com.lilypuree.decorative_blocks.datagen.types;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.core.BiomesOPlenty;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.ModList;


public enum BOPWoodTypes implements IWoodType {
    CHERRY("cherry"), DEAD("dead"), FIR("fir"), HELLBARK("hellbark"){
        @Override
        public boolean isFlammable() {
            return false;
        }
    },
    JACARANDA("jacaranda"), MAGIC("magic"), MAHOGANY("mahogany"), PALM("palm"), REDWOOD("redwood"),
    UMBRAN("umbran"), WILLOW("willow");

    private final String name;

    private BOPWoodTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public String namespace() {
        return "biomesoplenty";
    }

    public String getName() {
        return this.name;
    }



    public Block getLog() {
        return Registry.BLOCK.get(new ResourceLocation(BiomesOPlenty.MOD_ID, name + "_log"));
//        switch (this) {
//            case CHERRY:return BOPBlocks.cherry_log;
//            case DEAD:return BOPBlocks.dead_log;
////            case ETHEREAL:return BOPBlocks.ethereal_log;
//            case FIR:return BOPBlocks.fir_log;
//            case HELLBARK:return BOPBlocks.hellbark_log;
//            case JACARANDA:return BOPBlocks.jacaranda_log;
//            case MAGIC:return BOPBlocks.magic_log;
//            case MAHOGANY:return BOPBlocks.mahogany_log;
//            case PALM:return BOPBlocks.palm_log;
//            case REDWOOD:return BOPBlocks.redwood_log;
//            case UMBRAN:return BOPBlocks.umbran_log;
//            case WILLOW:return BOPBlocks.willow_log;
//        }
//        return BOPBlocks.willow_log;
    }

    public Block getStrippedLog() {
        return Registry.BLOCK.get(new ResourceLocation(BiomesOPlenty.MOD_ID, "stripped_"+name + "_log"));
//
//        switch (this) {
//            case CHERRY:return BOPBlocks.stripped_cherry_log;
//            case DEAD:return BOPBlocks.stripped_dead_log;
////            case ETHEREAL:return BOPBlocks.stripped_ethereal_log;
//            case FIR:return BOPBlocks.stripped_fir_log;
//            case HELLBARK:return BOPBlocks.stripped_hellbark_log;
//            case JACARANDA:return BOPBlocks.stripped_jacaranda_log;
//            case MAGIC:return BOPBlocks.stripped_magic_log;
//            case MAHOGANY:return BOPBlocks.stripped_mahogany_log;
//            case PALM:return BOPBlocks.stripped_palm_log;
//            case REDWOOD:return BOPBlocks.stripped_redwood_log;
//            case UMBRAN:return BOPBlocks.stripped_umbran_log;
//            case WILLOW:return BOPBlocks.stripped_willow_log;
//        }
//        return BOPBlocks.stripped_cherry_log;
    }

    public Block getSlab() {
        return Registry.BLOCK.get(new ResourceLocation(BiomesOPlenty.MOD_ID, name + "_slab"));
//        switch (this) {
//            case CHERRY:return BOPBlocks.cherry_slab;
//            case DEAD:return BOPBlocks.dead_slab;
////            case ETHEREAL:return BOPBlocks.ethereal_slab;
//            case FIR:return BOPBlocks.fir_slab;
//            case HELLBARK:return BOPBlocks.hellbark_slab;
//            case JACARANDA:return BOPBlocks.jacaranda_slab;
//            case MAGIC:return BOPBlocks.magic_slab;
//            case MAHOGANY:return BOPBlocks.mahogany_slab;
//            case PALM:return BOPBlocks.palm_slab;
//            case REDWOOD:return BOPBlocks.redwood_slab;
//            case UMBRAN:return BOPBlocks.umbran_slab;
//            case WILLOW:return BOPBlocks.willow_slab;
//        }
//        return BOPBlocks.willow_slab;
    }
    public Block getFence() {
        return Registry.BLOCK.get(new ResourceLocation(BiomesOPlenty.MOD_ID, name + "_fence"));
//
//        switch (this) {
//            case CHERRY:return BOPBlocks.cherry_fence;
//            case DEAD:return BOPBlocks.dead_fence;
////            case ETHEREAL:return BOPBlocks.ethereal_fence;
//            case FIR:return BOPBlocks.fir_fence;
//            case HELLBARK:return BOPBlocks.hellbark_fence;
//            case JACARANDA:return BOPBlocks.jacaranda_fence;
//            case MAGIC:return BOPBlocks.magic_fence;
//            case MAHOGANY:return BOPBlocks.mahogany_fence;
//            case PALM:return BOPBlocks.palm_fence;
//            case REDWOOD:return BOPBlocks.redwood_fence;
//            case UMBRAN:return BOPBlocks.umbran_fence;
//            case WILLOW:return BOPBlocks.willow_fence;
//        }
//        return BOPBlocks.willow_fence;
    }
    public Block getPlanks() {
        return Registry.BLOCK.get(new ResourceLocation(BiomesOPlenty.MOD_ID, name + "_planks"));
//
//        switch (this) {
//            case CHERRY:return BOPBlocks.cherry_planks;
//            case DEAD:return BOPBlocks.dead_planks;
////            case ETHEREAL:return BOPBlocks.ethereal_planks;
//            case FIR:return BOPBlocks.fir_planks;
//            case HELLBARK:return BOPBlocks.hellbark_planks;
//            case JACARANDA:return BOPBlocks.jacaranda_planks;
//            case MAGIC:return BOPBlocks.magic_planks;
//            case MAHOGANY:return BOPBlocks.mahogany_planks;
//            case PALM:return BOPBlocks.palm_planks;
//            case REDWOOD:return BOPBlocks.redwood_planks;
//            case UMBRAN:return BOPBlocks.umbran_planks;
//            case WILLOW:return BOPBlocks.willow_planks;
//        }
//        return BOPBlocks.willow_planks;
    }

    @Override
    public boolean isAvailable() {
        return ModList.get().isLoaded("biomesoplenty");
        
    }

    @Override
    public boolean isFlammable() {
        return true;
    }


}
