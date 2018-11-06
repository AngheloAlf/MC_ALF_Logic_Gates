package angheloalf.alf_logic_gates.blocks.datablock;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
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
public class LogicTileEntity extends TileEntity implements ITickable{
    protected int clicked = 0;
    protected int clicksMax = 4;

    // D Latch & D Flip-Flop
    protected int howMuchPower = 0;
    protected boolean powered = false;

    public LogicTileEntity(){
        super();
    }

    @Override
    public void update(){
        world.notifyNeighborsOfStateChange(getPos(), blockType, false);
    }

    public void setMax(int max){
        clicksMax = max;
    }

    public int click(){
        ++clicked;
        if(clicked >= clicksMax){
            clicked = 0;
        }
        markDirty();
        return clicked;
    }

    public int getClickCount(){
        return clicked;
    }

    public int setClick(int clicks){
        clicked = clicks % clicksMax;
        return clicked;
    }

    public int getHowMuchPower(){
        return howMuchPower;
    }

    public void setHowMuchPower(int power){
        this.howMuchPower = power;
    }

    public boolean isPowered(){
        return powered;
    }

    public void setPowered(boolean powered){
        this.powered = powered;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        clicked = compound.getInteger("clickedCount");
        clicksMax = compound.getInteger("clicksMax");
        howMuchPower = compound.getInteger("howMuchPower");
        powered = compound.getBoolean("powered");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("clickedCount", clicked);
        compound.setInteger("clicksMax", clicksMax);
        compound.setInteger("howMuchPower", howMuchPower);
        compound.setBoolean("powered", powered);
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
