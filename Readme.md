What's this?
============

A very simple service that gives you notification of the status of a set of website, which you can overlay on top of a website. It is intended as a demo of the [ManagedServiceFactory](http://www.osgi.org/javadoc/r4v42/org/osgi/service/cm/ManagedServiceFactory.html) mechanism in OSGi.

How do I use it?
================

Installing
----------
Drop the bundle into an OSGi framework, which contains the following services,

  * An OSGi compendium bundle,
  * The Apache Felix Dependency Manager (I've put a [pre-built one](http://dl.dropbox.com/u/2438787/www/2010-12/org.apache.felix.dependencymanager-3.0.0-SNAPSHOT.jar) up)
  * A Configuration Admin service (I use the Apache Felix one)
  * An OSGi HTTPService and a Whiteboard service for that (again, I used the Apache Felix ones)
  * A metatype service

The Apache Felix bundles can be downloaded from [their download site](http://felix.apache.org/site/downloads.cgi).

Additionally, you might want to install the Apache Felix Webconsole, you can configure our service.

Configuring
-----------

The bundle contains MetaType information, so you can create factory configurations using Webconsole. No matter which mechanism you choose, you can create factory configurations for `net.luminis.websitewatcher.factory`, containing a `url`.

The project
===========
The project is an Eclipse project, based on [bndtools](http://njbartlett.name/bndtools.html). Apart from what is shipped with bndtools by default, you will need

  * the Apache Felix Dependency Manager (see above), and
  * Apache Felix FileInstall, which we use for setting configurations. You can also remove it, and only use WebConsole.
