package com.terracottatech.qa.angela.agent.kit;

import com.terracottatech.qa.angela.common.TerracottaServerInstance;
import com.terracottatech.qa.angela.common.distribution.Distribution;
import com.terracottatech.qa.angela.common.tcconfig.License;
import com.terracottatech.qa.angela.common.tcconfig.SecurityRootDirectory;
import com.terracottatech.qa.angela.common.tcconfig.ServerSymbolicName;
import com.terracottatech.qa.angela.common.tcconfig.TcConfig;
import com.terracottatech.qa.angela.common.tcconfig.TerracottaServer;
import com.terracottatech.qa.angela.common.topology.Topology;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * Installation instance of a Terracotta server
 */
public class TerracottaInstall {

  private final File rootInstallLocation;
  private final Map<ServerSymbolicName, TerracottaServerInstance> terracottaServerInstances = new HashMap<>();

  public TerracottaInstall(File rootInstallLocation) {
    this.rootInstallLocation = rootInstallLocation;
  }

  public TerracottaServerInstance getTerracottaServerInstance(TerracottaServer terracottaServer) {
    synchronized (terracottaServerInstances) {
      return terracottaServerInstances.get(terracottaServer.getServerSymbolicName());
    }
  }

  public File getInstallLocation(TerracottaServer terracottaServer) {
    synchronized (terracottaServerInstances) {
      return terracottaServerInstances.get(terracottaServer.getServerSymbolicName()).getInstallLocation();
    }
  }

  public File getLicenseFileLocation(TerracottaServer terracottaServer) {
    synchronized (terracottaServerInstances) {
      return terracottaServerInstances.get(terracottaServer.getServerSymbolicName()).getLicenseFileLocation();
    }
  }

  public void addServer(TerracottaServer terracottaServer, SecurityRootDirectory securityRootDirectory, File installLocation, License license, Distribution distribution, Topology topology) {
    synchronized (terracottaServerInstances) {
      int stripeId = topology.findStripeIdOf(terracottaServer.getServerSymbolicName());
      boolean netDisruptionEnabled = topology.isNetDisruptionEnabled();
      TcConfig tcConfig = topology.findTcConfigOf(terracottaServer.getServerSymbolicName());
      terracottaServerInstances.put(terracottaServer.getServerSymbolicName(), new TerracottaServerInstance(terracottaServer.getServerSymbolicName(), installLocation, tcConfig, netDisruptionEnabled, stripeId, securityRootDirectory, license, distribution));
    }
  }

  public int removeServer(TerracottaServer terracottaServer) {
    synchronized (terracottaServerInstances) {
      TerracottaServerInstance instance = terracottaServerInstances.remove(terracottaServer.getServerSymbolicName());
      if (instance != null){
        instance.close();
      }
      return terracottaServerInstances.size();
    }
  }

  public int terracottaServerInstanceCount() {
    synchronized (terracottaServerInstances) {
      return terracottaServerInstances.size();
    }
  }

  public boolean installed(Distribution distribution) {
    synchronized (terracottaServerInstances) {
      return terracottaServerInstances.values().stream().anyMatch(tsi -> tsi.getDistribution().equals(distribution));
    }
  }

  public File installLocation(Distribution distribution) {
    synchronized (terracottaServerInstances) {
      TerracottaServerInstance terracottaServerInstance = terracottaServerInstances.values().stream()
          .filter(tsi -> tsi.getDistribution().equals(distribution))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Distribution not installed : " + distribution));
      return terracottaServerInstance.getInstallLocation();
    }
  }

  public File getRootInstallLocation() {
    return rootInstallLocation;
  }
}
