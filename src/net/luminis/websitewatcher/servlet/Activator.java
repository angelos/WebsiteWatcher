package net.luminis.websitewatcher.servlet;

import java.util.Properties;

import javax.servlet.Servlet;

import net.luminis.websitewatcher.WebsiteWatcher;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext context, DependencyManager manager) throws Exception {
		Properties props = new Properties();
		props.put("alias", "/websitewatcher");
		manager.add(createComponent()
			.setInterface(Servlet.class.getName(), props)
			.setImplementation(WebsiteWatcherServlet.class)
			.add(createServiceDependency()
				.setService(WebsiteWatcher.class)
				.setCallbacks("watcherAdded", "watcherRemoved")));
	}

	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {
	}
}
