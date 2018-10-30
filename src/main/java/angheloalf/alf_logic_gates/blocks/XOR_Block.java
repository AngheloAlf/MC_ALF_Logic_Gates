package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class XOR_Block extends LogicBlock{
    public XOR_Block(){
        super("xor_block");
    }

    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        boolean aEnabled = isAEnabled(world, pos);
        boolean bEnabled = isBEnabled(world, pos);
        boolean cEnabled = isCEnabled(world, pos);

        int aPower = aEnabled ? getAPower(world, pos, blockState): 0;
        int bPower = bEnabled ? getBPower(world, pos, blockState): 0;
        int cPower = cEnabled ? getCPower(world, pos, blockState): 0;

        LogicTileEntity tileEntity = getTE(world, pos);
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
        return 0;
    }

    public static int xor(int a, int b){
        int value = 0;
        if(b == 0){
            value = a;
        }
        else if(a == 0){
            value = b;
        }
        return repeatSignalOrPower(value);
    }
}
