package de.madetv1280.craftD3K.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionUtil {
    public static int compareVersions(String versionLatest, String versionCurrent) {
        try {
            if (versionLatest == null || versionCurrent == null) {
                return 99;
            }

            Pattern versionPattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+)");

            Matcher latestMatcher = versionPattern.matcher(versionLatest);
            Matcher currentMatcher = versionPattern.matcher(versionCurrent);

            if (!latestMatcher.find() || !currentMatcher.find()) {
                return 99;
            }

            versionLatest = latestMatcher.group(1);
            versionCurrent = currentMatcher.group(1);


            String[] partsLatest = versionLatest.split("\\.");
            String[] partsCurrent = versionCurrent.split("\\.");

            int length = Math.max(partsLatest.length, partsCurrent.length);

            for (int i = 0; i < length; i++) {
                int numLatest = 0;
                int numCurrent = 0;

                try {
                    numLatest = i < partsLatest.length ? Integer.parseInt(partsLatest[i].trim()) : 0;
                } catch (NumberFormatException nfe) {
                    return 99;
                }

                try {
                    numCurrent = i < partsCurrent.length ? Integer.parseInt(partsCurrent[i].trim()) : 0;
                } catch (NumberFormatException nfe) {
                    return 99;
                }

                if (numLatest > numCurrent) return -1;
                if (numLatest < numCurrent) return 1;
            }

            return 0;

        } catch (Exception e) {
            return 99;
        }
    }
}
