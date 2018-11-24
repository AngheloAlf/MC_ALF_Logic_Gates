package angheloalf.alf_logic_gates.network;

import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;

import io.netty.buffer.ByteBuf;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClockMessage implements IMessage{
    private BlockPos pos;
    private boolean isRemote;
    private int maxCount;
    private int step;
    private boolean disabled;
    private boolean fullRestarting;

    public ClockMessage(){

    }

    public ClockMessage(BlockPos pos, boolean isRemote, int maxCount, int step, boolean disabled, boolean fullRestarting){
        this.pos = pos;
        this.isRemote = isRemote;

        this.maxCount = maxCount;
        this.step = step;
        this.disabled = disabled;
        this.fullRestarting = fullRestarting;
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        buf.writeBoolean(isRemote);

        buf.writeInt(maxCount);
        buf.writeInt(step);

        buf.writeBoolean(disabled);
        buf.writeBoolean(fullRestarting);
    }

    @Override
    public void fromBytes(ByteBuf buf){
        int xBlock = buf.readInt();
        int yBlock = buf.readInt();
        int zBlock = buf.readInt();
        pos = new BlockPos(xBlock, yBlock, zBlock);

        isRemote = buf.readBoolean();

        maxCount = buf.readInt();
        step = buf.readInt();

        disabled = buf.readBoolean();
        fullRestarting = buf.readBoolean();
    }

    // The params of the IMessageHandler are <REQ, REPLY>
    // This means that the first param is the packet you are receiving, and the second is the packet you are returning.
    // The returned packet can be used as a "response" from a sent packet.
    static public class ClockMessageHandler implements IMessageHandler<ClockMessage, IMessage>{
        public ClockMessageHandler(){

        }

        @Override
        public IMessage onMessage(ClockMessage message, MessageContext ctx){
            if(!message.isRemote){
                return parseFromServerMessage(message, ctx);
            }
            else{
                return parseFromClientMessage(message, ctx);
            }
        }

        private IMessage parseFromServerMessage(ClockMessage message, MessageContext ctx){
            World world = Minecraft.getMinecraft().player.world;
            TileEntity tileEntity = world.getTileEntity(message.pos);

            int maxCount = message.maxCount;
            int step = message.step;
            boolean disable = message.disabled;
            boolean fullRestarting = message.fullRestarting;
            if(tileEntity instanceof ClockEntity){
                ClockEntity clockEntity = ((ClockEntity) tileEntity);
                clockEntity.setMaxCount(maxCount);
                clockEntity.setStep(step);
                clockEntity.disable(disable, fullRestarting);
            }
            return null; // No response packet
        }
        private IMessage parseFromClientMessage(ClockMessage message, MessageContext ctx){
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            TileEntity tileEntity = serverPlayer.world.getTileEntity(message.pos);

            int maxCount = message.maxCount;
            int step = message.step;
            boolean disable = message.disabled;
            boolean fullRestarting = message.fullRestarting;
            if(tileEntity instanceof ClockEntity){
                ClockEntity clockEntity = ((ClockEntity) tileEntity);
                serverPlayer.getServerWorld().addScheduledTask(() -> {
                    clockEntity.setMaxCount(maxCount);
                    clockEntity.setStep(step);
                    clockEntity.disable(disable, fullRestarting);
                });
            }
            return null; // No response packet
        }
    }
}