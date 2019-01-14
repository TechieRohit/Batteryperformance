package performance.cleaner.codebreaker.batteryperformance.Utilities;

/**
 * Created by J!MMY on 2/9/2017.
 */
public class HealthClass {
    public HealthClass() {

    }

    public String getHealth(int health) {
        String healthStatus = "Unknown";

        if (health == 7)
            healthStatus = "COLD";

        if (health == 4)
            healthStatus = "DEAD";

        if (health == 2)
            healthStatus = "GOOD";

        if (health == 3)
            healthStatus = "OVER HEAT";

        if (health == 3)
            healthStatus = "OVER VOLTAGE";

        if (health == 1)
            healthStatus = "UNKNOWN";

        if (health == 6)
            healthStatus = "UNSPECIFIED FAILURE";

        return healthStatus;
    }

}
