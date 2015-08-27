package com.spring;

import com.common.LoginFilter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Servlet implementation class DynamicImage
 */
public class DynamicImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public DynamicImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		float size = 20.0f;
		HttpSession session = request.getSession(true);
		MantraOTP mantraOTP = (MantraOTP)session.getAttribute(
				LoginFilter.OTP_SESSION_VALUE);
		
		String text = (mantraOTP == null)? "   " : mantraOTP.getOtpString();
		BufferedImage buffer =
			    new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = buffer.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			                    RenderingHints.VALUE_ANTIALIAS_ON);
			FontRenderContext fc = g2.getFontRenderContext();
			int fontsize = 20;
	        Font font = new Font("Helvetica",Font.BOLD,fontsize);
	        g2.setFont(font);
			Rectangle2D bounds = g2.getFont().getStringBounds( "  " + text + "  ",fc);
			
			// calculate the size of the text
			int width = (int) bounds.getWidth();
			int height = (int) bounds.getHeight();

			// prepare some output
			buffer = new BufferedImage(width, height,
			                           BufferedImage.TYPE_INT_RGB);
			g2 = buffer.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			                    RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.setColor(Color.LIGHT_GRAY);
//			g2.getFont().deriveFont(size);
			g2.fillRect(0,0,width,height);
			g2.setColor(Color.BLUE);
			g2.setFont(font);
			g2.drawString(text,5, (int)-bounds.getY());

//			g2.setFont(font);
			
	
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();

			// output the image as png
			ImageIO.write(buffer, "png", os);
			os.close();
			
	}

}
