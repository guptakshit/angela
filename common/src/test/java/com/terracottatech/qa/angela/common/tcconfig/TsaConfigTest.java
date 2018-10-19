package com.terracottatech.qa.angela.common.tcconfig;

import org.junit.Test;

import com.terracottatech.qa.angela.common.topology.Version;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.terracottatech.qa.angela.common.tcconfig.TsaStripeConfig.stripe;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Aurelien Broszniowski
 */

public class TsaConfigTest {

  @Test
  public void testAddServer() {
    TsaConfig tsaConfig = TsaConfig.tsaConfig(Version.version("10.0.0.0.0"),
        stripe("host1", "host2").offheap("primary", "50", "GB").data("name1", "root1")
    );
    final List<TcConfig> tcConfigs = tsaConfig.buildTcConfigs();
    assertThat(tcConfigs.size(), equalTo(1));
    final Collection<TerracottaServer> servers = tcConfigs.get(0).getServers();
    assertThat(servers.size(), equalTo(2));
    final Iterator<TerracottaServer> iterator = servers.iterator();
    assertThat(iterator.next().getServerSymbolicName().getSymbolicName(), is("Server1-1"));
    assertThat(iterator.next().getServerSymbolicName().getSymbolicName(), is("Server1-2"));
  }

  @Test
  public void TestWithouttimesParam() {
    TsaConfig tsaConfig = TsaConfig.tsaConfig(Version.version("10.0.0.0.0"),
        stripe("host1", "host2").offheap("primary", "50", "GB")
    );
    final List<TcConfig> tcConfigs = tsaConfig.buildTcConfigs();
    assertThat(tcConfigs.size(), equalTo(1));
  }

  @Test
  public void TestWithTimesParam() {
    TsaConfig tsaConfig = TsaConfig.tsaConfig(Version.version("10.0.0.0.0"),
        stripe("host1", "host2").offheap("primary", "50", "GB").data("data", "root1"),
        stripe("host1", "host2").offheap("primary", "50", "GB")
    );
    final List<TcConfig> tcConfigs = tsaConfig.buildTcConfigs();
    assertThat(tcConfigs.size(), equalTo(2));
  }
}
