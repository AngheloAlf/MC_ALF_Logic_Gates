package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NOT_Block extends LogicBlock{
    public NOT_Block(){
        super("not_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockState.getValue(FACING).getOpposite() == side){
            int block_state = blockState.getValue(BLOCK_STATE);
            if(blockAccess instanceof World){
                World world = (World) blockAccess;
                switch(block_state){
                    case 0:
                        return getAPower(world, pos, blockState) == 0 ? 15: 0;
                    case 1:
                        return getBPower(world, pos, blockState) == 0 ? 15: 0;
                    case 2:
                        return getCPower(world, pos, blockState) == 0 ? 15: 0;
                    case 3:
                        return 15;
                }
            }
            return 15;
        }
        return 0;
    }

    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
                return side == left;
            case 1:
                return side == back;
            case 2:
                return side == right;
            case 3:
                return false;
        }
        return false;
    }
}
