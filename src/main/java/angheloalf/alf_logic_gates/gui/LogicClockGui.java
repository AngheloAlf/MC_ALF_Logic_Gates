package angheloalf.alf_logic_gates.gui;

import angheloalf.alf_logic_gates.Mod_ALF_Logic_Gates;
import angheloalf.alf_logic_gates.blocks.containers.ClockContainer;
import angheloalf.alf_logic_gates.blocks.datablock.ClockEntity;
import angheloalf.alf_logic_gates.network.ClockMessage;
import angheloalf.alf_logic_gates.network.LogicGatesPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.io.IOException;


public class LogicClockGui extends GuiContainer{
    private static final ResourceLocation texture = new ResourceLocation(Mod_ALF_Logic_Gates.MODID, "textures/gui/basic_gui.png");
    private ClockEntity clockEntity;
    private BlockPos position;

    private GuiTextField maxCountField;

    public LogicClockGui(ClockEntity clockEntity, BlockPos pos){
        super(new ClockContainer(clockEntity));
        this.clockEntity = clockEntity;
        this.position = pos;
        xSize = 176;
        ySize = 75;
    }

    @Override
    public void initGui(){
        super.initGui();
        buttonList.add(new GuiButton(1, guiLeft+41, guiTop+40, 40, 20, "reset"));

        buttonList.add(new GuiButton(2, guiLeft+20, guiTop+20, 20, 20, "-"));
        maxCountField = new GuiTextField(4, this.fontRenderer, 20+20+1, 20, 40, 20);
        buttonList.add(new GuiButton(3, guiLeft+20+20+40+2, guiTop+20, 20, 20, "+"));
    }

    @Override
    protected void actionPerformed(GuiButton b){
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        int actualMaxCount = clockEntity.getMaxCount();

        switch(b.id){
            case 1: // reset
                clockEntity.reset();
                break;
            case 2: // -
                clockEntity.setMaxCount(--actualMaxCount);
                break;
            case 3: // +
                clockEntity.setMaxCount(++actualMaxCount);
                break;
        }

        LogicGatesPacketHandler.INSTANCE.sendToServer(new ClockMessage(x, y, z, clockEntity.getMaxCount(), clockEntity.getStep()));
        updateTextFields();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y){
        // Bind the image texture of our custom container
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        // Draw the image
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    // draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
    // renders relative to the top left corner of the background
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        final int LABEL_XPOS = 8;
        final int LABEL_YPOS = 6;
        String name = "Logic clock";
        if(clockEntity.getDisplayName() != null){
            name = clockEntity.getDisplayName().getUnformattedText();
        }
        fontRenderer.drawString(name, LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

        updateTextFields();
        maxCountField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);

    }

    protected void updateTextFields(){
        maxCountField.setText(String.valueOf(clockEntity.getMaxCount()));
    }

    @Override
    public void onGuiClosed(){
        System.out.println("Closing GUI.");
    }
}
