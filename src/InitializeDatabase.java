import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

import org.json.JSONObject;

public class InitializeDatabase {

	protected static void createTables() {
		Connection databaseConnection;
	}
	
	private static JSONObject[] getTeams(int page) throws IOException {
		URL url = new URL("http://www.thebluealliance.com/api/v2/teams/" + page);
		HttpURLConnection blueAllianceConnection = (HttpURLConnection) url
				.openConnection();
		blueAllianceConnection.setRequestMethod("GET");
		blueAllianceConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
		blueAllianceConnection.setRequestProperty("X-TBA-App-Id",
				"1619:scouting:1.0");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				blueAllianceConnection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			stringBuilder.append(currentLine);
		}
		reader.close();
		String JSON = stringBuilder.toString();
		JSON = JSON.substring(1, JSON.length() - 1);
		String[] teamsJSON = JSON.split("},");
		JSONObject[] teams = new JSONObject[teamsJSON.length];
		for (int i = 0; i < teamsJSON.length; i++) {
			teams[i] = new JSONObject(teamsJSON[i] + "}");
		}
		return teams;
	}

}
