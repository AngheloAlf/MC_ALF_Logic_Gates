package angheloalf.alf_logic_gates.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClockMessage implements IMessage{
    // A default constructor is always required
    public ClockMessage(){}

    private int maxCount;
    private int step;
    public ClockMessage(int maxCount, int step){
        this.maxCount = maxCount;
        this.step = step;
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(maxCount);
        buf.writeInt(step);
    }

    @Override
    public void fromBytes(ByteBuf buf){
        maxCount = buf.readInt();
        step = buf.readInt();
    }

    // The params of the IMessageHandler are <REQ, REPLY>
    // This means that the first param is the packet you are receiving, and the second is the packet you are returning.
    // The returned packet can be used as a "response" from a sent packet.
    static public class ClockMessageHandler implements IMessageHandler<ClockMessage, IMessage>{
        // Do note that the default constructor is required, but implicitly defined in this case

        public ClockMessageHandler(){

        }

        @Override public IMessage onMessage(ClockMessage message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

            int maxCount = message.maxCount;
            System.out.println("SERVER: "+maxCount);

            // How do i access the tile entity?

            /*serverPlayer.getServerWorld().addScheduledTask(() -> {

            });*/
            // No response packet
            return null;
        }
    }
}