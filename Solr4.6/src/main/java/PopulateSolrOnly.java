import org.apache.commons.cli.*;
import org.apache.commons.lang3.RandomUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cahillt on 9/23/15.
 */
public class PopulateSolrOnly {


  public static void main(String[] args) {
    Option solrConnectionOpt = Option.builder("solrConnection")
            .required(true)
            .longOpt("solrConnection")
            .hasArg()
            .build();

    Option solrCollectionOpt = Option.builder("solrCollection")
            .required(false)
            .longOpt("solrCollection")
            .hasArg()
            .build();

    Options options = new Options();
    options.addOption(solrConnectionOpt);
    options.addOption(solrCollectionOpt);

    CommandLineParser parser = new DefaultParser();

    try {
      CommandLine cmd = parser.parse(options, args);
      String solrConnection = cmd.getOptionValue("solrConnection");
      String solrCollection = cmd.getOptionValue("solrCollection");

      InputStream firstNameInputStream = InitalPopulateMongo.class.getClassLoader().getResourceAsStream("firstNames.txt");
      List<String> firstNames = new ArrayList<>();
      InputStream lastNameInputStream = InitalPopulateMongo.class.getClassLoader().getResourceAsStream("lastNames.txt");
      List<String> lastNames = new ArrayList<>();

      BufferedReader br = new BufferedReader(new InputStreamReader(firstNameInputStream));
      String line;
      while ((line = br.readLine()) != null) {
        firstNames.add(line);
      }

      br = new BufferedReader(new InputStreamReader(lastNameInputStream));
      while ((line = br.readLine()) != null) {
        lastNames.add(line);
      }

//      LBHttpSolrServer lbHttpSolrServer = new LBHttpSolrServer(solrConnection.split(","));

//      int count = 0;
//
//      2609277063 phone
//      123456789 SSN


//      for (String fName : firstNames) {
//        for (String lName : lastNames) {
//          SolrInputDocument solrInputDocument = new SolrInputDocument();
//          String id = fName + lName;
//          solrInputDocument.addField("id", id);
//          solrInputDocument.addField("first_t", fName);
//          solrInputDocument.addField("last_t", lName);
//          lbHttpSolrServer.add(solrInputDocument);
//          count++;
//          if (count == 5000) {
//            System.out.println(id);
//            lbHttpSolrServer.commit();
//            count = 0;
//          }
//        }
//        lbHttpSolrServer.commit();
//      }

      List<Thread> threads = new ArrayList<>();
      int lasti = 0;
      for (int i = 10000; i < lastNames.size(); i += 10000) {
        List<String> subLast = lastNames.subList(lasti, i);
        lasti = i;

        PopulateThread populateThread = new PopulateThread(solrConnection, firstNames, subLast);
        populateThread.start();
        threads.add(populateThread);
      }

      for (Thread t : threads) {
        t.join();
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

  }
}
