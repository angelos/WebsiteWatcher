package net.luminis.websitewatcher.watcher;

import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedServiceFactory;

public class Activator extends DependencyActivatorBase {
    @Override
    public void init(BundleContext context, DependencyManager manager) throws Exception {
        Properties props = new Properties();
        props.put(Constants.SERVICE_PID, WebsiteWatcherFactory.PID);
        
        manager.add(createComponent()
                .setInterface(ManagedServiceFactory.class.getName(), props)
                .setImplementation(WebsiteWatcherFactory.class));
    }

    @Override
    public void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}
