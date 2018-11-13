package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AND_Block extends LogicBlock{
    public AND_Block(){
        super("and_block");
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
                return and(aPower, bPower);
            case 1:
                return and(aPower, cPower);
            case 2:
                return and(bPower, cPower);
            case 3:
                return and(and(aPower, bPower), cPower);
        }

        return 0;
    }

    public static int and(int a, int b){
        int value = a < b ? a : b;
        return repeatSignalOrPower(value);
    }
}
