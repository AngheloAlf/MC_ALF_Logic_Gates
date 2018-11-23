package angheloalf.alf_logic_gates.gui;

import angheloalf.alf_logic_gates.blocks.containers.ClockContainer;
import angheloalf.alf_logic_gates.blocks.tileentities.ClockEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
    private final static GuiHandler guiHandlerRegistry = new GuiHandler();
    private final static int GUI_ID = 0xa1f;

    public static int getGuiID(){
        return GUI_ID;
    }

    public static GuiHandler getInstance(){
        return guiHandlerRegistry;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        if(ID != getGuiID()){
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if(tileEntity instanceof ClockEntity){
            ClockEntity clockEntity = (ClockEntity) tileEntity;
            return new ClockContainer(clockEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        if(ID != getGuiID()){
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if(tileEntity instanceof ClockEntity){
            ClockEntity clockEntity = (ClockEntity) tileEntity;
            return new LogicClockGui(clockEntity, xyz);
        }
        return null;
    }
}
