package angheloalf.alf_logic_gates.blocks.base_blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ThreeDiffInputLogicBlock extends LogicBlock{
    public ThreeDiffInputLogicBlock(String blockName){
        super(blockName);
    }

    @Override
    protected int getMaxStates(){
        return 6;
    }

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        return side != EnumFacing.UP && side != EnumFacing.DOWN;
    }


    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        return new EnumFacing[0];
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return false;
    }

    @Override
    protected int getAlternativePower(IBlockState state, World world, BlockPos pos, EnumFacing side){
        return 0;
    }

    protected int getSpecialInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            case 1:
                return getLeftSidePower(state, world, pos);
            case 2:
            case 3:
                return getBackSidePower(state, world, pos);
            case 4:
            case 6:
                return getRightSidePower(state, world, pos);
        }
        return 0;
    }

    protected int getFirstInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return getBackSidePower(state, world, pos);
            case 1:
                return getRightSidePower(state, world, pos);
            case 2:
                return getLeftSidePower(state, world, pos);
            case 3:
                return getRightSidePower(state, world, pos);
            case 4:
                return getLeftSidePower(state, world, pos);
            case 5:
                return getBackSidePower(state, world, pos);
        }
        return 0;
    }

    protected int getSecondInput(IBlockState state, World world, BlockPos pos){
        switch(state.getValue(BLOCK_STATE)){
            case 0:
            return getRightSidePower(state, world, pos);
            case 1:
                return getBackSidePower(state, world, pos);
            case 2:
                return getRightSidePower(state, world, pos);
            case 3:
                return getLeftSidePower(state, world, pos);
            case 4:
                return getBackSidePower(state, world, pos);
            case 5:
                return getLeftSidePower(state, world, pos);
        }
        return 0;
    }
}
