
public class ExactSolution {
    double p;
    double q;
    double y0;
    double yPrime0;

    // Constructor to initialize p, q, y0, yPrime0
    public ExactSolution(double p, double q, double y0, double yPrime0) {
        this.p = p;
        this.q = q;
        this.y0 = y0;
        this.yPrime0 = yPrime0;
    }

    // Solve the differential equation and return the exact solution as a numerical value at time t
    public double solve(double t) {
        double discriminant = (p * p) - (4 * q);
        if (discriminant > 0) {
            // Two distinct real roots
            double r1 = (-p + Math.sqrt(discriminant)) / 2;
            double r2 = (-p - Math.sqrt(discriminant)) / 2;
            double c1 = (yPrime0 - r2 * y0) / (r1 - r2);
            double c2 = y0 - c1;

            // Return the value of y(t)
            return c1 * Math.exp(r1 * t) + c2 * Math.exp(r2 * t);
        } else if (discriminant == 0) {
            // Repeated real root
            double r = -p / 2.0;
            double c1 = y0;
            double c2 = yPrime0 - (r * c1);

            // Return the value of y(t)
            return (c1 + c2 * t) * Math.exp(r * t);
        } else {
            // Complex roots
            double alpha = -p / 2.0;
            double beta = Math.sqrt(-discriminant) / 2.0;
            double c1 = y0;
            double c2 = (yPrime0 - alpha * c1) / beta;

            // Return the value of y(t)
            return Math.exp(alpha * t) * (c1 * Math.cos(beta * t) + c2 * Math.sin(beta * t));
        }
    }
}

