package angheloalf.alf_logic_gates.blocks.base_blocks;

import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class TwoDiffInputLogicBlock extends LogicBlock{
    public TwoDiffInputLogicBlock(String blockName){
        super(blockName);
    }

    /* Tile Entity */
    @Override
    protected int getMaxStates(){
        return 6;
    }
    /* END Tile Entity */

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
            case 3:
                return side == left || side == back;
            case 1:
            case 4:
                return side == left || side == right;
            case 2:
            case 5:
                return side == back || side == right;
        }
        return false;
    }

    protected int getCLKPower(IBlockState blockState, World world, BlockPos pos, LogicTileEntity tileEntity){
        int aPower = getRawAPower(world, pos, blockState);
        int bPower = getRawBPower(world, pos, blockState);
        int cPower = getRawCPower(world, pos, blockState);
        switch(tileEntity.getClickCount()){
            case 0:
            case 1:
                return aPower;
            case 2:
            case 3:
                return bPower;
            case 4:
            case 5:
                return cPower;
        }
        return 0;
    }

    protected int getDPower(IBlockState blockState, World world, BlockPos pos, LogicTileEntity tileEntity){
        int aPower = getRawAPower(world, pos, blockState);
        int bPower = getRawBPower(world, pos, blockState);
        int cPower = getRawCPower(world, pos, blockState);
        switch(tileEntity.getClickCount()){
            case 3:
            case 4:
                return aPower;
            case 0:
            case 5:
                return bPower;
            case 1:
            case 2:
                return cPower;
        }
        return 0;
    }

    @Nullable
    @Override
    protected EnumFacing[] getAlternativesOutputs(IBlockState state){
        return null;
    }

    @Override
    protected boolean hasAlternativesOutputs(){
        return false;
    }

    @Override
    protected int getAlternativePower(IBlockState blockState, World world, BlockPos pos, EnumFacing side){
        return 0;
    }
}
