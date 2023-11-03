package edu.redwoods.cis18.tomcatpi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.URISyntaxException;

@RestController
public class GetWebUI {
    @GetMapping("/")
    public String getIndex() throws IOException, URISyntaxException {
        //TODO: Change directory from hard-coded to project local or embedded resource.
        InputStream is = getClass().getClassLoader().getResourceAsStream("ui.html");
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        return sb.toString();
    }
}
