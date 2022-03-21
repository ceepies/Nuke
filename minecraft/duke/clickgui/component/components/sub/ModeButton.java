package duke.clickgui.component.components.sub;




import duke.modules.Module;
import duke.modules.settings.Setting;
import duke.modules.settings.impl.ModeSetting;
import org.lwjgl.opengl.GL11;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;


//Your Imports


public class ModeButton extends duke.clickgui.component.Component {

	private boolean hovered;
	private duke.clickgui.component.components.Button parent;
	private Setting set;
	private int offset;
	private int x;
	private int y;
	private Module mod;

	private int modeIndex;
	
	public ModeButton(Setting set, duke.clickgui.component.components.Button button, Module mod, int offset) {
		this.set = set;
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}

	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, new Color(24, 24, 24,175).getRGB());
		//Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(((ModeSetting)set).name + ": ", (parent.parent.getX() + 2) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, new Color(168, 167, 167,255).getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(((ModeSetting)set).name(), (parent.parent.getX() + 2) * 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(((ModeSetting)set).name + ": "), (parent.parent.getY() + offset + 2) * 2 + 5, duke.clickgui.component.Frame.color);
		GL11.glPopMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			ModeSetting modesetting = (ModeSetting)set;
			modesetting.cycle();
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
