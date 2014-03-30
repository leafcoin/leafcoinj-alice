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
import com.google.leafcoin.core.Sha256Hash;
import com.google.leafcoin.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends NetworkParameters {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        newInterval = INTERVAL_NEW;
        targetTimespan = TARGET_TIMESPAN;
        newTargetTimespan = TARGET_TIMESPAN_NEW;
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 223; //This is always addressHeader + 128
        addressHeader = 95;
        //p2shHeader = 5; //We don't have this
        acceptableAddressCodes = new int[] { addressHeader };
        port = 22813;
        
        packetMagic = 0xaaaaaacc;
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1388880557L);
        genesisBlock.setNonce(751211697);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 100000;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("721abe3814e15f1ab50514c8b7fffa7578c1f35aa915275ee91f4cb8d02be5c4"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        /* checkpoints.put(91722, new Sha256Hash("00000000000271a2dc26e7667f8419f2e15416dc6955e5a6c6cdf3f2574dd08e"));
        checkpoints.put(91812, new Sha256Hash("00000000000af0aed4792b1acee3d966af36cf5def14935db8de83d6f9306f2f"));
        checkpoints.put(91842, new Sha256Hash("00000000000a4d0a398161ffc163c503763b1f4360639393e0e4c8e300e0caec"));
        checkpoints.put(91880, new Sha256Hash("00000000000743f190a18c5577a3c2d2a1f610ae9601ac046a38084ccb7cd721"));
        checkpoints.put(200000, new Sha256Hash("000000000000034a7dedef4a161fa058a2d67a173a90155f3a2fe6fc132e0ebf")); */
        //TODO Get actual Leafcoin checkpoints

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

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }
}
