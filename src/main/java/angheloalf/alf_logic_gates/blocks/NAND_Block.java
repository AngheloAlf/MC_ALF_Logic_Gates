package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NAND_Block extends LogicBlock{
    public NAND_Block(){
        super("nand_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        if(blockState.getValue(FACING).getOpposite() == side){
            if(blockAccess instanceof World){
                World worldIn = (World) blockAccess;
                boolean aEnabled = isAEnabled(worldIn, pos);
                boolean bEnabled = isBEnabled(worldIn, pos);
                boolean cEnabled = isCEnabled(worldIn, pos);

                int aPower = aEnabled ? getAPower(worldIn, pos, blockState) : 0;
                int bPower = bEnabled ? getBPower(worldIn, pos, blockState) : 0;
                int cPower = cEnabled ? getCPower(worldIn, pos, blockState) : 0;

                LogicTileEntity tileEntity = getTE(worldIn, pos);
                if(tileEntity == null){
                    return 0;
                }

                switch(tileEntity.getClickCount()){
                    case 0:
                        return negate(and(aPower, bPower));
                    case 1:
                        return negate(and(aPower, cPower));
                    case 2:
                        return negate(and(bPower, cPower));
                    case 3:
                        return negate(and(and(bPower, cPower), cPower));
                }
            }
            return 15;
        }
        return 0;
    }

    private int and(int a, int b){
        return a < b ? a : b;
    }
}
