==================
js directory
==================

This directory centralizes all javascript scripts used throughout the web application.
The reason for not including the javascript scripts of each component under the component's
directory is that we would need more Struts actions as these files would not have been
publicly available. It's easier to put them all here and use module="" when including them
in JSPs. 