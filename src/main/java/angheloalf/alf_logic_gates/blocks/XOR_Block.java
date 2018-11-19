package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoInputLogicBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class XOR_Block extends TwoInputLogicBlock{
    public XOR_Block(){
        super("xor_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        int aPower = getAPower(world, pos, state);
        int bPower = getBPower(world, pos, state);
        int cPower = getCPower(world, pos, state);
        switch(state.getValue(BLOCK_STATE)){
            case 0:
                return xor(aPower, bPower);
            case 1:
                return xor(aPower, cPower);
            case 2:
                return xor(bPower, cPower);
            case 3:
                return xor(xor(aPower, bPower), cPower);
        }
        return 0;
    }
}
