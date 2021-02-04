/* The class that handles SymmetricDS. Adapted from:
    https://www.symmetricds.org/doc/3.12/html/user-guide.html#_node_properties_file
 */
package ampm;

import org.jumpmind.symmetric.SymmetricWebServer;

public class SymmetricDS {

    public static void main(String[] args) throws Exception {

        SymmetricWebServer node = new SymmetricWebServer(
                                   "classpath://my-application.properties", "conf/web_dir");

                                  
        // this will create the database, sync triggers, start jobs running
        node.start(8080);

        // this will stop the node
        node.stop();
    }
}