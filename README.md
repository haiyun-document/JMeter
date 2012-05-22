The problem
===========
A large part of performance testing, up to this point, has been on the server side of things.  However, with the advancement of technology, HTML5, JS and CSS improvements, more and more logic, behaviour, etc have been pushed down to the client.  Things that add to the overall browser execution time may include:

1. Client-side Javascript execution - eg. AJAX, JS templates
1. CSS transforms - eg. 2D, 3D, matrix transforms.
1. 3rd party plugins - eg. Facebook like, Double click ads, site analytics, etc

All these things add to the overall browser execution time, and this project aims to measure the time it takes to complete rendering all this content.  

**Note:** This project is **not** intended to replace the HTTP Samplers included in JMeter, rather it is meant to compliment them by measuring the time it takes the end user load time.  The use case would still be to create the HTTP Samplers with 50, 100, etc virtual users to create a load on the server side, but at the same time we would also execute a separate JMeter test suite comprising of clients only to assess the performance on the client side.  As mentioned, these samplers are complimentary.

Proposed  solution - JMeter with WebDriver
==========================================
The proposed solution here is to use [JMeter](http://jmeter.apache.org) to simulate the creation of the load and [WebDriver](http://seleniumhq.org) to script the browser interaction with the server.  To script the actions for the browser, we currently use Javascript (provided by [Rhino](http://www.mozilla.org/rhino/)).  

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

Run the test by pressing the **Start** button.  The browser should appear and it should go to Google as shown below.

![JMeter Web Sampler Browser](https://github.com/cplim/JMeter/raw/master/gh-docs/images/JMeter-web-sampler-browser.png)
