package com.terracottatech.qa.angela;

import com.terracottatech.qa.angela.client.ClusterFactory;
import com.terracottatech.qa.angela.client.Tsa;
import com.terracottatech.qa.angela.common.tcconfig.License;
import com.terracottatech.qa.angela.common.topology.LicenseType;
import com.terracottatech.qa.angela.common.topology.PackageType;
import com.terracottatech.qa.angela.common.topology.Topology;
import org.junit.Test;

import static com.terracottatech.qa.angela.common.distribution.Distribution.distribution;
import static com.terracottatech.qa.angela.common.tcconfig.TcConfig.tcConfig;
import static com.terracottatech.qa.angela.common.topology.Version.version;

/**
 * @author Aurelien Broszniowski
 */

public class InstallTest {

  @Test
  public void testLocallInstall() throws Exception {
    Topology topology = new Topology(distribution(version("10.2.0.0.53"), PackageType.KIT, LicenseType.TC_DB),
        tcConfig(version("10.2.0.0.53"), "/terracotta/10/tc-config-a.xml"));
    License license = new License("/terracotta/10/TerracottaDB101_license.xml");

    try (ClusterFactory factory = new ClusterFactory("TcDBTest::testConnection")) {
      Tsa tsa = factory.tsa(topology, license);
      tsa.installAll();
      tsa.startAll();
      tsa.licenseAll();
    }
  }

}
