package de.madetv1280.craftD3K.managers;

import de.madetv1280.craftD3K.constants.Integration;
import de.madetv1280.craftD3K.constants.keys.messages.InternalMsg;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.madetv1280.craftD3K.util.PluginLogger.log;

import org.json.*;

public class UpdateChecker {
    public static void checkForUpdates(Plugin plugin) {

        String latestVersion = getLatestVersion(plugin);
        String currentVersion = plugin.getDescription().getVersion();


        switch (compareVersions(latestVersion, currentVersion, plugin)) {
            case 1:
                log().info(InternalMsg.RUNNING_NEWER_THAN_AVAILABLE,
                        currentVersion, latestVersion);
                break;
            case -1:
                log().info(InternalMsg.RUNNING_OLDER_VERSION,
                        currentVersion, latestVersion);
                break;
            case 0:
                log().info(InternalMsg.RUNNING_LATEST_VERSION);
                break;
            case 99:
                log().info(InternalMsg.FAILED_CHECK_UPDATE_UNKNOW_VERSION_FORMAT);
                break;
        }

    }

    private static int compareVersions(String versionLatest, String versionCurrent, Plugin plugin) {
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


    private static String getLatestVersion(Plugin plugin) {
        try {
            URL url = new URL(String.format(Integration.MODRINTH_API_URL, Integration.MODRINTH_PROJECT_ID));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONArray versions = new JSONArray(sb.toString());

            JSONObject latest = versions.getJSONObject(0);
            String latestVersion = latest.getString("name");

            return latestVersion;
        } catch (Exception e) {
            log().info(InternalMsg.FAILED_CHECK_UPDATE_NETWORK_CONNTECTION);

            return null;
        }

    }
}
