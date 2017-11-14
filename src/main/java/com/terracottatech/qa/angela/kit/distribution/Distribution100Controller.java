package com.terracottatech.qa.angela.kit.distribution;

import com.terracottatech.qa.angela.kit.TerracottaServerInstance;
import com.terracottatech.qa.angela.tcconfig.TerracottaServer;
import com.terracottatech.qa.angela.topology.Topology;

import java.io.File;

/**
 * @author Aurelien Broszniowski
 */

public class Distribution100Controller extends DistributionController {


  public Distribution100Controller(final Distribution distribution) {
    super(distribution);
  }

  @Override
  public TerracottaServerInstance.TerracottaServerState start(final TerracottaServer terracottaServer, final Topology topology, final File installLocation) {
    System.out.println("start 10.0");
    return TerracottaServerInstance.TerracottaServerState.STARTED_AS_ACTIVE;
  }


  @Override
  public void stop() {
    System.out.println("stop 10.0");
  }
}