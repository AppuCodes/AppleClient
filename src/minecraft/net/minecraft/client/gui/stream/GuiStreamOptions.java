package net.minecraft.client.gui.stream;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiStreamOptions extends GuiScreen
{
    private static final GameOptions.Options[] field_152312_a = new GameOptions.Options[] {GameOptions.Options.STREAM_BYTES_PER_PIXEL, GameOptions.Options.STREAM_FPS, GameOptions.Options.STREAM_KBPS, GameOptions.Options.STREAM_SEND_METADATA, GameOptions.Options.STREAM_VOLUME_MIC, GameOptions.Options.STREAM_VOLUME_SYSTEM, GameOptions.Options.STREAM_MIC_TOGGLE_BEHAVIOR, GameOptions.Options.STREAM_COMPRESSION};
    private static final GameOptions.Options[] field_152316_f = new GameOptions.Options[] {GameOptions.Options.STREAM_CHAT_ENABLED, GameOptions.Options.STREAM_CHAT_USER_FILTER};
    private final GuiScreen parentScreen;
    private final GameOptions field_152318_h;
    private String field_152319_i;
    private String field_152313_r;
    private int field_152314_s;
    private boolean field_152315_t = false;

    public GuiStreamOptions(GuiScreen parentScreenIn, GameOptions p_i1073_2_)
    {
        this.parentScreen = parentScreenIn;
        this.field_152318_h = p_i1073_2_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        int i = 0;
        this.field_152319_i = I18n.format("options.stream.title", new Object[0]);
        this.field_152313_r = I18n.format("options.stream.chat.title", new Object[0]);

        for (GameOptions.Options options$options : field_152312_a)
        {
            if (options$options.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionSlider(options$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options$options));
            }
            else
            {
                this.buttonList.add(new GuiOptionButton(options$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options$options, this.field_152318_h.getKeyBinding(options$options)));
            }

            ++i;
        }

        if (i % 2 == 1)
        {
            ++i;
        }

        this.field_152314_s = this.height / 6 + 24 * (i >> 1) + 6;
        i = i + 2;

        for (GameOptions.Options options$options1 : field_152316_f)
        {
            if (options$options1.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionSlider(options$options1.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options$options1));
            }
            else
            {
                this.buttonList.add(new GuiOptionButton(options$options1.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), options$options1, this.field_152318_h.getKeyBinding(options$options1)));
            }

            ++i;
        }

        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height / 6 + 168, 150, 20, I18n.format("gui.done", new Object[0])));
        GuiButton guibutton = new GuiButton(201, this.width / 2 + 5, this.height / 6 + 168, 150, 20, I18n.format("options.stream.ingestSelection", new Object[0]));
        guibutton.enabled = this.mc.getTwitchStream().isReadyToBroadcast() && this.mc.getTwitchStream().func_152925_v().length > 0 || this.mc.getTwitchStream().func_152908_z();
        this.buttonList.add(guibutton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id < 100 && button instanceof GuiOptionButton)
            {
                GameOptions.Options options$options = ((GuiOptionButton)button).returnEnumOptions();
                this.field_152318_h.setOptionValue(options$options, 1);
                button.displayString = this.field_152318_h.getKeyBinding(GameOptions.Options.getEnumOptions(button.id));

                if (this.mc.getTwitchStream().isBroadcasting() && options$options != GameOptions.Options.STREAM_CHAT_ENABLED && options$options != GameOptions.Options.STREAM_CHAT_USER_FILTER)
                {
                    this.field_152315_t = true;
                }
            }
            else if (button instanceof GuiOptionSlider)
            {
                if (button.id == GameOptions.Options.STREAM_VOLUME_MIC.returnEnumOrdinal())
                {
                    this.mc.getTwitchStream().updateStreamVolume();
                }
                else if (button.id == GameOptions.Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal())
                {
                    this.mc.getTwitchStream().updateStreamVolume();
                }
                else if (this.mc.getTwitchStream().isBroadcasting())
                {
                    this.field_152315_t = true;
                }
            }

            if (button.id == 200)
            {
                this.mc.options.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button.id == 201)
            {
                this.mc.options.saveOptions();
                this.mc.displayGuiScreen(new GuiIngestServers(this));
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_152319_i, this.width / 2, 20, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_152313_r, this.width / 2, this.field_152314_s, 16777215);

        if (this.field_152315_t)
        {
            this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]), this.width / 2, 20 + this.fontRendererObj.FONT_HEIGHT, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
