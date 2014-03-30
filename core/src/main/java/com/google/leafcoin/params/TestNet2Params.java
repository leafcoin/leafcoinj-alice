/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.leafcoin.params;

import com.google.leafcoin.core.NetworkParameters;
import com.google.leafcoin.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the old version 2 testnet. This is not useful to you - it exists only because some unit tests are
 * based on it.
 */
public class TestNet2Params extends NetworkParameters {
    public TestNet2Params() {
        super();
        id = ID_TESTNET;
        interval = INTERVAL;
        newInterval = INTERVAL_NEW;
        targetTimespan = TARGET_TIMESPAN;
        newTargetTimespan = TARGET_TIMESPAN_NEW;
        packetMagic = 0xbbaaaaaa;
        port = 33813;
        addressHeader = 111;
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        acceptableAddressCodes = new int[] { 111 };
        dumpedPrivateKeyHeader = 223;
        genesisBlock.setTime(1388880557L);
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setNonce(387006691);
        spendableCoinbaseDepth = 100;
        subsidyDecreaseBlockCount = 100000;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("b78197f0e175697646db1f738edc1ffdcb30588ebe70e7e16026489076577061"));
        dnsSeeds = new String[] {
                "seed.leafco.in",
                "seed2.leafco.in",
                "seed3.leafco.in",
                "seed4.leafco.in",
                "seed5.leafco.in",
                "seed6.leafco.in",
                "leafcoin.mercuriusgids.nl"
        };
    }

    private static TestNet2Params instance;
    public static synchronized TestNet2Params get() {
        if (instance == null) {
            instance = new TestNet2Params();
        }
        return instance;
    }
}
