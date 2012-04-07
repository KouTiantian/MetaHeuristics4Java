package de.mh4j.solver.simulatedAnnealing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mh4j.solver.Solution;

public abstract class AbstractCoolingScheme<GenericSolutionType extends Solution> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Determines the length of the markov chain. Determines how long (in number
     * of steps) the algorithm runs until the current acceptance probability is
     * decreased.
     */
    protected int equilibrium;
    protected double temperature;

    private int currentEquilbrium;

    public AbstractCoolingScheme() {
        initialize();
    }

    private void initialize() {
        temperature = getInitialTemperature();
        equilibrium = getInitialEquilibrium();
    }

    public void updateTemperature() {
        if (currentEquilbrium >= equilibrium) {
            currentEquilbrium = 0;
            decreaseTemperature();
            log.trace("Temperature has been decreased to {}", temperature);
        }
        currentEquilbrium++;
    }

    public double getAcceptanceProbability(GenericSolutionType currentSolution, GenericSolutionType neighborSolution) {
        int currentCosts = currentSolution.getCosts();
        int neighborCosts = neighborSolution.getCosts();
        return Math.exp(-Math.abs(neighborCosts - currentCosts) / temperature);
    }

    protected abstract double getInitialTemperature();

    protected abstract int getInitialEquilibrium();

    public abstract void decreaseTemperature();
}
