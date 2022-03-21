package duke.clickgui.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Locale;


import net.minecraft.client.Minecraft;
import duke.Duke;
import duke.clickgui.component.components.Button;
import duke.modules.Module;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

//Your Imports


public class Frame {

	public ArrayList<Component> components;
	public Module.Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	public static int color;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	
	public Frame(Module.Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		/**
		 * 		public ArrayList<Module> getModulesInCategory(Category categoryIn){
		 * 			ArrayList<Module> mods = new ArrayList<Module>();
		 * 			for(Module m : this.modules){
		 * 				if(m.getCategory() == categoryIn)
		 * 					mods.add(m);
		 * 			}
		 * 			return mods;
		 * 		}
		 */
		
		for(Module mod : Duke.get().getModuleManager().getModulesByCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}

	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRendererObj) {
		//custom color
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(0,0,0,0,0);
		int colorCategory = 0;
		String text = null;
		switch(category.name().toUpperCase(Locale.ROOT)){
			case "COMBAT": {
				text = "B";
				colorCategory = new Color(255, 82, 82,255).getRGB();
				break;
			}
			case "MOVEMENT": {
				text = "G";
				colorCategory = new Color(46,204,113,255).getRGB();
				break;
			}
			case "PLAYER": {
				text = "F";
				colorCategory = new Color(142,68,173,255).getRGB();
				break;
			}
			case "VISUAL": {
				text = "H";
				colorCategory = new Color(55,0,206,255).getRGB();
				break;
			}

		}
		color = colorCategory;
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(0,0,0,0,0);
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, colorCategory);
		Gui.drawRect(this.x, this.y, (this.x + this.width), (this.y + this.barHeight), colorCategory);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.category.name(), (this.x + 2) * 2 + 5 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), (this.y + 2.5f) * 2 + 5, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				//Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), (int) HUD.ArrayColor);
				//Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, (int) HUD.ArrayColor);
				//Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), (int) HUD.ArrayColor);
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
