package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.core.DBBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBBlockStates extends BlockStateProvider {

    public DBBlockStates(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockStateGenerationHelper helper = new BlockStateGenerationHelper(Constants.MOD_ID, this);
        for (IWoodType wood : VanillaWoodTypes.values()) {
            if (wood != VanillaWoodTypes.BAMBOO)
                helper.beamBlock(DBBlocks.BEAMS.get(wood).get());
            helper.palisadeBlock(DBBlocks.PALISADES.get(wood).get());
            helper.seatBlock(DBBlocks.SEATS.get(wood).get());
            helper.supportBlock(DBBlocks.SUPPORTS.get(wood).get());
        }

        ModelFile barPanelBottomModel = helper.withSideEndTextures(helper.createChildModel("bar_panel_bottom", "bar_panel_bottom"), "bar_panel");
        ModelFile barPanelTopModel = helper.withSideEndTextures(helper.createChildModel("bar_panel_top", "bar_panel_top"), "bar_panel");
        ModelFile barPanelOpenModel = helper.withSideEndTextures(helper.createChildModel("bar_panel_open", "bar_panel_open"), "bar_panel");
        trapdoorBlock((TrapDoorBlock) DBBlocks.BAR_PANEL.get(), barPanelBottomModel, barPanelTopModel, barPanelOpenModel, true);

//        ModelFile chainModel = models().getBuilder("chain").parent(new ModelFile.UncheckedModelFile(modLoc("custom/chain")))
//                .texture("particle", modLoc("block/chain"))
//                .texture("texture", modLoc("block/chain"));
//        axisBlock(Registration.CHAIN.get(), chainModel);
    }
}
