public class Project {
    public static void main(String[] args) {
        if (args.length != 8) {
            System.err.println("Please provide 8 arguments");
            return;

        }
        try {
            double p = Double.parseDouble(args[0]); // Coefficient of y'
            double q = Double.parseDouble(args[1]); // Coefficient of y
            double y0 = Double.parseDouble(args[2]); // Initial value for y(0)
            double yPrime0 = Double.parseDouble(args[3]); // Initial value of y'(0)
            double tEnd = Double.parseDouble(args[4]); // Time at which simulation ends
            double h = Double.parseDouble(args[5]); // Step size for numerical methods
            int Miny=Integer.parseInt(args[6]);
            int Maxy=Integer.parseInt(args[7]);
            double MaxErrorEuler;
            double MaxErrorMidpoint ;
            double MaxErrorRungeKutta;
            if((Maxy==0||Miny==0)){
                System.err.println("You cannot have both Maximum of Y and Mininium of Y being equal to zero");
                return;
            }
            if (h <= 0 || tEnd <= 0) {
                System.err.println("Step size and end time must be positive");
                return;
            }
            StdDraw.setCanvasSize(1200, 900);
            StdDraw.setXscale(0, tEnd);
            double minY = Miny; // Set to an estimated minimum Y value
            double maxY = Maxy;  // Set to an estimated maximum Y value
            StdDraw.setYscale(minY, maxY);
            addGridLines(tEnd, minY, maxY);
            StdDraw.setPenRadius(.005);
            StdDraw.enableDoubleBuffering();
            ExactSolution exactSolution = new ExactSolution(p, q, y0, yPrime0);

            double[] eulerY = new double[(int)(tEnd / h) + 1];
            double[] midpointY = new double[(int)(tEnd / h) + 1];
            double[] rungeKuttaY = new double[(int)(tEnd / h) + 1];
            double[] times = new double[(int)(tEnd / h) + 1];
            plotExactSolution(exactSolution, tEnd);
            MaxErrorEuler = plotEuler(p, q, y0, yPrime0, tEnd, h, exactSolution, eulerY, times);
            MaxErrorMidpoint = plotMidpoint(p, q, y0, yPrime0, tEnd, h, exactSolution, midpointY, times);
            MaxErrorRungeKutta = plotRungeKutta(p, q, y0, yPrime0, tEnd, h, exactSolution, rungeKuttaY, times);
            // Show the results after all plotting
            StdDraw.show();
            addLegend(tEnd, maxY, MaxErrorEuler, MaxErrorMidpoint, MaxErrorRungeKutta, exactSolution);

        } catch (NumberFormatException e) {
            System.err.println("One or more inputs are not valid numbers");
        }
    }

    private static void plotExactSolution(ExactSolution exactSolution, double tEnd) {
        StdDraw.setPenColor(StdDraw.RED);
        for (double t = 0; t <= tEnd; t += 0.00001) {
            double exactY = exactSolution.solve(t);
            StdDraw.point(t, exactY);
        }
        StdDraw.show();
    }

    private static double plotEuler(double p, double q, double y0, double yPrime0, double tEnd, double h, ExactSolution exactSolution, double[] eulerY, double[] times) {
        double y1 = y0;
        double y2 = yPrime0;
        double t = 0;
        double prevT = t;
        double prevY1 = y1;
        double MaxError = 0;
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(.005);

        int i = 0;
        while (t <= tEnd) {
            double exactY = exactSolution.solve(t);
            double Error = Math.abs(exactY - y1);
            MaxError = Math.max(MaxError, Error);
            eulerY[i] = y1;
            times[i] = t;

            if (i > 0) {
                StdDraw.line(prevT, prevY1, t, y1);
            }

            double y1Next = y1 + h * y2;
            double y2Next = y2 + h * (-p * y2 - q * y1);
            prevT = t;
            prevY1 = y1;
            t += h;
            y1 = y1Next;
            y2 = y2Next;
            StdDraw.point(t, y1);
            i++;
        }
        StdDraw.show();
        return MaxError;
    }

    private static double plotMidpoint(double p, double q, double y0, double yPrime0, double tEnd, double h, ExactSolution exactSolution, double[] midpointY, double[] times) {
        double y1 = y0;
        double y2 = yPrime0;
        double t = 0.0;
        double prevT = t;
        double prevY1 = y1;
        double MaxError = 0;
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(.005);

        int i = 0;
        while (t <= tEnd) {
            double exactY = exactSolution.solve(t);
            double Error = Math.abs(exactY - y1);
            MaxError = Math.max(MaxError, Error);
            midpointY[i] = y1;
            times[i] = t;

            double y1Mid = y1 + 0.5 * h * y2;
            double y2Mid = y2 + 0.5 * h * (-p * y2 - q * y1);
            double y1Next = y1 + h * y2Mid;
            double y2Next = y2 + h * (-p * y2Mid - q * y1Mid);

            if (i > 0) {
                StdDraw.line(prevT, prevY1, t, y1);
            }

            prevT = t;
            prevY1 = y1;
            y1 = y1Next;
            y2 = y2Next;
            t += h;
            StdDraw.point(t, y1);
            i++;
        }
        StdDraw.show();
        return MaxError;
    }

    private static double plotRungeKutta(double p, double q, double y0, double yPrime0, double tEnd, double h, ExactSolution exactSolution, double[] rungeKuttaY, double[] times) {
        double y1 = y0;
        double y2 = yPrime0;
        double t = 0.0;
        double prevT = t;
        double prevY1 = y1;
        double MaxError = 0;
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setPenRadius(.005);

        int i = 0;
        while (t <= tEnd) {
            double exactY = exactSolution.solve(t);
            double Error = Math.abs(exactY - y1);
            MaxError = Math.max(MaxError, Error);
            rungeKuttaY[i] = y1;
            times[i] = t;

            double k1y1 = h * y2;
            double k1y2 = h * (-p * y2 - q * y1);
            double k2y1 = h * (y2 + 0.5 * k1y2);
            double k2y2 = h * (-p * (y2 + 0.5 * k1y2) - q * (y1 + 0.5 * k1y1));
            double k3y1 = h * (y2 + 0.5 * k2y2);
            double k3y2 = h * (-p * (y2 + 0.5 * k2y2) - q * (y1 + 0.5 * k2y1));
            double k4y1 = h * (y2 + k3y2);
            double k4y2 = h * (-p * (y2 + k3y2) - q * (y1 + k3y1));

            y1 += (k1y1 + 2 * k2y1 + 2 * k3y1 + k4y1) / 6.0;
            y2 += (k1y2 + 2 * k2y2 + 2 * k3y2 + k4y2) / 6.0;

            if (i > 0) {
                StdDraw.line(prevT, prevY1, t, y1);
            }

            prevT = t;
            prevY1 = y1;
            StdDraw.point(t, y1);
            t += h;
            i++;
        }
        StdDraw.show();
        return MaxError;
    }

    private static void addGridLines(double tEnd, double minY, double maxY) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (double t = 0; t <= tEnd; t += tEnd / 20) {
            StdDraw.line(t, minY, t, maxY);
        }
        for (double y = minY; y <= maxY; y += (maxY - minY) / 20) {
            StdDraw.line(0, y, tEnd, y);
        }
    }


    private static void addLegend(double tEnd, double maxY, double MaxErrorEuler, double MaxMidpoint, double MaxErrorRungeKutta,ExactSolution exactSolution) {
        double exactValueAtTEnd = exactSolution.solve(tEnd);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(tEnd -tEnd/2, maxY - maxY/2.5, "Exact Solution ("+ exactValueAtTEnd + ")");

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(tEnd -tEnd/2, maxY - maxY/3, "Euler (Error: " + MaxErrorEuler + ")");

        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text(tEnd -tEnd/2, maxY - maxY/4, "Midpoint (Error: " + MaxMidpoint + ")");

        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.text(tEnd -tEnd/2, maxY - maxY/5, "Runge-Kutta (Error: " + MaxErrorRungeKutta + ")");
        StdDraw.show();
    }
}
