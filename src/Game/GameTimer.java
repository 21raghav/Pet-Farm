package Game;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class GameTimer {
    public GameTimer() {
        // Record the start time
        Instant start = Instant.now();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Record the end time
            Instant end = Instant.now();

            // Calculate the elapsed time
            Duration elapsed = Duration.between(start, end);

            long totalSeconds = elapsed.getSeconds();
            long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;

            Map<String,String> timeData = DataManager.loadState("", "restrictions.csv");
            // Format as HH:MM
            String formattedTime = String.format("%02d:%02d", hours, minutes);

            System.out.print(formattedTime);


            String total = timeData.getOrDefault("Total", "00:00");

            String[] parts1 = total.split(":");
            String[] parts2 = formattedTime.split(":");

            int hours1 = Integer.parseInt(parts1[0]);
            int minutes1 = Integer.parseInt(parts1[1]);
            int hours2 = Integer.parseInt(parts2[0]);
            int minutes2 = Integer.parseInt(parts2[1]);

            // Add hours and minutes separately
            int totalMinutes = minutes1 + minutes2;
            int totalHours = hours1 + hours2 + (totalMinutes / 60); // Carry over minutes to hours
            totalMinutes %= 60; // Remainder minutes after carrying over

            String totalTime = String.format("%02d:%02d", totalHours, totalMinutes);
            timeData.put("Total", totalTime);

            int timesPlayed = Integer.parseInt(timeData.get("Times"));
            timesPlayed++;

            String[] parts = totalTime.split(":");
            int totalTimeHours = Integer.parseInt(parts[0]);
            int totalTimeMinutes = Integer.parseInt(parts[1]);

            // Convert total time to minutes
            int totalTotalTime = (totalTimeHours * 60) + totalTimeMinutes;

            // Divide by the divisor
            int dividedMinutes = totalTotalTime / timesPlayed;

            // Convert back to hours and minutes
            int resultHours = dividedMinutes / 60;
            int resultMinutes = dividedMinutes % 60;

            // Format as HH:MM
            timeData.put("Average",String.format("%02d:%02d", resultHours, resultMinutes));

            timeData.put("Times", String.valueOf(timesPlayed));


            DataManager.saveState("restrictions.csv", timeData);
        }));

    }
}
