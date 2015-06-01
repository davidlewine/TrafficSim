# TrafficSim
current branch is traffic_fxml

Traffic grid simulation

This simulates cars moving through a grid of streets.  Cars accelerate and deccelerate depending on how close they are to cars in front, how close they are to a stoplight, and the color of the stoplight.

I was interested in experimenting to see the effect on traffic flow of different timing schemes.  What schemes lead to gridllock depending on traffic volume?  What schemes create the lowest average travel time across town? etc.

Light timing can be changed using the gui. CURRENTLY YOU MUST PRESS ENTER TO HAVE CHANGES TAKE EFFECT. 

"to" input field controls the timing offset for each intersection.  Use this to synchronize lights along a street(n/s) or avenue (e/w).

"nsr" sets the duration of the n/s red light.
"ewr" sets the duration of the e/w red light.
changing either one of these will automatically adjust the other light at the intersection.
The length of the n/s(e/w) green light is equal to the length of the e/w(n/s) red light minus the standard yellow light length.

I experimented with a more graphical interface.  In particular an overlay of bars showing the red, green and yellow light length for each light (one bar with a red, green and yellow segment).  The segments could be dragged to change the light timing, and a pointer on the bar showed the intersection timing offset.  

I abandoned this idea because the way I implemneted it was too imprecise and dragging took longer than typing when changing many intersections.  I also thought it was not too hard to see the pattern of timing offsets from the table. I still would like to have a more graphical interface, maybe just for the timing offsets.




