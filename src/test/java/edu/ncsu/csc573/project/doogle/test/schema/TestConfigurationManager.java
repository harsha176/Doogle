/**
 * 
 */
package edu.ncsu.csc573.project.doogle.test.schema;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc573.project.common.ConfigurationManager;

/**
 * @author doogle-dev
 *
 */
public class TestConfigurationManager {

	@Test 
	public void testPublishDirectoryUpdate() throws Exception {
		File initialDir = ConfigurationManager.getInstance().getPublishDirectory();
		Assert.assertNotNull(initialDir);
		
		String newFolder = "/home";
		File newDir = new File(newFolder);
		ConfigurationManager.getInstance().setPublishDirectory(newDir);
		Assert.assertEquals(newFolder, ConfigurationManager.getInstance().getPublishDirectory().getAbsolutePath());
		ConfigurationManager.getInstance().setPublishDirectory(initialDir);
		Assert.assertEquals(initialDir.getAbsolutePath(), ConfigurationManager.getInstance().getPublishDirectory().getAbsolutePath());
	}

}
