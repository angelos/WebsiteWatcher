package net.luminis.websitewatcher.watcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.luminis.websitewatcher.WebsiteWatcher;

public class WebsiteWatcherImpl implements WebsiteWatcher {
    private URL m_site;
    
    public WebsiteWatcherImpl(String site) throws MalformedURLException {
        m_site = new URL(site);
    }
    
	@Override
	public boolean up() {
		InputStream connection = null;
		try {
		    connection = m_site.openStream();
		    return true;
		} catch (IOException e) {
		    // Apparently, something went wrong...
		    return false;
		}
		finally {
		    if (connection != null) {
		        try {
		            connection.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
}
