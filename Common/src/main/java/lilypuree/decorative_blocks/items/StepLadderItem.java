package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.StepLadderBlock;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.registration.DBBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StepLadderItem extends SwitchableBlockItem<BooleanProperty, Boolean> {
    public static final ResourceLocation OVERRIDE_TAG = new ResourceLocation(Constants.MOD_ID, "narrow");

    public StepLadderItem(Block blockIn, Properties builder) {
        super(blockIn, builder, ModBlockProperties.NARROW, OVERRIDE_TAG.getPath());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("wiki.decorative_blocks.seat"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState block = level.getBlockState(pos);
        if (block.is(DBBlocks.STEP_LADDER.get())) {
            Direction facing = block.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BlockPos offsetPos;
            if (context.getClickLocation().y - pos.getY() < 0.5F) {
                offsetPos = pos.below().relative(facing.getOpposite());
            } else {
                offsetPos = pos.above().relative(facing);
            }
            InteractionResult result = this.place(new StepLadderPlaceContext(context, offsetPos, facing.getOpposite()));
            if (result.consumesAction()) return result;
            else if (!context.getPlayer().isCrouching())
                return InteractionResult.PASS;
        }
        return super.useOn(context);
    }

    protected static class StepLadderPlaceContext extends BlockPlaceContext {
        public StepLadderPlaceContext(UseOnContext context, BlockPos pos, Direction dir) {
            super(context.getLevel(), context.getPlayer(), context.getHand(), context.getItemInHand(),
                    new BlockHitResult(new Vec3(
                            pos.getX() + 0.5 + dir.getStepX(),
                            pos.getY() + 0.5 + dir.getStepY(),
                            pos.getZ() + 0.5 + dir.getStepZ()
                    ), dir, pos, false));

        }

        @Override
        public BlockPos getClickedPos() {
            return this.getHitResult().getBlockPos();
        }
    }
}