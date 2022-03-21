package duke.clickgui.component.components.sub;



import duke.clickgui.component.Frame;
import duke.modules.settings.Setting;
import duke.modules.settings.impl.BooleanSetting;
import org.lwjgl.opengl.GL11;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;


//Your Imports

public class Checkbox extends duke.clickgui.component.Component {

	private boolean hovered;
	private Setting op;
	private duke.clickgui.component.components.Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Checkbox(Setting option, duke.clickgui.component.components.Button button, int offset) {
		this.op = option;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}

	@Override
	public void renderComponent() {
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12,  new Color(24, 24, 24,175).getRGB());
		//Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.op.name , (parent.parent.getX()+ 4) * 2 + 10, (parent.parent.getY() + offset + 2) * 2 + 4, new Color(168, 167, 167,255).getRGB());
		GL11.glPopMatrix();

		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset + 3, parent.parent.getX() + 9 - 1, parent.parent.getY() + offset + 9, new Color(11,11,11,255).getRGB());
		if(((BooleanSetting)op).enabled)
			Gui.drawRect(parent.parent.getX() + 3, parent.parent.getY() + offset + 4, parent.parent.getX() + 8 - 1, parent.parent.getY() + offset + 8, Frame.color);
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
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
			((BooleanSetting)op).enabled = !((BooleanSetting)op).enabled;
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
