JMeter with WebDriver
=====================

When load, performance testing is done, the emphasis is usually on the load/capacity that a server or site can handle.  The end user is usually simulated (ie. not using a real browser), and the load time may not represent what a real user might experience.  This project is an attempt to do load testing using a real browser on the actual site/server(s).

To do so we will use [JMeter](http://jmeter.apache.org) to simulate the creation of the load and [WebDriver](http://seleniumhq.org) to control the browser.  To script the actions for the browser, we currently use Javascript (provided by [Rhino](http://www.mozilla.org/rhino/)).  

**This is a spike to assess the viability of using a Web Browser as a JMeter sampler**

See the Getting Started section to try out the spike.

Getting Started
---------------

### Start JMeter
Run the following commands to show the GUI

    ant download_jars
    ant run_gui

If everything works, then the JMeter GUI will be shown.

![JMeter GUI](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter.png)

### Create a Web Sampler
In the GUI, right click on the test plan to create a **Thread Group**.

![JMeter Thread Group Popup](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-thread-group-popup.png)

Once created, the **Thread Group** contents should be displayed.

![JMeter Thread Group Display](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-thread-group.png)

Then right click on the Thread Group to add a **Web Browser** Sampler.

![JMeter Web Sampler Popup](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-web-sampler-popup.png)

The **Web Browser** Sampler contents should be displayed.

![JMeter Web Sampler Display](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-web-sampler.png)

## Create a Test script
In the **Script** pane enter the following commands to change the URL for the Web Browser.

    browser.get('http://www.google.com');

![JMeter Web Sampler Script](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-web-sampler-script.png)

Run the test by pressing the **Start** button.  The browser should appear and it should go to Google and then finish.
