package angheloalf.alf_logic_gates.network;

import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClockMessage implements IMessage{
    private int maxCount;
    private int step;
    private int xBlock;
    private int yBlock;
    private int zBlock;

    public ClockMessage(){

    }

    public ClockMessage(int xBlock, int yBlock, int zBlock, int maxCount, int step){
        this.maxCount = maxCount;
        this.step = step;
        this.xBlock = xBlock;
        this.yBlock = yBlock;
        this.zBlock = zBlock;
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(xBlock);
        buf.writeInt(yBlock);
        buf.writeInt(zBlock);

        buf.writeInt(maxCount);
        buf.writeInt(step);
    }

    @Override
    public void fromBytes(ByteBuf buf){
        xBlock = buf.readInt();
        yBlock = buf.readInt();
        zBlock = buf.readInt();

        maxCount = buf.readInt();
        step = buf.readInt();
    }

    // The params of the IMessageHandler are <REQ, REPLY>
    // This means that the first param is the packet you are receiving, and the second is the packet you are returning.
    // The returned packet can be used as a "response" from a sent packet.
    static public class ClockMessageHandler implements IMessageHandler<ClockMessage, IMessage>{
        public ClockMessageHandler(){

        }

        @Override
        public IMessage onMessage(ClockMessage message, MessageContext ctx){
            int xBlock = message.xBlock;
            int yBlock = message.yBlock;
            int zBlock = message.zBlock;

            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            TileEntity tileEntity = serverPlayer.world.getTileEntity(new BlockPos(xBlock, yBlock, zBlock));

            int maxCount = message.maxCount;
            int step = message.step;
            if(tileEntity instanceof ClockEntity){
                serverPlayer.getServerWorld().addScheduledTask(() -> {
                    ((ClockEntity) tileEntity).setMaxCount(maxCount);
                    ((ClockEntity) tileEntity).setStep(step);
                });
            }
            return null; // No response packet
        }
    }
}