/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ampm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.jumpmind.symmetric.ClientSymmetricEngine;

/**
 *
 * @author benab
 */
public class SymmetricClientNode {
	private ClientSymmetricEngine cEngine;
	private File propFile;


	public SymmetricClientNode(File file) throws FileNotFoundException, IOException {
		propFile = file;
		Properties propertiesFile = new Properties();
		propertiesFile.load(new FileReader(propFile));
		cEngine = new ClientSymmetricEngine(propertiesFile, true);
		getcEngine().openRegistration("client", "001");// client is the name of the node group and 001 is the ID
		getcEngine().setup();
		getcEngine().start();
	}

	public ClientSymmetricEngine getcEngine() {
		return cEngine;
	}

	public void setcEngine(ClientSymmetricEngine cEngine) {
		this.cEngine = cEngine;
	}
}