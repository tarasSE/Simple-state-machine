package simple.state.machine;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static simple.state.machine.RunnerState.FINISHED;
import static simple.state.machine.RunnerState.LEFT_THE_RACE;

public class Race {

    private Runner selectedRunner;
    private boolean inProgress = true;

    public static void main(String[] args) {
        var race = new Race();
        race.start();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            var runners = List.of(
                    new Runner("Rabbit", 100, 30, 8, 8, 16),
                    new Runner("Turtle", 100, 10, 100, 10, 10)
            );

            var commands = List.of("run", "stop", "leave");

            System.out.println("Choose your runner: ");
            printIndexed(runners);

            var choice = 0;
            while (true) {
                choice = scanner.nextInt() - 1;
                if (choice < 0 || choice > runners.size() - 1) System.out.println("Please enter correct number");
                else break;
            }

            selectedRunner = runners.get(choice);

            System.out.println("\nYour runner is " + selectedRunner.toString());

            System.out.println("\nRace starting in ");
            countToStart(3);

            System.out.println("\nUse next commands to command your runner: ");
            printIndexed(commands);

            var timeCounter = 0;
            var command = 0;
            while (inProgress) {
                command = scanner.nextInt() - 1;
                if (command < 0 || command > commands.size() - 1) System.out.println("Please enter correct number");

                switch (commands.get(command)) {
                    case "run" -> selectedRunner.run();
                    case "stop" -> selectedRunner.stop();
                    case "leave" -> {
                        selectedRunner.leave();
                        inProgress = false;
                    }
                }

                timeCounter += 1;

                System.out.println("\nTime: " + timeCounter);
                System.out.println(selectedRunner);

                inProgress = !EnumSet.of(FINISHED, LEFT_THE_RACE).contains(selectedRunner.getState());
            }

            System.out.println("The race ended with result: " + selectedRunner.getState());
            System.out.println("Time: " + timeCounter);
        }
    }

    private void countToStart(final int number) {
        IntStream.range(1, number + 1).boxed().sorted(Collections.reverseOrder())
                .map(i -> i + "... ").forEach(s -> {
                    try {
                        System.out.print(s);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void printIndexed(List<?> items) {
        IntStream.range(0, items.size())
                .mapToObj(i -> i + 1 + ". " + items.get(i).toString())
                .forEach(System.out::println);
    }
}
