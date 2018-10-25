package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class OR_Block extends LogicBlock{
    public OR_Block(String blockName){
        super(blockName);
    }

    public OR_Block(){
        super("or_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockState.getValue(FACING).getOpposite() == side){
            if(blockAccess instanceof World){
                World worldIn = (World) blockAccess;
                int aPower = isAEnabled(worldIn, pos) ? getAPower(worldIn, pos, blockState) : 0;
                int bPower = isBEnabled(worldIn, pos) ? getBPower(worldIn, pos, blockState) : 0;
                int cPower = isCEnabled(worldIn, pos) ? getCPower(worldIn, pos, blockState) : 0;

                if(aPower > 0 || bPower > 0 || cPower > 0){
                    return Math.max(Math.max(aPower, bPower), cPower);
                }
            }
        }
        return 0;
    }
}
