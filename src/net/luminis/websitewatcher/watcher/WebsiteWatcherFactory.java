package net.luminis.websitewatcher.watcher;

import java.net.MalformedURLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import net.luminis.websitewatcher.WebsiteWatcher;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

public class WebsiteWatcherFactory implements ManagedServiceFactory {
    public static final String PID = "net.luminis.websitewatcher.factory";
    
    private volatile DependencyManager m_dependencyManager;
    private final Map<String, Component> m_components = new HashMap<String, Component>();

    @Override
    public String getName() {
        return "website watcher factory";
    }

    @Override
    public void updated(String pid, @SuppressWarnings("rawtypes") Dictionary properties) throws ConfigurationException {
        if (m_components.containsKey(pid)) {
            return;
        }
        
        Component component;
		try {
			component = m_dependencyManager.createComponent()
				.setInterface(WebsiteWatcher.class.getName(), properties)
			    .setImplementation(new WebsiteWatcherImpl((String) properties.get(WebsiteWatcher.URL)));
		} catch (MalformedURLException e) {
			throw new ConfigurationException(WebsiteWatcher.URL, properties.get(WebsiteWatcher.URL) + " is not a valid URL", e);
		}
        
        m_components.put(pid, component);
        m_dependencyManager.add(component);
    }

    @Override
    public void deleted(String pid) {
        m_dependencyManager.remove(m_components.remove(pid));
    }
}
