package angheloalf.alf_logic_gates.gui;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.containers.ClockContainer;
import angheloalf.alf_logic_gates.blocks.datablock.ClockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;


public class LogicClockGui extends GuiContainer{
    private static final ResourceLocation texture = new ResourceLocation(Mod_ALF_Logic_Gates.MODID, "textures/gui/basic_gui.png");
    private ClockEntity clockEntity;

    public  LogicClockGui(ClockEntity clockEntity){
        super(new ClockContainer(clockEntity));
        this.clockEntity = clockEntity;
        xSize = 176;
        ySize = 75;
    }

    @Override
    public void initGui(){
        super.initGui();
        buttonList.add(new GuiButton(1, guiLeft+30, guiTop+50, 40, 20, "reset"));
        buttonList.add(new GuiButton(2, guiLeft+40, guiTop+30, 20, 20, "+"));
        buttonList.add(new GuiButton(3, guiLeft+20, guiTop+30, 20, 20, "-"));
    }

    @Override
    protected void actionPerformed(GuiButton b){
        int actualMaxCount = clockEntity.getMaxCount();
        System.out.println("Actual: " + actualMaxCount);
        switch(b.id){
            case 1: // reset
                System.out.println("Reset button clicked!");
                break;
            case 2: // +
                System.out.println("Setting: " + (actualMaxCount+1));
                clockEntity.setMaxCount(actualMaxCount + 1);
                break;
            case 3: // -
                System.out.println("Setting: " + (actualMaxCount-1));
                clockEntity.setMaxCount(actualMaxCount - 1);
                break;
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        // Bind the image texture of our custom container
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        // Draw the image
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    // draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
    // renders relative to the top left corner of the background
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        final int LABEL_XPOS = 8;
        final int LABEL_YPOS = 6;
        String name = "Logic clock";
        if(clockEntity.getDisplayName() != null){
            name = clockEntity.getDisplayName().getUnformattedText();
        }
        fontRenderer.drawString(name, LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());
    }

}
