package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoDiffInputLogicBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;

import angheloalf.alf_logic_gates.util.Logic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DLatch_Block extends TwoDiffInputLogicBlock{
    public DLatch_Block(){
        super("dlatch_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        LogicTileEntity tileEntity = getTE(world, pos);
        if(tileEntity == null){
            return 0;
        }

        if(getCLKPower(state, world, pos) > 0){
            return Logic.repeatSignalOrPower(getDPower(state, world, pos));
        }

        return tileEntity.getPower();
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World world, @Nullable BlockPos pos, @Nullable Block neighborBlock, @Nullable BlockPos neighborPos){
        if(world == null || pos == null || state == null){
            return;
        }

        LogicTileEntity tileEntity = getTE(world, pos);
        if(tileEntity != null){
            if(getCLKPower(state, world, pos) > 0){
                boolean old = tileEntity.getPower() > 0;
                tileEntity.setPower(getDPower(state, world, pos));

                if(old != (tileEntity.getPower() > 0)){
                    world.notifyBlockUpdate(pos, state, state, 3);
                    notifyStrongPowerToNeighbors(world, pos);
                }
            }
        }
    }
}
