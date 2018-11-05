package angheloalf.alf_logic_gates.blocks;

import angheloalf.alf_logic_gates.blocks.base_blocks.LogicBlock;
import angheloalf.alf_logic_gates.blocks.datablock.LogicTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DFlipFlop_Block extends LogicBlock{
    public DFlipFlop_Block(){
        super("dflipflop_block");
    }
    /* Tile Entity */
    @Override
    public TileEntity createTileEntity(@Nullable World world, @Nullable IBlockState state){
        LogicTileEntity tileEntity = new LogicTileEntity();
        if(state != null){
            tileEntity.setClick(state.getValue(BLOCK_STATE));
            tileEntity.setMax(6);
        }
        return tileEntity;
    }
    /* END Tile Entity */


    @Override
    protected int getOutputPower(IBlockState blockState, World world, BlockPos pos){
        LogicTileEntity tileEntity = getTE(world, pos);
        if(tileEntity == null){
            return 0;
        }
        return repeatSignalOrPower(tileEntity.getHowMuchPower());
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock, BlockPos neighborPos){
        LogicTileEntity tileEntity = getTE(worldIn, pos);
        if(tileEntity != null){
            boolean clkPower = getCLKPower(state, worldIn, pos, tileEntity) > 0;
            if(clkPower){
                if(tileEntity.isPowered() != clkPower){
                    tileEntity.setPowered(true);

                    tileEntity.setHowMuchPower(getDPower(state, worldIn, pos, tileEntity));
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                }
            }
            else{
                tileEntity.setPowered(false);
            }
        }
    }


    @Override
    protected boolean isSideEnabled(IBlockState state, EnumFacing side){
        int block_state = state.getValue(BLOCK_STATE);
        EnumFacing left = state.getValue(FACING).getOpposite().rotateYCCW();
        EnumFacing back = left.rotateYCCW();
        EnumFacing right = back.rotateYCCW();
        switch(block_state){
            case 0:
            case 3:
                return side == left || side == back;
            case 1:
            case 4:
                return side == left || side == right;
            case 2:
            case 5:
                return side == left || side == right;
        }
        return false;
    }

    private int getCLKPower(IBlockState blockState, World world, BlockPos pos, @Nonnull LogicTileEntity tileEntity){
        int aPower = getRawAPower(world, pos, blockState);
        int bPower = getRawBPower(world, pos, blockState);
        int cPower = getRawCPower(world, pos, blockState);
        switch(tileEntity.getClickCount()){
            case 0:
            case 1:
                return aPower;
            case 2:
            case 3:
                return bPower;
            case 4:
            case 5:
                return cPower;
        }
        return 0;
    }

    private int getDPower(IBlockState blockState, World world, BlockPos pos, @Nonnull LogicTileEntity tileEntity){
        int aPower = getRawAPower(world, pos, blockState);
        int bPower = getRawBPower(world, pos, blockState);
        int cPower = getRawCPower(world, pos, blockState);
        switch(tileEntity.getClickCount()){
            case 3:
            case 4:
                return aPower;
            case 0:
            case 5:
                return bPower;
            case 1:
            case 2:
                return cPower;
        }
        return 0;
    }
}
