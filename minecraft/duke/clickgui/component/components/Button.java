package duke.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;


import duke.clickgui.component.Component;
import duke.clickgui.component.Frame;
import duke.clickgui.component.components.sub.Checkbox;
import duke.clickgui.component.components.sub.Keybind;
import duke.clickgui.component.components.sub.ModeButton;
import duke.clickgui.component.components.sub.Slider;
import duke.modules.Module;
import duke.modules.settings.Setting;
import duke.modules.settings.impl.BooleanSetting;
import duke.modules.settings.impl.ModeSetting;
import duke.modules.settings.impl.NumberSetting;
import duke.util.Timer;
import org.lwjgl.opengl.GL11;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

//Your Imports


public class Button extends Component {
	public Timer timer = new Timer();
	public Module mod;
	public Frame parent;
	public int offset;
	public int pies = 12;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if(mod.settings != null) {
			for(Setting s : mod.settings){
				if(s instanceof ModeSetting){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s instanceof NumberSetting){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s instanceof BooleanSetting){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));

	}

	@Override
	public void setOff(int newOff) {
		
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {

		//custom color
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, (int) (this.mod.toggled ?Frame.color: new Color(24, 24, 24,255).getRGB()));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.name, (parent.getX() + 3) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
		if(this.subcomponents.size() > 2)
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "<" : ">", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, Frame.color);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				//Gui.drawRect(parent.getX(), parent.getY() + this.offset + 12, parent.getX() + 1, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), (int) new Color(ClickGUI.red, ClickGUI.green, ClickGUI.blue,255).getRGB());
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
