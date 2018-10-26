package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class XOR_Block extends LogicBlock{
    public XOR_Block(){
        super("xor_block");
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
                        return xor(aPower, bPower);
                    case 1:
                        return xor(aPower, cPower);
                    case 2:
                        return xor(bPower, cPower);
                    case 3:
                        return xor(xor(aPower, bPower), cPower);
                }
            }
        }
        return 0;
    }

    private int xor(int a, int b){
        if(b == 0){
            return a;
        }
        if(a == 0){
            return b;
        }
        return 0;
    }
}
