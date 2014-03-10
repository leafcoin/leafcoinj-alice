package com.google.leafcoin.tools;

import java.io.File;

import com.google.leafcoin.core.AbstractBlockChain;
import com.google.leafcoin.core.Block;
import com.google.leafcoin.core.BlockChain;
import com.google.leafcoin.core.FullPrunedBlockChain;
import com.google.leafcoin.core.NetworkParameters;
import com.google.leafcoin.core.PrunedException;
import com.google.leafcoin.core.VerificationException;
import com.google.leafcoin.params.MainNetParams;
import com.google.leafcoin.params.TestNet3Params;
import com.google.leafcoin.store.BlockStore;
import com.google.leafcoin.store.BlockStoreException;
import com.google.leafcoin.store.BoundedOverheadBlockStore;
import com.google.leafcoin.store.FullPrunedBlockStore;
import com.google.leafcoin.store.H2FullPrunedBlockStore;
import com.google.leafcoin.store.MemoryBlockStore;
import com.google.leafcoin.store.MemoryFullPrunedBlockStore;
import com.google.leafcoin.store.SPVBlockStore;
import com.google.leafcoin.utils.BlockFileLoader;
import com.google.common.base.Preconditions;

/** Very thin wrapper around {@link com.google.leafcoin.util.BlockFileLoader} */
public class BlockImporter {
    public static void main(String[] args) throws BlockStoreException, VerificationException, PrunedException {
        System.out.println("USAGE: BlockImporter (prod|test) (H2|BoundedOverhead|Disk|MemFull|Mem|SPV) [blockStore]");
        System.out.println("       blockStore is required unless type is Mem or MemFull");
        System.out.println("       eg BlockImporter prod H2 /home/user/bitcoinj.h2store");
        System.out.println("       Does full verification if the store supports it");
        Preconditions.checkArgument(args.length == 2 || args.length == 3);
        
        NetworkParameters params = null;
        if (args[0].equals("test"))
            params = TestNet3Params.get();
        else
            params = MainNetParams.get();
        
        BlockStore store = null;
        if (args[1].equals("H2")) {
            Preconditions.checkArgument(args.length == 3);
            store = new H2FullPrunedBlockStore(params, args[2], 100);
        } else if (args[1].equals("BoundedOverhead")) {
            Preconditions.checkArgument(args.length == 3);
            store = new BoundedOverheadBlockStore(params, new File(args[2]));
        } else if (args[1].equals("MemFull")) {
            Preconditions.checkArgument(args.length == 2);
            store = new MemoryFullPrunedBlockStore(params, 100);
        } else if (args[1].equals("Mem")) {
            Preconditions.checkArgument(args.length == 2);
            store = new MemoryBlockStore(params);
        } else if (args[1].equals("SPV")) {
            Preconditions.checkArgument(args.length == 3);
            store = new SPVBlockStore(params, new File(args[2]));
        }
        
        AbstractBlockChain chain = null;
        if (store instanceof FullPrunedBlockStore)
            chain = new FullPrunedBlockChain(params, (FullPrunedBlockStore) store);
        else
            chain = new BlockChain(params, store);
        
        BlockFileLoader loader = new BlockFileLoader(params, BlockFileLoader.getReferenceClientBlockFileList());
        
        for (Block block : loader)
            chain.add(block);
    }
}
