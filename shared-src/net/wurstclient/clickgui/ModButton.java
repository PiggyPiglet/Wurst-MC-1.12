/*
 * Copyright � 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.clickgui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.wurstclient.features.Mod;

public final class ModButton extends Component
{
	private final Mod hack;
	
	public ModButton(Mod hack)
	{
		this.hack = hack;
		setWidth(getDefaultWidth());
		setHeight(getDefaultHeight());
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton)
	{
		if(mouseButton != 0)
			return;
		
		hack.setEnabled(!hack.isEnabled());
	}
	
	@Override
	public void render(int mouseX, int mouseY)
	{
		int x1 = getX();
		int y1 = getY();
		int x2 = x1 + getWidth();
		int y2 = y1 + getHeight();
		
		int scroll = getParent().isScrollingEnabled()
			? getParent().getScrollOffset() : 0;
		boolean hovering = mouseX >= x1 && mouseY >= y1 && mouseX < x2
			&& mouseY < y2 && mouseY >= -scroll
			&& mouseY < getParent().getHeight() - 13 - scroll;
		
		// color
		if(hack.isEnabled())
			if(hack.isBlocked())
				GL11.glColor4f(1, 0, 0, hovering ? 0.75F : 0.5F);
			else
				GL11.glColor4f(0, 1, 0, hovering ? 0.75F : 0.5F);
		else
			GL11.glColor4f(0.25F, 0.25F, 0.25F, hovering ? 0.75F : 0.5F);
		
		// background
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(x1, y1);
		GL11.glVertex2i(x1, y2);
		GL11.glVertex2i(x2, y2);
		GL11.glVertex2i(x2, y1);
		GL11.glEnd();
		
		// outline
		GL11.glColor4f(0.0625F, 0.0625F, 0.0625F, 0.5F);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2i(x1, y1);
		GL11.glVertex2i(x1, y2);
		GL11.glVertex2i(x2, y2);
		GL11.glVertex2i(x2, y1);
		GL11.glEnd();
		
		// hack name
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		int fx = x1 + (getWidth() - fr.getStringWidth(hack.getName())) / 2;
		int fy = y1 + 2;
		fr.drawString(hack.getName(), fx, fy, 0xf0f0f0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	@Override
	public int getDefaultWidth()
	{
		return Minecraft.getMinecraft().fontRendererObj
			.getStringWidth(hack.getName()) + 2;
	}
	
	@Override
	public int getDefaultHeight()
	{
		return 11;
	}
}
