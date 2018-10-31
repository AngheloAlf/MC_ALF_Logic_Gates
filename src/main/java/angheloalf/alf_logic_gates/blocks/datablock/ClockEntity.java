package angheloalf.alf_logic_gates.blocks.datablock;

import net.minecraft.block.state.IBlockState;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ClockEntity extends TileEntity implements ITickable{
    // We only calculate this value on the client so we don't care about the server
    // side and we also don't care about saving this value. That's why we have no
    // readFromNBT() and writeToNBT().
    private boolean lit = false;

    // Counter that is decremented faster if there are more entities in the vicinity.
    // If it reaches negative we toggle the light
    private int counter = 0;

    // To prevent counting entities every tick we delay it for 10 ticks and remember the last count we had.
    private int delayCounter = 10;
    private int lastCount = 1;

    public ClockEntity(){
        super();
    }

    public boolean isOn(){
        return lit;
    }

    @Override
    public void update(){
        //updateCounter();
        counter -= lastCount * 4;
        if (counter <= 0) {
            markDirty();
            // world.notifyBlockUpdate(pos, null, null, 3);
            lit = !lit;
            counter = 400;      // This is 20 seconds. Rate increases if more mods are near
            // world.markBlockRangeForRenderUpdate(getPos(), getPos());
        }
    }

    private void updateCounter() {
        // Don't count the entities every tick. That would be a bit slow.
        delayCounter--;
        if (delayCounter <= 0) {
            //List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(
            //         getPos().add(-10, -10, -10), getPos().add(10, 10, 10)));
            delayCounter = 10;
            lastCount = 1;
            // lastCount = list.size();
        }
        else{
            lastCount = 0;
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        counter = compound.getInteger("counter");
        delayCounter = compound.getInteger("delayCounter");
        lastCount = compound.getInteger("lastCount");
        lit = compound.getBoolean("lit");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("counter", counter);
        compound.setInteger("delayCounter", delayCounter);
        compound.setInteger("lastCount", lastCount);
        compound.setBoolean("lit", lit);
        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
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
    public SPacketUpdateTileEntity getUpdatePacket() {
        // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the client you can write
        // a more optimal NBT here.
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }
}
