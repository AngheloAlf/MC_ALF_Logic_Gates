package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputLogicBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class XNOR_Block extends TwoInputLogicBlock{
    public XNOR_Block(){
        super("xnor_block");
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
                return negate(XOR_Block.xor(aPower, bPower));
            case 1:
                return negate(XOR_Block.xor(aPower, cPower));
            case 2:
                return negate(XOR_Block.xor(bPower, cPower));
            case 3:
                return negate(XOR_Block.xor(XOR_Block.xor(aPower, bPower), cPower));
        }
        return 0;
    }
}
