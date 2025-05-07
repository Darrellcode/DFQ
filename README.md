General Form:
This program is designed to solve second-order linear homogeneous ordinary differential equations (ODEs) of the form:
y”+py’+qy= 0 
y”-py’+qy= 0 
y"+py’-qy= 0 
y”-py’-qy= 0 

Before using the program, ensure that your equation is in one of the above forms. If the coefficient of  y” is not 1, you must divide or multiply the entire equation by the appropriate factor to get it into the required form.

You will also need to provide initial conditions for y(0)  and y'(0).

Command-Line Arguments:
The program requires six command-line arguments in the following order:

1. p: The coefficient of y’.
2. q: The coefficient of y.
3. Initial value for y(0).
4. Initial value for y’(0).
5. Time at which the simulation ends.
6. Step size for the numerical methods.

In addition, you will need to specify the minimum and maximum values for the y-axis in the graph:

7. MinY: The minimum y-value for the graph.
8. MaxY: The maximum y-value for the graph.

Adjusting the Graph's Y-Axis:
- Initial y-range: It's recommended to start with MinY = -1 and MaxY = 1 as default values. You can adjust these values based on the behavior of your equation and the results of the simulation.
- If your solution grows or shrinks rapidly, adjusting these values may be necessary to ensure the graph is properly scaled.

Important Notes:
- The program assumes the equation is in the form y” + py’ + qy = 0, so if your equation has negative coefficients (e.g.,y” - py’ + qy = 0), you should treat it as y" + (-p)y’ + (-q) y = 0 .

Running the Program:
1. Ensure that your second-order ODE is in one of the acceptable forms and that you have initial conditions for y(0) and y'(0).
2. Provide the required arguments in the correct order when executing the program.
3. After clicking "Apply", the program will simulate the ODE's solution over the specified time interval using various numerical methods (Euler, Midpoint, and Runge-Kutta) and plot the results.
4. The program will display a graph showing the exact solution (in red) and the numerical solutions for each method (in blue, green, and magenta). It will also display the maximum error for each method in the legend.
5. Last but not least if you have an extremely big graph that goes off to infinity the graph shall show no values, it will almost seemingly truncate itself. Furthermore, you graph will come off as clunky and unfinished in STDdraw, then you will get an error. To fix this you must choose a T that is smaller.

