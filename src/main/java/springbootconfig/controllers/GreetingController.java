package springbootconfig.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootconfig.configuration.DbConfiguration;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    //With a default value
    @Value("${my.greeting:Default Value}")
    private String greetingMessage;

    //For a comma separated list
    @Value("${listValues}")
    private List<String> listOfValues;

    //For multiple key-value pairs
    @Value("#{${db.connection}}")
    private Map<String,String> dbParameters;

    @Autowired
    private DbConfiguration dbConfiguration;

    @Autowired
    private Environment environment;

    @GetMapping("/greeting")
    public String greeting(){
        for(Map.Entry<String,String> dbParameters:dbParameters.entrySet()){
            System.out.println("Key: "+dbParameters.getKey()+" ,Value: "+dbParameters.getValue());
        }
        return greetingMessage+" , "+listOfValues+" , "+dbParameters;
    }

    @GetMapping("/dbDetails")
    public String getDbDetails(){
        return  dbConfiguration.getConnection()+" , "+
                dbConfiguration.getHost()+" , "+
                dbConfiguration.getPort();
    }

    @GetMapping("/envDetails")
    public String[] getEnvDetails(){
        return environment.getActiveProfiles();
    }
}
