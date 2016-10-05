package colourMap;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class ColourMapFrameBuffer {
	
	private final int WIDTH;
	private final int HEIGHT;
	private int fbo;
	private int colourMap;
	
	
	protected ColourMapFrameBuffer(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		initialiseFrameBuffer();
	}
	
	
	protected void cleanUp() {
		GL30.glDeleteFramebuffers(fbo);
		GL11.glDeleteTextures(colourMap);
	}
	
	
	protected void bindFrameBuffer() {
		bindFrameBuffer(fbo, WIDTH, HEIGHT);
	}
	
	protected void unbindFrameBuffer() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	private void initialiseFrameBuffer() {
		fbo = createFrameBuffer();
		colourMap = createTextureAttachment(WIDTH, HEIGHT);
		unbindFrameBuffer();
	}
	
	private static void bindFrameBuffer(int frameBuffer, int width, int height) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, frameBuffer);
		GL11.glViewport(0, 0, width, height);
	}
	
	private static int createFrameBuffer() {
		int frameBuffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		GL11.glDrawBuffer(GL11.GL_NONE);
		GL11.glReadBuffer(GL11.GL_NONE);
		return frameBuffer;
	}
	
	private int createTextureAttachment( int width, int height) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height,
				0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
				texture, 0);
		return texture;
	}

}
