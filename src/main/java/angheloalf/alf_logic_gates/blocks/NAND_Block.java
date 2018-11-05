package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NAND_Block extends LogicBlock{
    public NAND_Block(){
        super("nand_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        LogicTileEntity tileEntity = getTE(world, pos);
        if(tileEntity == null){
            return 0;
        }

        int aPower = getAPower(world, pos, blockState);
        int bPower = getBPower(world, pos, blockState);
        int cPower = getCPower(world, pos, blockState);
        switch(tileEntity.getClickCount()){
            case 0:
                return negate(AND_Block.and(aPower, bPower));
            case 1:
                return negate(AND_Block.and(aPower, cPower));
            case 2:
                return negate(AND_Block.and(bPower, cPower));
            case 3:
                return negate(AND_Block.and(AND_Block.and(bPower, cPower), cPower));
        }
        return 0;
    }
}
