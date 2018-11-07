package angheloalf.alf_logic_gates.blocks.containers;

import angheloalf.alf_logic_gates.blocks.datablock.ClockEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ClockContainer extends Container{
    private ClockEntity tileEntity;

    public ClockContainer(ClockEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    // Vanilla calls this method every tick to make sure the player is still able to access the inventory, and if not closes the gui
    @Override
    public boolean canInteractWith(EntityPlayer player){
        return tileEntity.isUsableByPlayer(player);
    }
}
