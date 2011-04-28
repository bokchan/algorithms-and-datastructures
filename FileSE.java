

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileSE implements SEServer.SearchService {
    ArrayList<String> lines = new ArrayList<String>();

    void indexFile(String fileName) throws IOException {
        // We assume file is in UTF-8 text format.
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        while (true) {
            String s = r.readLine();
            if (s == null) break; // no more lines
            lines.add(s);
        }
    }

    public String search(String queryString) {
        int line = Integer.parseInt(queryString);
        return "<p><b>Line " + line + ":</b> " + lines.get(line) + "</p>"; 
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Please give a single file name as argument.");
            System.exit(2);
        }
        String fileName = args[0];

        FileSE engine = new FileSE();
        engine.indexFile(fileName);

        SEServer server = new SEServer(8888, engine);
        server.run();
    }
}