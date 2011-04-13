package net.luminis.websitewatcher.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luminis.websitewatcher.WebsiteWatcher;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class WebsiteWatcherServlet extends HttpServlet {
	private final Map<String, WebsiteWatcher> m_watchers = new HashMap<String, WebsiteWatcher>();
	private String m_watcherPage;
	private volatile BundleContext m_bundleContext;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo() == null) {
			response.setHeader("Content-Type", "text/html");
			response.getWriter().write(m_watcherPage);
		}
		else if (request.getPathInfo().equals("/status")) {
			writeStatus(response);
		}
	}
	
	public void start() throws IOException {
		URL page = m_bundleContext.getBundle().getEntry("watcherpage.html");
		StringBuilder result = new StringBuilder();
		
		InputStream in = null;
		try {
			in = page.openStream();
			byte[] buf = new byte[1024];
			
			for (int read = in.read(buf); read > -1; read = in.read(buf)) {
				result.append(new String(buf, 0, read, "UTF-8"));
			}
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
		
		m_watcherPage = result.toString();
	}
	
	private void writeStatus(HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder("{");
		for (Map.Entry<String, WebsiteWatcher> entry : m_watchers.entrySet()) {
			if (result.length() > 1) {
				result.append(",");
			}
			result.append("\"" + entry.getKey() + "\":\"" + (entry.getValue().up() ? "up" : "down")  + "\"");
		}
		result.append("}");
		
		response.getWriter().write(result.toString());
	}
	
	public void watcherAdded(ServiceReference ref, WebsiteWatcher watcher) {
		m_watchers.put((String) ref.getProperty(WebsiteWatcher.URL), watcher);
	}

	public void watcherRemoved(ServiceReference ref, WebsiteWatcher watcher) {
		m_watchers.remove(ref.getProperty(WebsiteWatcher.URL));
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

}
