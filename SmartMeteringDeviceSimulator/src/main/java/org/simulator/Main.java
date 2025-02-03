package org.simulator;

public class Main {
    public static void main(String[] args) {

        Simulator simulator = new Simulator(args[0]);

        simulator.setup();
        simulator.run();
        simulator.shutdown();

    }
}
