package network;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

//quoting edx shamelessly quoting: http://zetcode.com/articles/javareadwebpage/
//JSON parsing inspired by http://theoryapp.com/parse-json-in-java/
//minor adjustments
public class JokeFetcher {

    public String fetchJoke()  {

        try {
            URL url = new URL("https://official-joke-api.appspot.com/jokes/programming/random");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            JSONObject obj = new JSONObject(sb.toString().substring(1, sb.toString().length() - 1));
            String setup = obj.getString("setup");
            String punchline = obj.getString("punchline");
            br.close();

            return setup + "\n" + punchline;

        } catch (IOException e) {
            return "Something went wrong!";
        }
    }
}

