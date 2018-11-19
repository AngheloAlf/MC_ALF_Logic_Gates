package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.TwoDiffInputLogicBlock;
import angheloalf.alf_logic_gates.blocks.tileentities.LogicTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DFlipFlop_Block extends TwoDiffInputLogicBlock{
    public DFlipFlop_Block(){
        super("dflipflop_block");
    }

    @Override
    protected int getOutputPower(IBlockState state, World world, BlockPos pos){
        LogicTileEntity tileEntity = getTE(world, pos);
        if(tileEntity == null){
            return 0;
        }
        return repeatSignalOrPower(tileEntity.getPower());
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World worldIn, @Nullable BlockPos pos, @Nullable Block neighborBlock, @Nullable BlockPos neighborPos){
        if(worldIn == null || pos == null || state == null){
            return;
        }

        LogicTileEntity tileEntity = getTE(worldIn, pos);
        if(tileEntity != null){
            boolean clkPower = getCLKPower(state, worldIn, pos) > 0;
            if(clkPower){
                if(tileEntity.isAlternatePowering() != clkPower){
                    tileEntity.setAlternatePower(true);

                    tileEntity.setPower(getDPower(state, worldIn, pos));
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                }
            }
            else{
                tileEntity.setAlternatePower(false);
            }
        }
    }
}
