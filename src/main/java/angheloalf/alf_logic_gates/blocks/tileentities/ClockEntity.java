package angheloalf.alf_logic_gates.blocks.tileentities;

import angheloalf.alf_logic_gates.blocks.base_blocks.AlfBaseBlock;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClockEntity extends TileEntity implements ITickable{
    private final int defaultMaxCont = 20; // 20 equals 1 second
    private boolean disabled = false;
    private boolean fullRestarting = false;
    private boolean lit = false;
    private int power = 0;
    private int counter = 0;
    private int step = 1;
    private int maxCount = defaultMaxCont;

    public ClockEntity(){
        super();
    }

    public boolean isAlternatePowering(){
        return lit && !disabled;
    }

    public int getPower(){
        return power;
    }

    public void disable(boolean disable){
        this.disabled = disable;
        fullRestarting = true;
    }

    public int getMaxCount(){
        return maxCount / 2;
    }

    public void setMaxCount(int newMaxCount){
        maxCount = newMaxCount * 2;
    }

    public int getStep(){
        return step;
    }

    public void setStep(int step){
        this.step = step;
    }

    public void reset(){
        maxCount = defaultMaxCont;
        step = 1;
    }

    public void fullRestart(){
        reset();

        counter = 0;
        lit = false;
        disabled = false;
        fullRestarting = false;
    }

    @Override
    public void update(){
        if(!disabled){
            if(fullRestarting){
                fullRestart();
            }
            counter -= step;
            if(counter <= 0){
                lit = !lit;
                power = lit ? 15 : 0;
                counter = maxCount;
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

                AlfBaseBlock.notifyStrongPowerToNeighbors(world, blockType, pos);
            }
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player){
        if(this.world.getTileEntity(this.pos) != this){
            return false;
        }
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        disabled = compound.getBoolean("disabled");
        fullRestarting = compound.getBoolean("fullRestarting");

        lit = compound.getBoolean("lit");
        power = compound.getInteger("power");

        counter = compound.getInteger("counter");
        step = compound.getInteger("step");
        maxCount = compound.getInteger("maxCount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound = super.writeToNBT(compound);

        compound.setBoolean("disabled", disabled);
        compound.setBoolean("fullRestarting", fullRestarting);

        compound.setBoolean("lit", lit);
        compound.setInteger("power", power);

        compound.setInteger("counter", counter);
        compound.setInteger("step", step);
        compound.setInteger("maxCount", maxCount);

        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag(){
        // getUpdateTag() is called whenever the chunkdata is sent to the
        // client. In contrast getUpdatePacket() is called when the tile entity
        // itself wants to sync to the client. In many cases you want to send
        // over the same information in getUpdateTag() as in getUpdatePacket().
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound nbt){
        readFromNBT(nbt);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the client you can write
        // a more optimal NBT here.
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet){
        // Here we get the packet from the server and read it into our client side tile entity
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }
}
