package de.madetv1280.craftD3K.managers;

import de.madetv1280.craftD3K.constants.Integration;
import de.madetv1280.craftD3K.constants.keys.messages.InternalMsg;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static de.madetv1280.craftD3K.util.PluginLogger.log;
import static de.madetv1280.craftD3K.util.VersionUtil.*;

import org.json.*;

public class UpdateChecker {
    public static void checkForUpdates(Plugin plugin) {

        String latestVersion = getLatestVersion();
        String currentVersion = plugin.getDescription().getVersion();


        switch (compareVersions(latestVersion, currentVersion)) {
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


    private static String getLatestVersion() {
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
