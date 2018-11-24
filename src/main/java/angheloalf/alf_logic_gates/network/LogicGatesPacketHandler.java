package angheloalf.alf_logic_gates.network;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class LogicGatesPacketHandler{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Mod_ALF_Logic_Gates.MODID);
    public static int discriminator = 0;

    public static void init(){
        INSTANCE.registerMessage(ClockMessage.ClockMessageHandler.class, ClockMessage.class, ++discriminator, Side.SERVER);
        INSTANCE.registerMessage(ClockMessage.ClockMessageHandler.class, ClockMessage.class, ++discriminator, Side.CLIENT);
    }
}
