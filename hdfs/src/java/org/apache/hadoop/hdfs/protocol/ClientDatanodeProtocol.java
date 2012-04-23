/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.protocol;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hdfs.security.token.block.BlockTokenSelector;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.security.token.TokenInfo;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.security.token.block.BlockTokenIdentifier;
import org.apache.hadoop.security.KerberosInfo;
import org.apache.hadoop.security.token.Token;

/** An client-datanode protocol for block recovery
 */
@InterfaceAudience.Private
@InterfaceStability.Evolving
@KerberosInfo(
    serverPrincipal = DFSConfigKeys.DFS_DATANODE_USER_NAME_KEY)
@TokenInfo(BlockTokenSelector.class)
public interface ClientDatanodeProtocol extends VersionedProtocol {
  public static final Log LOG = LogFactory.getLog(ClientDatanodeProtocol.class);

  /**
   * 7: added getBlockLocalPathInfo.
   */
  public static final long versionID = 7L;

  /** Return the visible length of a replica. */
  long getReplicaVisibleLength(Block b) throws IOException;

  /**
   * Retrieves the path names of the block file and metadata file stored on the
   * local file system.
   * 
   * In order for this method to work, one of the following should be satisfied:
   * <ul>
   * <li>
   * The client user must be configured at the datanode to be able to use this
   * method.</li>
   * <li>
   * When security is enabled, kerberos authentication must be used to connect
   * to the datanode.</li>
   * </ul>
   * 
   * @param block
   *          the specified block on the local datanode
   * @param token 
   *          the block access token.
   * @return the BlockLocalPathInfo of a block
   * @throws IOException
   *           on error
   */
  BlockLocalPathInfo getBlockLocalPathInfo(Block block,
      Token<BlockTokenIdentifier> token) throws IOException;   
}
